package com.example.scanner.logic.datatypes.responseTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"product_request_id"})
public class ProductRequestLine {
    private Product product;
    private int id;
    private int quantity;
    private int scanned;

    public int getScanned() {
        return scanned;
    }

    public ProductRequestLine() {
    }

    public ProductRequestLine(Product product, Integer integer) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void decreaseQuantity(){
        quantity--;
        scanned++;
    }

}
