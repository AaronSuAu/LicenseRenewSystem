package com.comp9322.AssignREST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9322.AssignREST.dao.StatesDao;

@RestController
@RequestMapping("/")
public class StatesController {
	@Autowired
	StatesDao statesDao;
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public int get(){
		System.out.println("1");
		return statesDao.getAll().size();
	}

}
