package kr.co.spring.eatgo.interfaces;

import kr.co.spring.eatgo.application.RestaurantService;
import kr.co.spring.eatgo.domain.MenuItem;
import kr.co.spring.eatgo.domain.MenuItemRepository;
import kr.co.spring.eatgo.domain.Restaurant;
import kr.co.spring.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController //restAPI를 쓰는 컨트롤러
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        //id를 줄게 레스토랑의 기본정보와 메뉴정보를 달라.

        return restaurant;
    }

}
