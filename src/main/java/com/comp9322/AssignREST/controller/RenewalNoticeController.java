package com.comp9322.AssignREST.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.RenewalNoticeDao;
import com.comp9322.AssignREST.model.RenewalNotices;
import com.comp9322.AssignREST.response.RenewalNoticesResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/renewals")
public class RenewalNoticeController {
	@Autowired
	RenewalNoticeDao renewalNoticeDao;
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<RenewalNotices> getAll(){
		return renewalNoticeDao.getAll();
	}
	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public String create(@RequestBody RenewalNotices renewalNotices){
		System.out.println("1");
		renewalNoticeDao.create(renewalNotices);
		RenewalNoticesResponse<RenewalNotices> response = new RenewalNoticesResponse<RenewalNotices>();
		response.setCode(200);
		response.setDescription("success_operation");
		List<RenewalNotices> list = new ArrayList<>();
		response.setList(list);
		Gson gson = new Gson();
		String jsonString = gson.toJson(response);
		System.out.println(jsonString);
		return jsonString;
	}
}
