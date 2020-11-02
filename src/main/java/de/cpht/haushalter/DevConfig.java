package de.cpht.haushalter;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevConfig {
    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }
}
