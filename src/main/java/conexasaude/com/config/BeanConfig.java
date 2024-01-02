package conexasaude.com.config;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods(POST.name(), PUT.name(), DELETE.name(), PATCH.name(), GET.name(), OPTIONS.name())
                    .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
                    .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_DISPOSITION)
                    .maxAge(3600);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
