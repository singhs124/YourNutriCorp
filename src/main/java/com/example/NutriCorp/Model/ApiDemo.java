package com.example.NutriCorp.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "ApiDemoTable")
public class ApiDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pid;
    Integer id;
    Integer userid;
    String title;
    String body;
}
