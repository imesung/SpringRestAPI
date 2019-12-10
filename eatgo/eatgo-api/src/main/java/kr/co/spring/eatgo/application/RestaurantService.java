package kr.co.spring.eatgo.application;

import kr.co.spring.eatgo.domain.MenuItem;
import kr.co.spring.eatgo.domain.MenuItemRepository;
import kr.co.spring.eatgo.domain.Restaurant;
import kr.co.spring.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants;
    }

    public Restaurant getRestaurant(Long id){

        Restaurant restaurant = restaurantRepository.findById(id);

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItem(menuItems);
        return restaurant;
    }
}
