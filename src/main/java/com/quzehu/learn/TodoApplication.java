package com.quzehu.learn;

import com.quzehu.learn.invoker.Invoker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author quzehu
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TodoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(TodoApplication.class, args);
		Invoker invoker = applicationContext.getBean(Invoker.class);
		invoker.callLoop();
	}
}
