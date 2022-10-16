package SpringBoot.app.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Bean("clienteRest") //Registra restTamplate
    public RestTemplate resgisterRestTemplate() {
        return new RestTemplate();
    }
    
}
