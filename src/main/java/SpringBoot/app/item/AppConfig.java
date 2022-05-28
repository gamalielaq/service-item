package SpringBoot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Bean("clienteRest") //Registra restTamplate
    @LoadBalanced //Ulizar ribbon para el baleanceo de carga
    public RestTemplate resgisterRestTemplate() {
        return new RestTemplate();
    }
    
}
