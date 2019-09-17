package com.example.test.springbootapp;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		ThreadContext.put("value1", "1");

		// sample {"@timestamp":"2019-09-17T15:31:31.243+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":29,"thread":"main","value1":"1","txID":"6e057f1b-c8c0-45ae-a69e-e6b30e2b135b","msg":"this is a test log message"}
		log.info("this is a test log message");

		//ThreadContext.remove("value1");
		ThreadContext.put("value2", "2");

		// sample {"@timestamp":"2019-09-17T15:31:31.258+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":34,"thread":"main","value2":"2","value1":"1","txID":"6e057f1b-c8c0-45ae-a69e-e6b30e2b135b","msg":"this is a 2nd test log message"}
		log.info("this is a 2nd test log message");
		
		TestObj testObj = new TestObj(11111, "content of test obj");
		
		ThreadContext.put("testObj1", new Gson().toJson(testObj));
		// sample {"@timestamp":"2019-09-17T16:17:55.052+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.SpringbootAppApplication","method":"testLogging","file":"SpringbootAppApplication.java","line":70,"thread":"main","txID":"eedfa29e-29b0-40c6-9ff6-fb41c131cc1c","value2":"2","value1":"1","testObj1":"{\"id\":11111,\"content\":\"content of test obj\"}","msg":"testing obj in context"}
		log.info("testing obj in context");
		
		ThreadContext.remove("testObj1");
		
		try {
			throw new Exception("test exception");
		} catch (Exception e) {
			log.error("caught an error!", e);
		}
		
		ThreadContext.clearAll();
	}

}
