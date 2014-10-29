package com.koodlelab.bubbletea;

import org.mongodb.morphia.annotations.Id;

public class Order {
    @Id
    private String id;
    private DrinkType drinkType;
    private String size;
    private String drinker;
    private String[] selectedOptions;
    private long shopId;

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public String getSize() {
        return size;
    }

    public String getDrinker() {
        return drinker;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public String getId() {
        return id;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
}
