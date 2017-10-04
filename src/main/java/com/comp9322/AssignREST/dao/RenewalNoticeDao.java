package com.comp9322.AssignREST.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.comp9322.AssignREST.model.RenewalNotices;

@Mapper
@Repository
public interface RenewalNoticeDao {
	@Select("select * from renewal_notices where status <> 'archived'")
	List<RenewalNotices> getAll();

	@Insert("insert into renewal_notices (address, contact_email, "
			+ "status, review_result, access_token, licid)values("
			+ "#{address}, #{contact_email}, #{status}, #{review_result}, " + "#{access_token}, " + "#{licid})"
			+ "#{address}, #{contact_email}, #{status}, #{review_result}, "
			+ "#{access_token}, #{licid})")
	@Options(useGeneratedKeys = true, keyProperty = "nid", keyColumn = "renewal_notice.nid")
	int create(RenewalNotices renewalNotices);

	@Update("update renewal_notices set address=#{address}, contact_email ="
			+ "#{contact_email}, status = #{status}, review_result = #{review_result}, "
			+ "access_token = #{access_token}, licid=#{licid} where nid=#{nid}")
	int update(RenewalNotices renewalNotices);

	@Select("select * from renewal_notices where status = #{status} and status <> 'archived'")
	List<RenewalNotices> getNoticesByStatus(@Param("status") String status);

	@Select("select * from renewal_notices where nid = #{nid} and status <> 'archived'")
	List<RenewalNotices> getNoticesByNid(@Param("nid") int nid);

	@Delete("delete from renewal_notices where nid = #{nid}")
	int deleteNoticesByNid(@Param("nid") int nid);

	@Select("select * from renewal_notices where access_token = #{access_token} and status <> 'archived'")
	List<RenewalNotices> getNoticesByAccessToken(@Param("access_token") String accessToken);

	@Delete("delete from renewal_notices where nid != null")
	int deleteAll();
}
