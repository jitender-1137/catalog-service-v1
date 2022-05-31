package com.yaari.ms.catalogservice;

import com.yaari.ms.resource.server.EnableResourceServer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// todo numItems

@SpringBootApplication
@NoArgsConstructor
@Getter
@Setter
@EnableJpaRepositories
@ComponentScan("com.yaari")
@EnableCaching
@EnableResourceServer
public class CategoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryserviceApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
