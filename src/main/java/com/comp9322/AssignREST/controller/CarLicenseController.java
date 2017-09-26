package com.comp9322.AssignREST.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.CarLicenseDao;
import com.comp9322.AssignREST.model.CarLicenses;
import com.comp9322.AssignREST.response.JsonResponse;
import com.comp9322.AssignREST.response.JsonResponseList;
import com.google.gson.Gson;

@RestController
@RequestMapping("/licenses")
public class CarLicenseController {
	@Autowired
	CarLicenseDao carLicenseDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getAllLicenses() {
		List<CarLicenses> licenses = carLicenseDao.getAllLicenses();
		if (licenses == null || licenses.size() == 0) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			JsonResponseList<CarLicenses> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					licenses);
			return new Gson().toJson(jsonResponseList);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String updateLicense(@RequestBody CarLicenses licenseToUpdate) {
		try {
			if (carLicenseDao.updateLicense(licenseToUpdate)) {
				return new Gson().toJson(new JsonResponse(200, "successful_operation"));
			} else {
				return new Gson().toJson(new JsonResponse(405, "invalid_input"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Gson().toJson(new JsonResponse(405, "invalid_input"));
		}
	}

	@RequestMapping(value = "/{licid}", method = RequestMethod.GET)
	public String getPaymentByID(@PathVariable("licid") Long licid) {
		List<CarLicenses> license = carLicenseDao.getLicenseByLicid(licid);
		if (license == null || license.size() == 0) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			JsonResponseList<CarLicenses> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					license);
			return new Gson().toJson(jsonResponseList);
		}
	}

	@RequestMapping(value = "/expiry/{days}", method = RequestMethod.GET)
	public String getPaymentByExpiry(@PathVariable("days") int days) {
		// get expiry date
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		long expiryDate = today.getTime() + (long) days * 24 * 60 * 60 * 1000;// after
		List<CarLicenses> licenses = carLicenseDao.getAllLicenses();
		List<CarLicenses> expiryLicenses = new ArrayList<CarLicenses>();
		Date date;
		try {
			for (CarLicenses l : licenses) {
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
			JsonResponseList<CarLicenses> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					expiryLicenses);
			return new Gson().toJson(jsonResponseList);
		}
	}

}
