package com.comp9322.AssignREST.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.comp9322.AssignREST.model.States;
@Mapper
@Repository
public interface StatesDao {
	@Select("select * from states")
	List<States> getAll();
}
