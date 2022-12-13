package br.com.hugolini.config;

import br.com.hugolini.util.impl.PersonMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile({"dev", "test"})
public class PersonConfig {

    @Bean
    public PersonMapperImpl personMapper() {
        return new PersonMapperImpl();
    }
}
