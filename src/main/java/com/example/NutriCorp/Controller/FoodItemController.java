package com.example.NutriCorp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class FoodItemController {


    @GetMapping("/getFoods")
    public String getAllFood(){
        return "Hello" ;
    }
}
