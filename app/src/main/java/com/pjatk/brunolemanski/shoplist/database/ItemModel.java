package com.pjatk.brunolemanski.shoplist.database;

/**
 * Class responsible for item model in database.
 */
public class ItemModel {

    private long id;
    private String title;
    private String price;
    private String quantity;
    private boolean done;

    public ItemModel(long id, String title, String price, String quantity, boolean done) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}