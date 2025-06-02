package com.example.NutriCorp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodApiConfig {

    @Value("${usda.ApiKey}")
    public String foodApiKey;

    @Value("${usda.baseUrl}")
    public String BaseURL;
}
