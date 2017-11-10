package com.etf.unsa.ba.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseController <M, R extends CrudRepository<M, Long>>{
	
	protected R repository; 
	
	@Autowired
	public void setRepo (R repository)	{
		this.repository = repository; 
	}
	
	//@RequestMapping("/all")
	
	@RequestMapping("/one/{id}")
	public M one(@PathVariable long id)	{
		return repository.findOne(id); 
	}

}
