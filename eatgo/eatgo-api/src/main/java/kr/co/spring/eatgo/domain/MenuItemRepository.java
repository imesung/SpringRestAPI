package kr.co.spring.eatgo.domain;

import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantId(Long restaurentId);

}
