package com.comp9322.AssignREST.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.CarLicenseDao;
import com.comp9322.AssignREST.dao.FeePaymentDao;
import com.comp9322.AssignREST.dao.RenewalNoticeDao;
import com.comp9322.AssignREST.model.CarLicenses;
import com.comp9322.AssignREST.model.FeePayments;
import com.comp9322.AssignREST.model.RenewalNotices;
import com.comp9322.AssignREST.response.JsonResponse;
import com.comp9322.AssignREST.response.JsonResponseList;
import com.google.gson.Gson;

@RestController
@RequestMapping("/payments")
public class FeePaymentController {
	@Autowired
	FeePaymentDao feePaymentDao;
	@Autowired
	CarLicenseDao carLicneseDao;
	@Autowired
	RenewalNoticeDao renewalNoticeDao;

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

	@RequestMapping(value = "/nid/{nid}", method = RequestMethod.GET, produces = "application/json")
	public String getPyamentByNid(@PathVariable("nid") int nid) {
		List<FeePayments> list = feePaymentDao.getPaymentsByNid(nid);
		if (list.size() == 0) {
			return new Gson().toJson(new JsonResponse(404, "not_found"));
		} else {
			JsonResponseList<FeePayments> jsonResponseList = new JsonResponseList<>(200, "successful_operation", list);
			return new Gson().toJson(jsonResponseList);
		}
	}

	@RequestMapping(value = "/finish", method = RequestMethod.PUT, produces = "application/json")
	public String finishPayment(@RequestBody RenewalNotices rNotices) {
		List<FeePayments> feeList;
		List<CarLicenses> carList;
		FeePayments fPayments = null;
		CarLicenses carLicenses = null;
		int nid = rNotices.getNid();
		feeList = feePaymentDao.getPaymentsByNid(nid);
		if (feeList.size() == 1) {
			fPayments = feeList.get(0);
		} else {
			return new Gson().toJson(new JsonResponse(404, "payment_not_found"));
		}
		System.out.println("*****************" + rNotices.getLicid());
		carList = carLicneseDao.getLicenseByLicid((long) rNotices.getLicid());
		if (carList.size() == 1) {
			carLicenses = carList.get(0);
		} else {
			return new Gson().toJson(new JsonResponse(404, "car_license_not_found"));
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		fPayments.setPaid_date(dateFormat.format(date));
		if (!feePaymentDao.updateReminderFlag(fPayments)) {
			return new Gson().toJson(new JsonResponse(500, "payment_update_fail"));
		}
		
		carLicenses.setContact_email(rNotices.getContact_email());
		carLicenses.setCurrent_address(rNotices.getAddress());
		if(!updateExpiryDate(carLicenses, rNotices)){
			return new Gson().toJson(new JsonResponse(500, "car_license_update_fail"));
		}
		if (!carLicneseDao.updateLicense(carLicenses)) {
			return new Gson().toJson(new JsonResponse(500, "car_license_update_fail"));
		}
		rNotices.setStatus("archived");
		if (renewalNoticeDao.update(rNotices) != 1) {
			return new Gson().toJson(new JsonResponse(500, "notice_update_fail"));
		}
		return new Gson().toJson(new JsonResponse(200, "successful_operation"));

	}

	public boolean updateExpiryDate(CarLicenses carLicenses, RenewalNotices rNotices) {
		if (carLicenses != null) {
			System.out.println("**************update expiry1");
			String dateString = carLicenses.getExpiry_date();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = null;
			try {
				startDate = df.parse(dateString);
				String newDateString = df.format(startDate);
				System.out.println(newDateString);
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				System.out.println("**************update expiry2");
				System.out.println(rNotices.getStatus());
				if (rNotices.getStatus().equals("validated") ||rNotices.getStatus().equals("rejected")) {
					cal.add(Calendar.YEAR, 1);
				} else if (rNotices.getStatus().equals("accepted")) {
					System.out.println("**************update expiry3");
					cal.add(Calendar.YEAR, 5);
				}
				startDate = cal.getTime();
				carLicenses.setExpiry_date(df.format(startDate));
				System.out.println("**************update expiry4");

				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
