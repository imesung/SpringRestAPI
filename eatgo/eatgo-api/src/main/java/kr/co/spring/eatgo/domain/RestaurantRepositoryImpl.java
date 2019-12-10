package kr.co.spring.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1004L,"Bob zip", "Seoul"));
        restaurants.add(new Restaurant(2020L,"Kimbob heaven", "Seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        Restaurant restaurant = restaurants.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElse(null);
        return restaurant;
    }
}
