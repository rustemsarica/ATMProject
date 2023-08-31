package com.rustemsarica.ATMProject.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.rustemsarica.ATMProject.audit.AuditorAwareImpl;

@Configuration
public class AuditorAwareBean {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
