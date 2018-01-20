package com.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 适配工厂类
 * @author tomtop2149
 */
public class AdapterFactory implements FactoryInterface{

	private static AdapterFactory adapterFactory = new AdapterFactory();
	
	public AdapterFactory() {
	}
	
	public static AdapterFactory getFactory(){
		return adapterFactory;
	}
	
	/**
	 * 适配对象（复制属性）
	 * @param src 源对象
	 * @param dClass 目标对象class
	 * @return 目标对象
	 */
	@Override
	public <D>D getDestObject(Object src, Class<D> dClass) {
		D dest = null;
		try {
			dest = dClass.newInstance();
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}
	
	/**
	 * 适配对象List（复制属性）
	 * @param src 源对象List
	 * @param dClass 目标对象class
	 * @return 目标对象List
	 */
	public <S,D>List<D> getDestObjectList(List<S> srcList, Class<D> dClass) {
		List<D> destList = new ArrayList<D>();
		try {
			for(S src : srcList){
				D dest = dClass.newInstance(); //实例化目标对象
				BeanUtils.copyProperties(dest, src); //复制属性
				destList.add(dest); //List添加目标对象
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destList;
	}
	
}
