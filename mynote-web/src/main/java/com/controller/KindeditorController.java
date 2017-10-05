package com.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.ImageUploadUtil;
import com.util.JsonUtils;


@Controller
@Slf4j
public class KindeditorController{
	@Value("${web.upload-path}")
	private String uploadPath;
	
	/**
     * 上传
     * @Title fileUpload
     * @param request
     * @param response
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map<String, Object> imageUpload(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
        try {
        	//父文件夹
            String dirName = request.getParameter("dir");
            if (dirName != null) {
                if(!Arrays.asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                    responseMap.put("error", 1);
                    responseMap.put("message", "无效的目录");
                    return responseMap;
                }
            }
            String dirPath = uploadPath + dirName + "/";
        	String fileName = ImageUploadUtil.upload(request, dirPath);
            responseMap.put("error", 0);
            responseMap.put("url", "/" + dirName +"/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", 1);
            responseMap.put("message", e);
        }
        log.info(responseMap.toString());
        return responseMap;
    }
    
    /**
     * 文件管理
     * @param request
     * @param response
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/fileManager")
    public void fileManager(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根目录路径
        String rootPath = uploadPath;
        //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl  = "/";//request.getServletContext().getRealPath("/") +  "upload/";
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        //要打开的文件夹
        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if(!Arrays.asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                out.println("无效的目录");
                return;
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        //排序形式，name or size or type
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        //目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if(!currentPathFile.isDirectory()){
            out.println("Directory does not exist.");
            return;
        }

        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, (o1,o2)->
				(Long.valueOf(o1.get("filesize").toString()).intValue() - Long.valueOf(o2.get("filesize").toString()).intValue())
			);
        } else if ("type".equals(order)) {
            Collections.sort(fileList, (o1,o2) -> o1.get("filetype").toString().compareTo(o2.get("filetype").toString()));
        } else {
            Collections.sort(fileList, (o1,o2) -> o1.get("filename").toString().compareTo(o2.get("filename").toString()));
        }
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", rootUrl + currentDirPath); //url
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        response.setContentType("application/json; charset=UTF-8");
        out.println(JsonUtils.object2Json(result));
    }
    
    /**
     * 描述：kindeditor 粘贴图片上传
     * @date 2017年5月23日上午11:04:16
     * @return
     */
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/imgUpload/base64", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> imageUploadBase64(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("errCode",0);
        try{
            String imgageFilePath = null;
            String imageDataBase64 = request.getParameter("imageDataBase64");
             if(StringUtils.isNotEmpty(imageDataBase64)){
                 String[] arrImageData = imageDataBase64.split(",");
                 String[] arrTypes = arrImageData[0].split(";");
                 String[] arrImageType = arrTypes[0].split(":");
                 String imageType = arrImageType[1];
                 String imageTypeSuffix = imageType.split("/")[1];
                 if("base64".equalsIgnoreCase(arrTypes[1])){
                     Base64 decoder = new Base64();
                     byte[] decodeBuffer = decoder.decodeBase64(arrImageData[1]);
                     SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                     String currFormat = df.format(new Date());
                     File currFileBag = new File(uploadPath);
                     if(!currFileBag.exists()){
                         currFileBag.mkdirs();
                     }
                     String dir = uploadPath + "image/";
                     File currFormatFile = new File(dir + currFormat);
                     if(!currFormatFile.exists()){
                    	 currFormatFile.mkdirs();
                     }
                     imgageFilePath = currFormat+"/"+new Random().nextInt(100000) + "." + imageTypeSuffix;
                     File currFile = new File(dir +imgageFilePath);
                     int i = 0;
                     while(currFile.exists()){
                         imgageFilePath = currFormat+"/"+new Random().nextInt(100000) + "." + imageTypeSuffix;
                         currFile = new File(dir +imgageFilePath);
                         i++;
                         if(i>=100000){
                             imgageFilePath = null;
                             currFile = null;
                             break;
                         }
                     }
                     
                     if(currFile!=null){
                         OutputStream out = new FileOutputStream(currFile);
                         out.write(decodeBuffer);  
                         out.flush();  
                         out.close();  
                     }
                 }
             }
             //imgageFilePath路径存在表示上传成功
             if(imgageFilePath!=null){
                 resultMap.put("result", "/image/" +imgageFilePath);
             }else{
                log.error("上传图片发生未知异常....");
                resultMap.put("errCode",1);
                resultMap.put("errMsg","上传图片发生未知异常...."); 
             }
        }catch(Exception e){
            log.error("上传图片发生异常: ", e);
            resultMap.put("errCode",1);
            resultMap.put("errMsg","上传图片发生未知异常...."); 
        }
        return resultMap;
    }
}
