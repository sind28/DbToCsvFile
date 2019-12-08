package com.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.demo.model.DbModel;
// Added Comments
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

@Autowired
public JobBuilderFactory jobBuilderFactory;

@Autowired
public StepBuilderFactory stepBuilderFactory;

@Autowired
public DataSource dataSource;

@Bean
public DataSource dataSource() {
	
	DriverManagerDataSource dataSource= new DriverManagerDataSource();
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	dataSource.setUrl("jdbc:mysql://localhost:3306/record");
	dataSource.setUsername("root");
	dataSource.setPassword("root");
	return dataSource;
	
}
@Bean
public JdbcCursorItemReader<DbModel> reader(){
	JdbcCursorItemReader<DbModel> reader = new JdbcCursorItemReader<DbModel>();
	reader.setDataSource(dataSource);
	reader.setSql("select employee_id,employee_name,employee_relatives,transaction_id from employee_table");
    reader.setRowMapper(new DbRowMapper() );
	return reader;
	
}
public class DbRowMapper implements RowMapper<DbModel>{
	
	@Override
	public DbModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		DbModel db = new DbModel();
		db.setEmployee_id(rs.getInt("employee_id"));
		db.setEmployee_name(rs.getString("employee_name"));
		db.setEmployee_relatives(rs.getString("employee_relatives"));
		db.setTransaction_id(rs.getInt("transaction_id"));
		return db;
	}
	
}


@Bean
public CustomItemProcessor processor() {
	return new CustomItemProcessor() ;
}

@Bean
public FlatFileItemWriter<DbModel> writer(){
	FlatFileItemWriter<DbModel> writer = new FlatFileItemWriter<DbModel>();
	writer.setResource(new ClassPathResource("path.csv"));
	writer.setLineAggregator(new DelimitedLineAggregator<DbModel>(){{
		setDelimiter(",");
		setFieldExtractor(new BeanWrapperFieldExtractor<DbModel>() {{
			setNames(new String[] {"employee_id","employee_name","employee_relatives","transaction_id"});
		}});
	}});
	
	
	return writer;
	
}
@Bean
public Step step1() {
 return stepBuilderFactory.get("step1").<DbModel, DbModel> chunk(1)
   .reader(reader())
   .processor(processor())
   .writer(writer())
   .build();
}
@Bean
public Job exportUserJob() {
 return jobBuilderFactory.get("exportUserJob")
   .incrementer(new RunIdIncrementer())
   .flow(step1())
   .end()
   .build();
}




	
}
