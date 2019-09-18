package com.example.test.springbootapp;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static net.logstash.logback.argument.StructuredArguments.v;

import com.google.gson.Gson;

@SpringBootApplication
public class SpringbootAppApplication {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
	}
	
	private class TestObj {
		
		private long id;
		private String content;
		
		public TestObj(long id, String content) {
			this.id = id;
			this.content = content;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	@PostConstruct
	public void testLogging() {
		String txID = UUID.randomUUID().toString();

		ThreadContext.put("txID", txID);

		// sample {"@timestamp":"2019-09-18T10:25:07.662+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":60,"thread":"main","txID":"cbd75e44-3ccd-41a0-a19c-e537476befe5","value1":1,"msg":"this is a test log message"}

		log.info("this is a test log message", v("value1", 1));

		// sample {"@timestamp":"2019-09-18T10:25:07.693+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":63,"thread":"main","txID":"cbd75e44-3ccd-41a0-a19c-e537476befe5","value2":2,"msg":"this is a 2nd test log message"}

		log.info("this is a 2nd test log message", v("value2", 2));
		
		TestObj testObj = new TestObj(11111, "content of test obj");
		
		// sample {"@timestamp":"2019-09-18T10:26:20.142+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":71,"thread":"main","txID":"d537dccd-0534-45f8-810d-960768f85585","obj1":{"id":11111,"content":"content of test obj"},"msg":"testing obj in context"}

		log.info("testing obj in context", v("obj1", testObj));
		
		
		try {
			throw new Exception("test exception");
		} catch (Exception e) {
			log.error("caught an error!", e);
		}
		
	}

}
