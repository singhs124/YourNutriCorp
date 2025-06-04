package com.example.NutriCorp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIApiConfig {

    @Value("${openAI.ApiKey}")
    public String openApiKey;
}
