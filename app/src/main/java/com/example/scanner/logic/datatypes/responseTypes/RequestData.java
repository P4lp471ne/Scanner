package com.example.scanner.logic.datatypes.responseTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestData extends ShortRequestDescription {
    private List<ProductRequestLine> lines;
    private Warehouse warehouseIn;

    @JsonProperty("warehouse_in")
    public Warehouse getWarehouseIn() {
        return warehouseIn;
    }

    public void setWarehouseIn(Warehouse warehouseIn) {
        this.warehouseIn = warehouseIn;
    }

    @JsonProperty("warehouse_out")
    public Warehouse getWarehouseOut() {
        return warehouseOut;
    }

    public void setWarehouseOut(Warehouse warehouseOut) {
        this.warehouseOut = warehouseOut;
    }

    private Warehouse warehouseOut;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    private int id;

    RequestData(){
        super();
    }

    @JsonProperty("product_request_lines")
    public List<ProductRequestLine> getLines() {
        return lines;
    }

    public void setLines(List<ProductRequestLine> lines) {
        this.lines = lines;
    }
}
