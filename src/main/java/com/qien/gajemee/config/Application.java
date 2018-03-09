package com.qien.gajemee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.qien.gajemee.aspect.LoginAspect;


@Configuration
@EnableAspectJAutoProxy
public class Application {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public LoginAspect loginAspect() {
        return new LoginAspect();
    }
}

