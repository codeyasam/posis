package com.codeyasam.posis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PosisController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		return "index";
	}
	
}
