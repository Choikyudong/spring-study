package com.example;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class MironautTestTest {

    @Inject
    @Client("/")
    HttpClient client; //Http 클라이언트 주입

    @Test
    void getTest() {
        String response = client.toBlocking().retrieve("/");
        assertEquals(response, "Hello World");
    }

}
