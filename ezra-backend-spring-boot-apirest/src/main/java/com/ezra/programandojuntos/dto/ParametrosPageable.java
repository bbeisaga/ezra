package com.ezra.programandojuntos.dto;

import com.ezra.programandojuntos.enums.SortActiveCliente;
import com.ezra.programandojuntos.enums.SortDirection;

public class ParametrosPageable<T> {
	private Integer pageSize;
	private Integer pageNumber;
	private String query;
	private SortActiveCliente active;
	private SortDirection direction;
	
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	public SortDirection getDirection() {
		return direction;
	}
	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}
	public SortActiveCliente getActive() {
		return active;
	}
	public void setActive(SortActiveCliente active) {
		this.active = active;
	}
	

	
	
}
