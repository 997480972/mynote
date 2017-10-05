package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

public class GzipUtils {
	/** 向浏览器输出字符串响应数据 */
	public static void writeResponseDataStr(String data,HttpServletResponse response){
		/** 设置响应内容类型 */
		response.setContentType("text/html;charset=utf-8");
		try {
			/** 告诉浏览器，服务器响应的数据是用GZIP压缩的 */
			response.setHeader("Content-Encoding", "gzip");
			//GZIP压缩的原理是:将数据全部压缩进内存输出流中,再从将内存输出流中的数据输出
			/** 创建内存输出流的容器 */
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			/** 创建GZIP压缩对象 */
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			/** 进行压缩 */
			gzip.write(data.getBytes("utf-8"));
			gzip.flush();
			gzip.close();
			/** 向浏览器输出响应数据 */
			response.getOutputStream().write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
