package com.example.NutriCorp.Service;

import com.example.NutriCorp.Model.FoodItem;
import com.example.NutriCorp.Repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    @Autowired
    private ItemRepo it;

    public List<FoodItem> getAllFoodData(){
        return it.findAll();
    }

}
