# springboot-json-log

We can use `ThreadContext` to add in additional context to all logs within 1 flow, most commonly transanction id:

```java
String txID = UUID.randomUUID().toString();
ThreadContext.put("txID", txID);
```

## sample log output

```json
# first call to /
{"@timestamp":"2019-09-18T11:08:53.980+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":36,"thread":"http-nio-8080-exec-1","txID":"c25f44b7-7c12-4f5a-89da-7118f533007f","value1":1,"msg":"this is a test log message"}
{"@timestamp":"2019-09-18T11:08:53.997+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":42,"thread":"http-nio-8080-exec-1","txID":"c25f44b7-7c12-4f5a-89da-7118f533007f","value2":2,"msg":"this is a 2nd test log message"}
{"@timestamp":"2019-09-18T11:08:53.997+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":50,"thread":"http-nio-8080-exec-1","txID":"c25f44b7-7c12-4f5a-89da-7118f533007f","obj1":{"id":11111,"content":"content of test obj"},"msg":"testing obj in context"}
{"@timestamp":"2019-09-18T11:08:54.008+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.service.TestService","method":"testNestedLog","file":"TestService.java","line":13,"thread":"http-nio-8080-exec-1","txID":"c25f44b7-7c12-4f5a-89da-7118f533007f","msg":"test log inside method call"}

# second call, note the different txID
{"@timestamp":"2019-09-18T11:09:18.203+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":36,"thread":"http-nio-8080-exec-2","txID":"f26b7b85-7146-4233-9286-a581decd1672","value1":1,"msg":"this is a test log message"}
{"@timestamp":"2019-09-18T11:09:18.203+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":42,"thread":"http-nio-8080-exec-2","txID":"f26b7b85-7146-4233-9286-a581decd1672","value2":2,"msg":"this is a 2nd test log message"}
{"@timestamp":"2019-09-18T11:09:18.213+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.controller.LogTestController","method":"testLog","file":"LogTestController.java","line":50,"thread":"http-nio-8080-exec-2","txID":"f26b7b85-7146-4233-9286-a581decd1672","obj1":{"id":11111,"content":"content of test obj"},"msg":"testing obj in context"}
{"@timestamp":"2019-09-18T11:09:18.213+08:00","appName":"test-log-app","level":"INFO","class":"com.example.test.springbootapp.service.TestService","method":"testNestedLog","file":"TestService.java","line":13,"thread":"http-nio-8080-exec-2","txID":"f26b7b85-7146-4233-9286-a581decd1672","msg":"test log inside method call"}
```
