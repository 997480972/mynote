package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dao.NoteDao;
import com.entity.Note;
import com.util.GzipUtils;
import com.util.JsonUtils;


@Controller
@Slf4j
public class NoteController {
	@Autowired
	private NoteDao noteDao;
	
	/**
	 * 系统首页
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView toMain(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String noteString = JsonUtils.object2Json(noteDao.findAll());
			map.put("notes", noteString); 
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		log.info("map:{}",map);
		return new ModelAndView("layout/main",map);
	}
	
	//跳转Note编辑页面
	@RequestMapping({"/noteEdit"})
	public String addNote(String id,  Model model){
		if(null != id){
			Note note = noteDao.findOne(Integer.valueOf(id));
			log.info("note:{}",note);
			model.addAttribute("note",JsonUtils.object2Json(note));
		}
		return "note/note-edit";
	}
	
	//保存Note
	@RequestMapping(value="/note", method=RequestMethod.POST)
	public void saveNote(Note note, HttpServletResponse response){
		log.info("saveNote:{}",noteDao.save(note));
		GzipUtils.writeResponseDataStr(JsonUtils.object2Json(note), response);
		
	}
	//查看Note
	@RequestMapping(value="/note/{id}", method=RequestMethod.GET)
	public String fetchNote(@PathVariable("id") String id, Model model){
		Note note = noteDao.findOne(Integer.valueOf(id));
		log.info("note:{}",note);
		model.addAttribute("note",JsonUtils.object2Json(note));
		return "note/note";
	}
}
