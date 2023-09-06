package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.batch.model.Persona;
import com.batch.steps.PersonaItemProcessor;
import com.batch.steps.PersonaItemReader;
import com.batch.steps.PersonaItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilder;
	
	@Bean
	PersonaItemReader itemReader() {
		return new PersonaItemReader();
	}
	
	@Bean
	PersonaItemProcessor itemProcessor() {
		return new PersonaItemProcessor();
	}
	
	@Bean
	PersonaItemWriter itemWriter() {
		return new PersonaItemWriter();
	}
	
	@Bean
	/**Método que crea un TaskExecutor y se le definen parametros*/
	TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		/**Define la cantidad inicial de hilos al arrancar la aplicación*/
		taskExecutor.setCorePoolSize(1);
		/**Define el máximo de hilos adicionales a desplegar, en caso que la cantidad inicial no sea suficientes*/
		taskExecutor.setMaxPoolSize(5);
		/**Define el máximo de tareas en cola, estos estarán en memoria esperando a ser procesados*/
		taskExecutor.setQueueCapacity(5);
		return taskExecutor;
	}
	
	/**Se definen los parametros del Step
	 * Al trabajar con Chunks, el reader, processor y writer es pueden hacer en un solo Step*/
	@Bean
	Step readFile() {
		/**Se define el nombre del Step dentro del Spring Context*/
		return stepBuilder.get("readFile")
				/**Se define el objeto de entrada, el objeto de salida
				 * y la cantidad de elementos que tendrá cada lote al ingresar al Step*/
				.<Persona, Persona>chunk(5)
				/**Se define el reader*/
				.reader(itemReader())
				/**Se define el processor*/
				.processor(itemProcessor())
				/**Se define el writer*/
				.writer(itemWriter())
				/**Se carga el TaskExecutor a SpringBatch para que él administre los hilos*/
				.taskExecutor(taskExecutor())
				.build();
	}
	
	/**Se definen los parametros del Job*/
	@Bean
	Job job() {
		/**Se define el nombre del Job dentro del Spring Context*/
		return jobBuilder.get("readFileWithChunk")
				/**Se define el Step de inicio*/
				.start(readFile())
				/**Suponiendo que existen más pasos, puede definirse el Step siguiente con .next()*/
				.build();
	}
}
