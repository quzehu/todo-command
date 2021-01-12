package com.quzehu.learn;

import com.quzehu.learn.invoker.Invoker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author quzehu
 */
@SpringBootApplication
@MapperScan("com.quzehu.learn.mapper")
public class TodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
		Invoker invoker = new Invoker();
		invoker.callLoop();
	}
}
