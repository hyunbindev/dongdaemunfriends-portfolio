package com.baduk.baduk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class TestController {
	@GetMapping("/")
	public String test() {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@SSSSs");
		return "test";
	}
}
