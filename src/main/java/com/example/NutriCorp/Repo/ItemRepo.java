package com.example.NutriCorp.Repo;

import com.example.NutriCorp.Model.ApiDemo;
import com.example.NutriCorp.Model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<ApiDemo,Integer> {
}
