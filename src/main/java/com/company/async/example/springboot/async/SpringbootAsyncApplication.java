package com.company.async.example.springboot.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;


@SpringBootApplication
public class SpringbootAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAsyncApplication.class, args).close();
	}

}
