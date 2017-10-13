package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResult<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_PAGE_NO = 1;
    public final static int DEFAULT_PAGE_SIZE = 20;
	
	private List<T> pageData;
	private int pageSize=DEFAULT_PAGE_SIZE; //每页多少条
	private int pageNumber=DEFAULT_PAGE_NO;//第几页
	private int dataTotal; //总数据
	private int pageTotal; //总页数
	
	public PageResult() {
		super();
	}
	/**
     * 创建一个分页数据对象
     * 
     * @param pageNumber
     *        页号，从1开始
     * @param pageSize
     *        每页记录数
     * @param dataTotal
     *        总记录数
     */
	public PageResult(int pageSize, int pageNumber, int dataTotal) {
		super();
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.dataTotal = dataTotal;
		this.calcPageTotal();// 计算总页数;
	}
	

    /**
     * 创建一个分页数据对象
     * 
     * @param pageNumber
     *        页号，从1开始
     * @param pageSize
     *        每页记录数
     * @param dataTotal
     *        总记录数
     * @param pageData
     *        当前页数据列表
     */
    public PageResult(int pageNumber, int pageSize, int dataTotal, List<T> pageData)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.dataTotal = dataTotal;
        this.pageData = pageData;
        this.calcPageTotal();// 计算总页数;
    }
    /**
     * 取总页数
     */
    private void calcPageTotal()
    {
        if (dataTotal == 0)
        {
            this.pageTotal = 0;
        }
        else
        {
            if (dataTotal % pageSize == 0)
            {
                this.pageTotal = dataTotal / pageSize;
            }
            else
            {
                this.pageTotal = dataTotal / pageSize + 1;
            }
        }
    }
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getDataTotal() {
		return dataTotal;
	}
	public void setDataTotal(int dataTotal) {
		this.dataTotal = dataTotal;
        this.calcPageTotal();// 计算总页数
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	
	
	/**
     * 取得List的第N页的subList
     * 
     * @param list
     *        要分页的list
     * @param pageSize
     * @param pageNo
     * @return List
     */
    private static <T> List<T> subList(List<T> list, int pageNo, int pageSize)
    {
        // 初始化每页尺寸和页号
        pageSize = (pageSize <= 0 ? 10 : pageSize);
        pageNo = (pageNo <= 0 ? 1 : pageNo);

        // 计算开始结束位置
        int begin = (pageSize * (pageNo - 1) > list.size() ? list.size() : pageSize * (pageNo - 1));
        int end = (pageSize * pageNo > list.size() ? list.size() : pageSize * pageNo);

        // 返回分页数据
        return new ArrayList<T>(list.subList(begin, end));
    }
    
    /**
     * 取得List的第N页的PageResult对象
     * 
     * @param list
     *        要分页的list
     * @param pageNo
     *        第几页，第一页为1
     * @param pageSize
     *        每页记录数
     * @return List 一页的数据
     */
    public static <T> PageResult<T> pageList(List<T> list, int pageNo, int pageSize)
    {
        List<T> l = subList(list, pageNo, pageSize);
        return new PageResult<T>(pageNo, pageSize, list.size(), l);
    }
	@Override
	public String toString() {
		return "PageResult [pageData=" + pageData + ", pageSize=" + pageSize + ", pageNumber=" + pageNumber
				+ ", dataTotal=" + dataTotal + ", pageTotal=" + pageTotal + "]";
	}
    
}
