package kr.co.spring.eatgo.domain;

public class MenuItem {
    private final String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
