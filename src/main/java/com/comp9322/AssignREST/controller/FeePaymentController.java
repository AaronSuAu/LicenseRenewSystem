package com.comp9322.AssignREST.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.FeePaymentDao;
import com.comp9322.AssignREST.model.FeePayments;
import com.comp9322.AssignREST.response.JsonResponse;
import com.comp9322.AssignREST.response.JsonResponseList;
import com.google.gson.Gson;

@RestController
@RequestMapping("/payments")
public class FeePaymentController {
	@Autowired
	FeePaymentDao feePaymentDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getAllPayments() {
		List<FeePayments> paymentsList = feePaymentDao.getAllPayments();
		if (paymentsList == null || paymentsList.size() == 0) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			JsonResponseList<FeePayments> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					paymentsList);
			return new Gson().toJson(jsonResponseList);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addPayment(@RequestBody FeePayments newPayment) {
		try {
			if (!feePaymentDao.addPayment(newPayment)) {
				return new Gson().toJson(new JsonResponse(405, "invalid_input"));
			} else {
				ArrayList<FeePayments> paymentsList = new ArrayList<FeePayments>();
				paymentsList.add(newPayment);
				JsonResponseList<FeePayments> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
						paymentsList);
				return new Gson().toJson(jsonResponseList);
			}
		} catch (Exception e) {
			return new Gson().toJson(new JsonResponse(405, "invalid_input"));
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String updatePayment(@RequestBody FeePayments paymentToUpdate) {
		try {
			if (feePaymentDao.updateReminderFlag(paymentToUpdate)) {
				return new Gson().toJson(new JsonResponse(200, "successful_operation"));
			} else {
				return new Gson().toJson(new JsonResponse(405, "invalid_input"));
			}
		} catch (Exception e) {
			return new Gson().toJson(new JsonResponse(405, "invalid_input"));
		}
	}

	@RequestMapping(value = "/{pid}", method = RequestMethod.GET)
	public String getPaymentByID(@PathVariable("pid") Long pid) {
		FeePayments payment = feePaymentDao.getPaymentByID(pid);
		if (payment == null) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			List<FeePayments> paymentsList = new ArrayList<FeePayments>();
			paymentsList.add(payment);
			JsonResponseList<FeePayments> jsonResponseList = new JsonResponseList<>(200, "successful_operation",
					paymentsList);
			return new Gson().toJson(jsonResponseList);
		}
	}

}
