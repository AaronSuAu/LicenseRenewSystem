package com.comp9322.AssignREST.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.LicenseNoticeDao;
import com.comp9322.AssignREST.model.LicenseNotice;
import com.comp9322.AssignREST.response.JsonResponse;
import com.comp9322.AssignREST.response.JsonResponseList;
import com.google.gson.Gson;

@RestController
@RequestMapping("/licenseNotice")
public class LicenseNoticeController {
	@Autowired
	LicenseNoticeDao licenseNoticeDao;
	Gson gson = new Gson();
	JsonResponseList<LicenseNotice> jsonResponseList;
	JsonResponse jsonResponse;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public String getAll() {
		List<LicenseNotice> list = licenseNoticeDao.getAll();
		if (list.size() != 0) {
			jsonResponseList = new JsonResponseList<LicenseNotice>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		} else {
			jsonResponse = new JsonResponse(404, "not found");
			return gson.toJson(jsonResponse);
		}
	}

	@RequestMapping(value = "/expiry/{days}", method = RequestMethod.GET)
	public String getPaymentByExpiry(@PathVariable("days") int days) {
		// get expiry date
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		long expiryDate = today.getTime() + (long) days * 24 * 60 * 60 * 1000;// after
		List<LicenseNotice> list = licenseNoticeDao.getAll();

		List<LicenseNotice> expiryLicenses = new ArrayList<LicenseNotice>();
		Date date;
		try {
			for (LicenseNotice l : list) {
				date = dateFormat.parse(l.getExpiry_date());
				if (date.getTime() <= expiryDate) {
					expiryLicenses.add(l);
				}
			}
		} catch (ParseException e) {
			return new Gson().toJson(new JsonResponse(405, "invalid_input"));
		}

		if (expiryLicenses == null || expiryLicenses.size() == 0) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			JsonResponseList<LicenseNotice> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					expiryLicenses);
			return new Gson().toJson(jsonResponseList);
		}
	}

	@RequestMapping(value = "/licid/{licid}", method = RequestMethod.GET, produces = "application/json")
	public String getByLicid(@PathVariable("licid") int licid) {
		List<LicenseNotice> list = licenseNoticeDao.getLicenseNoticeByLicid(licid);
		if (list.size() != 0) {
			jsonResponseList = new JsonResponseList<LicenseNotice>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		} else {
			jsonResponse = new JsonResponse(404, "not found");
			return gson.toJson(jsonResponse);
		}
	}

	@RequestMapping(value = "/nid/{nid}", method = RequestMethod.GET, produces = "application/json")
	public String getByNid(@PathVariable("nid") int nid) {
		List<LicenseNotice> list = licenseNoticeDao.getLicenseNoticeByLicid(nid);
		if (list.size() != 0) {
			jsonResponseList = new JsonResponseList<LicenseNotice>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		} else {
			jsonResponse = new JsonResponse(404, "not found");
			return gson.toJson(jsonResponse);
		}
	}

	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET, produces = "application/json")
	public String getByToken(@PathVariable("token") String token) {
		List<LicenseNotice> list = licenseNoticeDao.getLicenseNoticeByToken(token);
		if (list.size() != 0) {
			jsonResponseList = new JsonResponseList<LicenseNotice>(200, "successful_operation", list);
			return gson.toJson(jsonResponseList);
		} else {
			jsonResponse = new JsonResponse(404, "not found");
			return gson.toJson(jsonResponse);
		}
	}
}
