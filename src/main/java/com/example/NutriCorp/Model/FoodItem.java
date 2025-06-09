package com.example.NutriCorp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    Float cal;
    Float fats;
    Float protein;
    Float carbs;
}
