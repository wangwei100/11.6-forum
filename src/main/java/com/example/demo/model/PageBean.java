package com.example.demo.model;

import java.util.List;

public class PageBean<T> {
	private int pageNum;
	private int pageSize;
	private int totalPage;
	private int totalRecord;
	private int begin;
	private int end;
	private List<T> list;

	public PageBean(int pageNum, int pageSize, int totalRecord) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		if (totalRecord % pageSize == 0) {
			this.totalPage = totalRecord / pageSize;
		} else {
			this.totalPage = totalRecord / pageSize + 1;
		}
		this.begin = 1;
		this.end = 5;

		if (totalPage <= 5) {
			this.end = this.totalPage;
		} else {
			this.begin = pageNum - 2;
			this.end = pageNum + 2;

			if (begin < 0) {
				this.begin = 1;
				this.end = 5;
			}
			if (end > this.totalPage) {
				this.end = totalPage;
				this.begin = end - 5;
			}
		}
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
