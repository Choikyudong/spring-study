package com.example.springbatch.batch;

import com.example.springbatch.entity.Users;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileBatch {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final EntityManagerFactory entityManagerFactory;

	// 1. csv 파일을 읽어오는 Reader
	@Bean
	public FlatFileItemReader<Users> csvReader() {
		FlatFileItemReader<Users> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("users.csv"));
		reader.setLinesToSkip(1); // 헤더 줄 건너뛰기
		reader.setLineMapper(new DefaultLineMapper<>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames("name", "email");
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(Users.class);
			}});
		}});
		return reader;
	}

	// 2. Users에 관한 처리 로직
	@Bean
	public ItemProcessor<Users, Users> usersDataProcessor() {
		return user -> {
			// 데이터 처리 로직 (예: 필터링, 수정 등)
			return user;
		};
	}

	// 3. ItemWriter로 데이터를 DB에 저장
	@Bean
	public ItemWriter<Users> usersWriter() {
		JpaItemWriter<Users> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	// 4. Step 설정
	@Bean
	public Step fileStep() {
		return new StepBuilder("fileStep", jobRepository)
				.<Users, Users> chunk(10, platformTransactionManager)
				.reader(csvReader())
				.processor(usersDataProcessor())
				.writer(usersWriter())
				.build();
	}

	// 5. Job 설정
	@Bean
	@Qualifier("fileJob")
	public Job fileJob() {
		return new JobBuilder("fileJob", jobRepository)
				.start(fileStep())
				.build();
	}

}
