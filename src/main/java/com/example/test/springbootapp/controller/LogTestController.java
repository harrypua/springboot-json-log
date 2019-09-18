package com.example.test.springbootapp.controller;

import static net.logstash.logback.argument.StructuredArguments.v;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.springbootapp.dto.TestObj;
import com.example.test.springbootapp.service.TestService;

@RestController
@RequestMapping("/")
public class LogTestController {

	@Autowired
	TestService testService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping
	public String testLog() {
		String txID = UUID.randomUUID().toString();
		ThreadContext.put("txID", txID);

		log.info("this is a test log message", v("value1", 1));

		log.info("this is a 2nd test log message", v("value2", 2));

		TestObj testObj = new TestObj(11111, "content of test obj");

		log.info("testing obj in context", v("obj1", testObj));

		// the txID context will be logged within this method also
		testService.testNestedLog();
		
		return "ok!";
	}

	@GetMapping("exception")
	public String testExceptionLog() {
		String txID = UUID.randomUUID().toString();
		ThreadContext.put("txID", txID);
		
		try {
			throw new Exception("test exception");
		} catch (Exception e) {
			log.error("caught an error!", e);
		}
		return "ok!";
	}
}
