package com.comp9322.AssignREST.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.comp9322.AssignREST.model.LicenseNotice;
@Repository
@Mapper
public interface LicenseNoticeDao{
	@Select("select car_licenses.licid, renewal_notices.nid, driver_name, license_number, "
			+ "car_licenses.contact_email as email, car_licenses.current_address "
			+ "as address, renewal_notices.status, license_class, "
			+ "renewal_notices.review_result, expiry_date  from car_licenses "
			+ "left join renewal_notices on car_licenses.licid = renewal_notices.licid; ")
	public List<LicenseNotice> getAll();
	
	@Select("select car_licenses.licid, renewal_notices.nid, driver_name, license_number, "
			+ "car_licenses.contact_email as email, car_licenses.current_address "
			+ "as address, renewal_notices.status, license_class, "
			+ "renewal_notices.review_result, expiry_date from car_licenses, renewal_notices"
			+ " where car_licenses.licid = renewal_notices.licid and car_licenses.licid = #{licid}")
	public List<LicenseNotice> getLicenseNoticeByLicid(@Param("licid") int licid);
	
	@Select("select car_licenses.licid, renewal_notices.nid, driver_name, license_number, "
			+ "car_licenses.contact_email as email, car_licenses.current_address "
			+ "as address, renewal_notices.status, license_class, "
			+ "renewal_notices.review_result, expiry_date from car_licenses, renewal_notices"
			+ " where car_licenses.licid = renewal_notices.licid and renewal_notices.nid = #{nid}")
	public List<LicenseNotice> getLicenseNoticeByNid(@Param("nid") int nid);
	
	@Select("select car_licenses.licid, renewal_notices.nid, driver_name, license_number, "
			+ "car_licenses.contact_email as email, car_licenses.current_address "
			+ "as address, renewal_notices.status, license_class, "
			+ "renewal_notices.review_result, expiry_date from car_licenses, renewal_notices"
			+ " where car_licenses.licid = renewal_notices.licid and renewal_notices.access_token = #{token}")
	public List<LicenseNotice> getLicenseNoticeByToken(@Param("token") String token);
	
}