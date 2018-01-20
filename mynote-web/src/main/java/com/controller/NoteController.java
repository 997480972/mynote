package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.PageRq;
import com.dto.PageRs;
import com.entity.Note;
import com.hystrix.NoteHystrix;
import com.util.GzipUtils;
import com.util.JsonUtils;
import com.util.PageParam;
import com.util.PageResult;


@Controller
@Slf4j
public class NoteController {
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private NoteHystrix noteHystrix;
	
	@ResponseBody
	@RequestMapping("/info")
	public String home() {
		return "i am from port:" + port;
	}
	/**
	 * 系统首页
	 * @return
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView main(){
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("categoryName", "Java");
 		return new ModelAndView("layout/main");
	}
	@ResponseBody
	@RequestMapping(value="/notes", method = RequestMethod.POST)
	public PageRs<Note> toMain(PageRq pageRq){
		System.out.println("post:" + pageRq);
		PageRs<Note>  pageRs = noteHystrix.findAll(pageRq);
		return pageRs;
	}
	
	//跳转Note编辑页面
	@RequestMapping({"/noteEdit"})
	public String addNote(String id,  Model model){
		if(null != id){
			Note note = noteHystrix.findOne(Integer.valueOf(id));
			log.info("note:{}",note);
			model.addAttribute("note",JsonUtils.object2Json(note));
		}
		return "note/note-edit";
	}
	
	//保存Note
	@RequestMapping(value="/note", method=RequestMethod.POST)
	public void saveNote(Note note, HttpServletResponse response){
		log.info("saveNote:{}",noteHystrix.save(note));
		GzipUtils.writeResponseDataStr(JsonUtils.object2Json(note), response);
		
	}
	//通过id查看Note
	@RequestMapping(value="/note/{id}", method=RequestMethod.GET)
	public String fetchNote(@PathVariable("id") Integer id, Model model){
		Note note = noteHystrix.findOne(id);
		log.info("note:{}",note);
		model.addAttribute("note",JsonUtils.object2Json(note));
		return "note/note";
	}
}
