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

		// sample
		// {"@timestamp":"2019-09-18T10:25:07.662+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":60,"thread":"main","txID":"cbd75e44-3ccd-41a0-a19c-e537476befe5","value1":1,"msg":"this
		// is a test log message"}

		log.info("this is a test log message", v("value1", 1));

		// sample
		// {"@timestamp":"2019-09-18T10:25:07.693+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":63,"thread":"main","txID":"cbd75e44-3ccd-41a0-a19c-e537476befe5","value2":2,"msg":"this
		// is a 2nd test log message"}

		log.info("this is a 2nd test log message", v("value2", 2));

		TestObj testObj = new TestObj(11111, "content of test obj");

		// sample
		// {"@timestamp":"2019-09-18T10:26:20.142+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":71,"thread":"main","txID":"d537dccd-0534-45f8-810d-960768f85585","obj1":{"id":11111,"content":"content
		// of test obj"},"msg":"testing obj in context"}

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
