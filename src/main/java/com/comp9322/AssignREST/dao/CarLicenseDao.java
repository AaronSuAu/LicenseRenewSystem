package com.comp9322.AssignREST.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.comp9322.AssignREST.model.CarLicenses;

@Repository
@Mapper
public interface CarLicenseDao {
	/*
	 * select all license
	 */
	@Select("select * from car_licenses")
	List<CarLicenses> getAllLicenses();

	/*
	 * get license by licid
	 */
	@Select("select * from car_licenses where licid = #{licid}")
	List<CarLicenses> getLicenseByLicid(@Param("licid") long licid);

	/*
	 * get licences by expiry_date
	 */
	@Select("select * from car_licenses where expiry_date <= #{date}")
	List<CarLicenses> getLicenseByExpiryDate(@Param("date") String date);

	/*
	 * update the license
	 */
	@Update("update car_licenses set driver_name=#{driver_name}, current_address=#{current_address}, "
			+ "contact_email=#{contact_email}, expiry_date=#{expiry_date} where licid = #{licid} ")
	boolean updateLicense(CarLicenses carLicense);
}
