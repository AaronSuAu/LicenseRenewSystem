package com.comp9322.AssignREST.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.RenewalNoticeDao;
import com.comp9322.AssignREST.model.RenewalNoticeHATEOS;
import com.comp9322.AssignREST.model.RenewalNotices;
import com.comp9322.AssignREST.response.JsonResponse;
import com.comp9322.AssignREST.response.JsonResponseList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/renewals")
public class RenewalNoticeController {
	@Autowired
	RenewalNoticeDao renewalNoticeDao;
	
	Gson gson = new Gson();
	JsonResponseList<RenewalNotices> jsonResponseList;
	JsonResponse jsonResponse;
	//Get all the notice
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public String getAll() {
		List<RenewalNotices> list = renewalNoticeDao.getAll();
		if (list.size() != 0) {
			jsonResponseList = new JsonResponseList<RenewalNotices>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		}else {
			jsonResponse = new JsonResponse(404, "not found");
			return gson.toJson(jsonResponse);
		}
	}
	// Create a new notice
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public String create(@RequestBody RenewalNotices renewalNotices) {
		String uniqueKey = UUID.randomUUID().toString();
		while (renewalNoticeDao.getNoticesByAccessToken(uniqueKey).size() != 0) {
			uniqueKey = UUID.randomUUID().toString();
		}
		renewalNotices.setAccess_token(uniqueKey);
		renewalNotices.setStatus("new");
		if(renewalNoticeDao.create(renewalNotices)==1){
			jsonResponseList = new JsonResponseList<RenewalNotices>();
			jsonResponseList.setCode(200);
			jsonResponseList.setDescription("successful_operation");
			List<RenewalNotices> list = new ArrayList<>();
			list.add(renewalNotices);
			jsonResponseList.setList(list);
			String jsonString = gson.toJson(jsonResponseList);
			return jsonString;
		}else{
			jsonResponse = new JsonResponse(405, "invalid_input");
			return gson.toJson(jsonResponse);
		}
	}
	// update a notice
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json")
	public String update(@RequestBody RenewalNotices renewalNotices) {
		if (renewalNoticeDao.update(renewalNotices) == 1) {
			List<RenewalNotices> list = new ArrayList<>();
			list.add(renewalNotices);
			jsonResponseList = new JsonResponseList<>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		} else {
			jsonResponse = new JsonResponse(405, "invalid_input");
			return gson.toJson(jsonResponse);
		}
	}
	//get notices by status
	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET, produces = "application/json")
	public String getNoticesByStatus(@PathVariable("status") String status) {
		List<RenewalNotices> list = renewalNoticeDao.getNoticesByStatus(status);
		if(list.size() != 0){
			jsonResponseList = new JsonResponseList<>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		}else{
			jsonResponse = new JsonResponse(404, "not_found");
			return gson.toJson(jsonResponse);
		}
	}
	
	//get notice by notice id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public String getNoticesByNid(@PathVariable("id") int nid){
		List<RenewalNotices> list = renewalNoticeDao.getNoticesByNid(nid);
		if(list.size() == 0){
			jsonResponse = new JsonResponse(404, "not_found");
			return gson.toJson(jsonResponse);
		}else{
			jsonResponseList = new JsonResponseList<>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		}
	}
	// delete notice by notice id
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public String deleteNoticeByNid(@PathVariable("id") int nid){
		if(renewalNoticeDao.deleteNoticesByNid(nid)==0){
			jsonResponse = new JsonResponse(404, "not_found");
		}else{
			jsonResponse = new JsonResponse(200, "successful_operation");
		}
		return gson.toJson(jsonResponse);
	}
	//get notice by access token
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET, produces = "application/json")
	public String getNoticeByAccessToken(@PathVariable("token") String accessToken){
		List<RenewalNotices> list = renewalNoticeDao.getNoticesByAccessToken(accessToken);
		if(list.size() == 0){
			jsonResponse = new JsonResponse(404, "not_found");
			return gson.toJson(jsonResponse);
		}else{
			List<RenewalNoticeHATEOS> resultList = new ArrayList<>();
			RenewalNoticeHATEOS rHateos = new RenewalNoticeHATEOS(list.get(0));
			rHateos.setLink("http://localhost:8090/licenses/"+list.get(0).getLicid());
			resultList.add(rHateos);
			JsonResponseList<RenewalNoticeHATEOS> jsonResponseList;
			jsonResponseList = new JsonResponseList<>(200, "successful_operation", resultList);
			return gson.toJson(jsonResponseList);
		}
	}
//	@RequestMapping(value = "/danger/delete", method = RequestMethod.DELETE)
//	public String deleteDanger(){
//		renewalNoticeDao.deleteAll();
//		return "success";
//	}
}
