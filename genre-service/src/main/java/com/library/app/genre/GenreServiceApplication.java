package com.library.app.genre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GenreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenreServiceApplication.class, args);
    }

}
