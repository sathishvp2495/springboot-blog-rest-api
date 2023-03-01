package com.satz.blog.payload;

import java.util.List;

public class PostResponse {
	
	private List<PostDto> content;
	private int PageNo;
	private int PageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
	public List<PostDto> getContent() {
		return content;
	}
	public void setContent(List<PostDto> content) {
		this.content = content;
	}
	public int getPageNo() {
		return PageNo;
	}
	public void setPageNo(int pageNo) {
		PageNo = pageNo;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	@Override
	public String toString() {
		return "PostResponse [content=" + content + ", PageNo=" + PageNo + ", PageSize=" + PageSize + ", totalElements="
				+ totalElements + ", totalPages=" + totalPages + ", last=" + last + "]";
	}
	public PostResponse(List<PostDto> content, int pageNo, int pageSize, long totalElements, int totalPages,
			boolean last) {
		super();
		this.content = content;
		PageNo = pageNo;
		PageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
	}
	public PostResponse() {
		// TODO Auto-generated constructor stub
	}
	
	

}
