package com.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBatchApplicationChunkApplication {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplicationChunkApplication.class, args);
	}

	/**Se configura la ejecución del Job en el CommandLineRunner para iniciar junto con la aplicación*/
	@Bean
	CommandLineRunner init() {
		return args -> {
			JobParameters parameters = new JobParametersBuilder()
					.addString("name", "chunkJob")
					.addLong("id", System.currentTimeMillis())
					.addDate("date", new Date())
					.toJobParameters();
			jobLauncher.run(job, parameters);
		};
	}
	
}
