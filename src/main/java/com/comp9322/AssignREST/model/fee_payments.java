package com.comp9322.AssignREST.model;

import org.springframework.stereotype.Component;

@Component
public class fee_payments {
	private int pid;
	private int nid;
	private int amount;
	private String paid_date;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPaid_date() {
		return paid_date;
	}
	public void setPaid_date(String paid_date) {
		this.paid_date = paid_date;
	}
	public fee_payments(int pid, int nid, int amount, String paid_date) {
		super();
		this.pid = pid;
		this.nid = nid;
		this.amount = amount;
		this.paid_date = paid_date;
	}
	public fee_payments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
