package com.example.scanner.logic.datatypes.responseTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {"corporation_id", "is_delete"})
public class Product {
    boolean isDelete;
    private int id;
    private CorportationId corportationId;
    private String nameView;
    private String productCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CorportationId getCorportationId() {
        return corportationId;
    }

    public void setCorportationId(CorportationId corportationId) {
        this.corportationId = corportationId;
    }

    @JsonProperty("name_view")
    public String getNameView() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView = nameView;
    }

    @JsonProperty("product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
