package com.skilldistillery.eventtracker.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4202" })
public class PingController {
	
	@RequestMapping(path = "ping", method = RequestMethod.GET)
	public String ping() {
		return "pong event tracker";
	}
}
