package com.controller;


import com.util.AdapterFactory;
import com.util.IdCardUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;


@RestController
@Api(tags={"身份证接口"})
public class IdCardController {
	private AdapterFactory adapterFactory = AdapterFactory.getFactory();

	//根据身份编号获取户籍地址（省市区）
	@ApiOperation(value = "根据身份编号获取户籍地址（省市区）", notes = "根据身份编号获取户籍地址（省市区）")
	@RequestMapping(value = "/getAddressByIdCard/{idCard}", method = RequestMethod.GET)
	public String getAddressByIdCard(@PathVariable("idCard") String idCard) {
		return new String(IdCardUtils.getAddressByIdCard(idCard).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

	//根据身份编号获取生日年
	@ApiOperation(value = "根据身份编号获取生日年", notes = "生日(yyyy)")
	@RequestMapping(value = "/getYearByIdCard/{idCard}", method = RequestMethod.GET)
	public Short getYearByIdCard(@PathVariable("idCard") String idCard) {
		return IdCardUtils.getYearByIdCard(idCard);
	}

	//根据身份编号获取生日月
	@ApiOperation(value = "根据身份编号获取生日月", notes = "生日(MM)")
	@RequestMapping(value = "/getMonthByIdCard/{idCard}", method = RequestMethod.GET)
	public Short getMonthByIdCard(@PathVariable("idCard") String idCard) {
		return IdCardUtils.getMonthByIdCard(idCard);
	}

	//根据身份编号获取生日天
	@ApiOperation(value = "根据身份编号获取生日天", notes = "生日(dd)")
	@RequestMapping(value = "/getDayByIdCard/{idCard}", method = RequestMethod.GET)
	public Short getDayByIdCard(@PathVariable("idCard") String idCard) {
		return IdCardUtils.getDayByIdCard(idCard);
	}

	//根据身份编号获取性别
	@ApiOperation(value = "根据身份编号获取性别", notes = "性别(男，女)")
	@RequestMapping(value = "/getGenderByIdCard/{idCard}", method = RequestMethod.GET)
	public String getGenderByIdCard(@PathVariable("idCard") String idCard) {
		return IdCardUtils.getGenderByIdCard(idCard);
	}

}
