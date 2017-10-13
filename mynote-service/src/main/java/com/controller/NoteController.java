package com.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.NoteDao;
import com.entity.Note;
import com.util.PageParam;
import com.util.PageResult;

@RestController
public class NoteController {
	@Value("${server.port}")
	String port;

	@Autowired
	private NoteDao noteDao;

	@RequestMapping("/info")
	public String home() {
		return "i am from port:" + port;
	}

	/**
	 * 系统首页
	 * @return
	 */
	@RequestMapping(value = "/", method =  RequestMethod.GET)
	public PageResult<Note> findPage() {
		PageParam<Note> pageParam = new PageParam<Note>();
		Direction d = Direction.DESC;
		if(pageParam.getDirection() == 1) {
			d = Direction.ASC;
		}
		System.out.println(pageParam);
		Page<Note> page = noteDao.findAll(new PageRequest(pageParam.getPageNo()-1, pageParam.getPageSize(), d, pageParam.getSort()));
		System.out.println(page);
		PageResult<Note> pageResult = new PageResult<Note>();
		pageResult.setDataTotal((int)page.getTotalElements());
		pageResult.setPageData(page.getContent());
		pageResult.setPageNumber(page.getNumber() + 1);
		pageResult.setPageSize(page.getSize());
		pageResult.setPageTotal(page.getTotalPages());
		return pageResult;
	}
	/**
	 * 分页查询
	 * @param pageParam
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public PageResult<Note> findPage(@RequestBody PageParam<Note> pageParam) {
		Direction d = Direction.DESC;
		if(pageParam.getDirection() == 1) {
			d = Direction.ASC;
		}
		System.out.println(pageParam);
		Note note = pageParam.getP();
		Page<Note> page = null;
		if(null == note){
			page = noteDao.findAll(new PageRequest(pageParam.getPageNo()-1, pageParam.getPageSize(), d, pageParam.getSort()));
		} else { //分页查询分类
			page = noteDao.findAllByCategoryName(note.getCategoryName(),new PageRequest(pageParam.getPageNo()-1, pageParam.getPageSize(), d, pageParam.getSort()));
		}
		PageResult<Note> pageResult = new PageResult<Note>();
		pageResult.setDataTotal((int)page.getTotalElements());
		pageResult.setPageData(page.getContent());
		pageResult.setPageNumber(page.getNumber() + 1);
		pageResult.setPageSize(page.getSize());
		pageResult.setPageTotal(page.getTotalPages());
		return pageResult;
	}

	// 查看Note
	@RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
	public Note fetchNote(@PathVariable("id") Integer id) {
		Note note = noteDao.findById(id);
		System.out.println(note);
		return note;
	}

	// save Note
	@RequestMapping(value = "/note", method = RequestMethod.POST)
	public Note saveNote(@RequestBody Note note) {
		return noteDao.save(note);
	}
}
