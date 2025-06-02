package com.example.NutriCorp.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Data
@Table(name = "FoodItems")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ItemId;
    String ItemName;
    Float cal;
    Float fats;
    Float protein;
    Float carbs;

}
