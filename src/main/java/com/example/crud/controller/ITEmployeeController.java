package com.example.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ITEmployeeController {

	
	@GetMapping
	public String data() {
		return "hi ";
	}
}
