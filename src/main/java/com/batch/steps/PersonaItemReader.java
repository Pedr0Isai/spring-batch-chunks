package com.batch.steps;

import java.nio.charset.StandardCharsets;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.batch.model.Persona;

import lombok.extern.slf4j.Slf4j;

/**El reader hereda de la clase FlatFileItemReader que facilita la lectura de documentos*/
@Slf4j
public class PersonaItemReader extends FlatFileItemReader<Persona> {
	/**Creamos el constructor en donde se definen los parametros del ItemReader*/
	public PersonaItemReader(){
		log.info("------------> Inicio del paso de LECTURA <------------");
		
		/**Nombre del Item*/
		setName("readPersona");
		/**Ruta donde encontrará el archivo a leer*/
		setResource(new ClassPathResource("personas.csv"));
		/**Cuantas lineas debe saltar antes de leer el documento*/
		setLinesToSkip(1);
		/**Especifica la codificación del archivo, NO opcional*/
		setEncoding(StandardCharsets.UTF_8.name());
		/**El LineMapper define como se leera el archivo*/
		setLineMapper(getLineMapper());
		
		log.info("------------> Fin del paso de LECTURA <------------");
	}
	
	public LineMapper<Persona> getLineMapper(){
		/**Con DefaultLineMapper se configuran las propiedades de lectura del archivo*/
		DefaultLineMapper<Persona> lineMapper = new DefaultLineMapper<>();
		/**Con DelimitedLineTokenizer se definen un limitador para cada propiedad*/
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		String[] columnas = new String[]{"nombre","apellido","edad"};
		int[] indexColumnas = new int[] {0,1,2};
		
		/**Define el nombre de las columnas*/
		lineTokenizer.setNames(columnas);
		/**Define el indice de cada columna*/
		lineTokenizer.setIncludedFields(indexColumnas);
		/**Define el caracter separador*/
		lineTokenizer.setDelimiter(",");
		
		/**Se mapean las columnas con las propiedades del objeto Persona*/
		BeanWrapperFieldSetMapper<Persona> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Persona.class);
		
		/**Agregar las configuraciones anteriores al DefaultLineMapper*/
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
	}
}
