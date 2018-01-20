package com.util;

/**
 * 抽象工厂
 * @author tomtop2149
 */
public interface FactoryInterface {


	public <D>D getDestObject(Object src, Class<D> dClass);
}
