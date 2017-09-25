package com.comp9322.AssignREST.response;

import java.util.List;

import com.comp9322.AssignREST.model.RenewalNotices;

public class RenewalNoticesResponse<T> {
	private int code;
	private String description;
	private List<T> list;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public RenewalNoticesResponse(int code, String description, List<T> list) {
		super();
		this.code = code;
		this.description = description;
		this.list = list;
	}
	public RenewalNoticesResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
