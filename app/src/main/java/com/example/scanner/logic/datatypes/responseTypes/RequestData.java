package com.example.scanner.logic.datatypes.responseTypes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestData extends ShortRequestDescription {
    private List<ProductRequestLine> lines;
    private Warehouse warehouseIn;
    private Warehouse warehouseOut;
    private int id;

    RequestData() {
        super();
    }

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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    @JsonProperty("product_request_lines")
    public List<ProductRequestLine> getLines() {
        return lines;
    }

    public void setLines(@NonNull List<ProductRequestLine> lines) {
        this.lines = lines;
    }
}
