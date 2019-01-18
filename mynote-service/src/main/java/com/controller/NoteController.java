package com.controller;



import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.NoteDao;
import com.dto.BaseResponse;
import com.dto.NoteRq;
import com.dto.PageRq;
import com.dto.PageRs;
import com.dto.NoteRsData;
import com.entity.Note;
import com.util.AdapterFactory;
import com.util.JsonUtils;
import com.util.PageParam;
import com.util.PageResult;

@RestController
@Api(tags={"笔记接口"})
public class NoteController {
	@Value("${server.port}")
	String port;

	@Autowired
	private NoteDao noteDao;
	
	private AdapterFactory adapterFactory = AdapterFactory.getFactory();

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ApiOperation("info信息")
	public String home() {
		return "i am from port:" + port;
	}

	/**
	 * 系统首页
	 * @return
	 */
//	@RequestMapping(value = "/notes", method = RequestMethod.GET)
//	@ApiOperation(value = "获取第一页Note信息", notes = "获取note第一页的数据")
//	public PageRs<NoteRsData> findPage() {
//		System.out.println("获取第一页Note信息");
//		PageRq<Note> pageRq = new PageRq<Note>();
//		Direction d = Direction.DESC;
//		if(pageRq.getDirection() == 1) {
//			d = Direction.ASC;
//		}
//		//分页查询数据
//		Page<Note> page = noteDao.findAll(new PageRequest(pageRq.getPageNo()-1, pageRq.getPageSize(), d, pageRq.getSort()));
//		return new PageRs<NoteRsData>(page, NoteRsData.class);
//	}
	/**
	 * 分页查询
	 * @param
	 * @return
	 */
	@ApiOperation(value = "获取Note信息", notes = "获取note数据，分页查询")
	@RequestMapping(value = "/notes", method = RequestMethod.POST)
	public PageRs<NoteRsData> findPage(@RequestBody PageRq pageRq) {
		System.out.println("获取note数据，分页查询");
		
		System.out.println(pageRq);
		Map<String, Object> queryMap = pageRq.getQueryMap();
		Page<Note> page = null;
		if(null != queryMap && queryMap.size() == 0){
			page = noteDao.findAll(new PageRequest(pageRq.getPageNo()-1, pageRq.getPageSize(), pageRq.getDescOrAsc(), pageRq.getSort()));
		} else { //分页查询分类
			page = noteDao.findAllByCategoryName(queryMap.get("categoryName").toString(),new PageRequest(pageRq.getPageNo()-1, pageRq.getPageSize(), pageRq.getDescOrAsc(), pageRq.getSort()));
		}
		return new PageRs<NoteRsData>(page, NoteRsData.class);
	}
	
	// 查看Note
	@ApiOperation(value = "根据id获取note", notes = "获取单个Note")
	@RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
	public NoteRsData fetchNote(@PathVariable("id") Integer id) {
		Note note = noteDao.findById(id);
		System.out.println(note);
		return adapterFactory.getDestObject(note, NoteRsData.class);
	}

	// save Note
	@ApiOperation(value = "保存单个note", notes = "保存或更新，有id则更新，无id则新增")
	@RequestMapping(value = "/note", method = RequestMethod.POST)
	public PageRs<NoteRsData> saveNote(@Valid @RequestBody NoteRq noteRq, BindingResult bindingResult) {
		String msg = noteRq.validate(bindingResult);
		PageRs<NoteRsData> pageRs = new PageRs<NoteRsData>(msg);
		if(StringUtils.isNotBlank(msg)){ //校验不通过
			pageRs.setCode(403);
			return pageRs;
		}
		 //封装实体
		Note note = adapterFactory.getDestObject(noteRq, Note.class);
		pageRs.getPageData().add(adapterFactory.getDestObject(noteDao.save(note), NoteRsData.class)); //封装页面响应对象
		pageRs.setMessage("save succcess!");
		return pageRs;
	}
}
