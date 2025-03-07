package com.example.controller;

import com.example.entity.Users;
import com.example.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller
public class TestController {

	@Inject
	private UserRepository userRepository;

	@Get
	public String hello() {
		return "Hello World";
	}

	@Get(value = "/query")
	public String getWordWithQuery(@QueryValue("q") String query) {
		return "Query is " + query;
	}

	@Get(value = "/query/{id}")
	public HttpResponse<String> getWordByPath(@PathVariable("id") String id) {
		return HttpResponse.ok("Path is " + id);
	}

	@Post(value = "/users")
	public HttpResponse<Users> jpaTest(@Body String name) {
		Users users = new Users(name);
		return HttpResponse.created(userRepository.save(users));
	}

}
