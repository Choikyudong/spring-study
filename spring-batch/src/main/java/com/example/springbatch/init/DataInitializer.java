package com.example.springbatch.init;

import com.example.springbatch.entity.Users;
import com.example.springbatch.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final UsersRepository usersRepository;

	// 테스트용 사용자 데이터를 추가한다.
	@PostConstruct
	public void init() {
		if (usersRepository.count() == 0) {
			List<Users> usersList = new ArrayList<>();
			usersList.add(new Users("사용자1", "slgmgm@naver.com"));
			usersList.add(new Users("사용자2", "kyudongtest1@gmail.com"));
			usersRepository.saveAll(usersList);
		}
	}

}
