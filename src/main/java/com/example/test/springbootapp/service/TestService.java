package com.example.test.springbootapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void testNestedLog() {
		log.info("test log inside method call");
	}
}
