package com.recodesolutions.itticket;

import com.recodesolutions.itticket.audit.AuditorAwareImpl;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class ItTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItTicketApplication.class, args);
	}

	@Bean
	@RequestScope
	public RequestHeaderData requestScopeHeaderData() {
		return new RequestHeaderData();
	}
	@Bean
	public AuditorAware<Long> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				TO Enable this with the domain
//				registry.addMapping("/**").allowedOrigins("http://localhost:8080");
				registry.addMapping("/**");
			}
		};
	}

}
