package com.example.test.springbootapp;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootAppApplication {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
	}

	@PostConstruct
	public void testLogging() {
		String txID = UUID.randomUUID().toString();

		ThreadContext.put("txID", txID);
		ThreadContext.put("value1", "1");

		// sample {"@timestamp":"2019-09-17T15:31:31.243+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":29,"thread":"main","value1":"1","txID":"6e057f1b-c8c0-45ae-a69e-e6b30e2b135b","msg":"this is a test log message"}
		log.info("this is a test log message");

		//ThreadContext.remove("value1");
		ThreadContext.put("value2", "2");

		// sample {"@timestamp":"2019-09-17T15:31:31.258+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":34,"thread":"main","value2":"2","value1":"1","txID":"6e057f1b-c8c0-45ae-a69e-e6b30e2b135b","msg":"this is a 2nd test log message"}
		log.info("this is a 2nd test log message");
		
		
		try {
			throw new Exception("test exception");
		} catch (Exception e) {
			log.error("caught an error!", e);
		}
	}

}
