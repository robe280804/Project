package com.app_fitness.common_files;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedBuilder")
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    @Qualifier("standardBuilder")
    public WebClient.Builder standardClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient authServiceWebClient(@Qualifier("loadBalancedBuilder") WebClient.Builder builder){
        return builder.baseUrl("http://AUTH-SERVICE")
                .build();
    }
}
