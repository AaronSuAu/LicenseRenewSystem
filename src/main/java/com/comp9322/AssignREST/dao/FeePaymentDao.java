package com.comp9322.AssignREST.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.comp9322.AssignREST.model.FeePayments;

@Repository
@Mapper
public interface FeePaymentDao {
	/*
	 * get all payments
	 */
	@Select("select * from fee_payments")
	List<FeePayments> getAllPayments();

	/*
	 * get payment by p_id
	 */
	@Select("select * from fee_payments where pid = #{pid}")
	FeePayments getPaymentByID(@Param("pid") long pid);

	/*
	 * add a payment
	 */
	@Insert("insert into fee_payments (nid, amount) " + "values(#{nid}, #{amount})")
	@Options(useGeneratedKeys = true, keyProperty = "pid", keyColumn = "fee_payments.pid")
	boolean addPayment(FeePayments payment);

	/*
	 * update a payment
	 */
	@Update("update fee_payments set amount=#{amount}, paid_date=#{paid_date} where pid = #{pid}")
	boolean updateReminderFlag(FeePayments payment);
	
	@Select("select * from fee_payments where nid = #{nid} and paid_date is NULL")
	List<FeePayments> getPaymentsByNid(@Param("nid") int id);
}
