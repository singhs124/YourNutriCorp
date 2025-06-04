package com.example.NutriCorp.Service;


import com.example.NutriCorp.Model.ChatMessage;
import com.example.NutriCorp.Model.ChatRequest;
import com.example.NutriCorp.config.OpenAIApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIApiService {

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    OpenAIApiConfig openAIApiConfig ;
    private String SYTEM_PROMPT = "You are a food expert. Normalize messy food inputs into clean food items.";

    public void filterInput(String Input){
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system" , SYTEM_PROMPT));
        messages.add(new ChatMessage("user" , Input));

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");
        chatRequest.setMessages(messages);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(openAIApiConfig.openApiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(chatRequest,httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
                );
        System.out.println(response);
    }

}
