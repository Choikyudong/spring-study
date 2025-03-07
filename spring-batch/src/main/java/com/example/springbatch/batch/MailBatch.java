package com.example.springbatch.batch;

import com.example.springbatch.entity.Users;
import com.example.springbatch.service.MailService;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MailBatch {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final MailService mailService;

	// 1. User 데이터를 읽어오는 Reader
	@Bean
	public JpaPagingItemReader<Users> usersReader() {
		return new JpaPagingItemReaderBuilder<Users>()
				.name("userReader")
				.entityManagerFactory(entityManagerFactory)
				.queryString("SELECT u FROM Users u") // 모든 유저 가져오기
				.pageSize(10) // 10명씩 처리
				.build();
	}

	// 2. 메일을 보내는 Processor
	@Bean
	public ItemProcessor<Users, Users> mailProcessor() {
		return user -> {
			mailService.sendMail(
					user.getEmail(),
					"안녕하세요, " + user.getName() + "님!",
					"테스트입니다~~"
			);
			return user;
		};
	}

	// 3. 단순 로그 출력 (Writer)
	@Bean
	public ItemWriter<Users> mailLogWriter() {
		return users -> users.forEach(user -> log.info("메일 전송 완료: " + user.getEmail()));
	}

	// 4. Step 설정
	@Bean
	public Step mailStep() {
		return new StepBuilder("mailStep", jobRepository)
				.<Users, Users> chunk(10, platformTransactionManager)
				.reader(usersReader())
				.processor(mailProcessor())
				.writer(mailLogWriter())
				.build();
	}

	// 5. Job 설정
	@Bean
	@Qualifier("mailJob")
	public Job mailJob() {
		return new JobBuilder("mailJob", jobRepository)
				.start(mailStep())
				.build();
	}

}
