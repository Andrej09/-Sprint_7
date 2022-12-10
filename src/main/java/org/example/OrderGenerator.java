package org.example;

public class OrderGenerator{

    public CreatingOrder creatingOrder(){
        return new CreatingOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                null);
    }
}