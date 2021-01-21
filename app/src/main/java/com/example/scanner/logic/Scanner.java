package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.requestTypes.Report;
import com.example.scanner.logic.datatypes.requestTypes.ScannedProduct;
import com.example.scanner.logic.datatypes.responseTypes.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;

public class Scanner {
    private Map<Product, ScannedProduct> requestLine = new HashMap<>();
    private boolean active;
    private String id;
    private boolean doubleScan;

    protected boolean isActive() {
        return active;
    }

    protected void scan(Product product) {
        if (!requestLine.containsKey(product)) requestLine.put(product, new ScannedProduct());
        ScannedProduct prod = requestLine.get(product);
        prod.setQuantity(prod.getQuantity() + 1);
//    protected void setRequest(RequestData data){
//        requestLines = data.getLines();
//        id = data.getExtId();
//    }
    }

    protected boolean isDoubleScan() {
        return doubleScan;
    }

    public void setDoubleScan(boolean mode) {
        doubleScan = mode;
    }

    protected void start() {
        active = true;
    }

    protected Report getReport() {
        return new Report(id, requestLine.values().stream().collect(toCollection(ArrayList::new)));
    }

    protected void cancel() {
        requestLine = null;
        active = false;
    }
}
