package com.example.NutriCorp.Model;

import lombok.Data;

@Data
public class ChatMessage {
    private String role;
    private String content;

    public ChatMessage(String role, String content){
        this.role = role;
        this.content = content;
    }
}
