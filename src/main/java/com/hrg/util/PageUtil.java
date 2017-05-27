package com.hrg.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 类说明：分页信息类
 *
 */
public class PageUtil<T> implements Serializable {

	private static final long serialVersionUID = 3509748940303200410L;

	/**
	 * 对象信息
	 */
	private List<T> pageResults;

	/**
	 * 总条数
	 */
	private Integer totalCount;

	/**
	 * 当前页
	 */
	private Integer currentPage;

	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize;

	/**
	 * 总页码
	 */
	private Integer totalPage;


	/**
	 * 开始行数
	 */
	@JsonIgnore
	private Integer startRow;

	public PageUtil(int pageSize, int tolalCount) {
		this.setPageSize(pageSize);
		this.setTotalCount(tolalCount);
	}
	public PageUtil(){
		this.setPageSize(8);
	}
	/**
	 * 
	 * 方法说明：分页信息生成，分页唯一需要设置的方法
	 * @param currentPage : 当前页
	 */
	public void generate(int currentPage) {
		this.setCurrentPage(currentPage);
		//组装totalPage
		this.setTotalPage((int) Math.ceil((double) this.getTotalCount() / this.getPageSize()));
		if(this.getTotalPage() == 0) {
			this.setTotalPage(1);
		}
		//当前页传参溢出最大页，设置为当前页
		if(this.getCurrentPage() > this.getTotalPage()) {
			this.setCurrentPage(this.getTotalPage());
		}
		this.setStartRow((currentPage - 1) * this.getPageSize());
	}

	public List<T> getPageResults() {
		return pageResults;
	}

	public void setPageResults(List<T> pageResults) {
		this.pageResults = pageResults;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalCountAndGenerate(Integer totalCount) {
		this.totalCount = totalCount;
		generate(currentPage);
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}


}
