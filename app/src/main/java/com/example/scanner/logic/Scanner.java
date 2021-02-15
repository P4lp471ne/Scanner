package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.requestTypes.Report;
import com.example.scanner.logic.datatypes.requestTypes.ScannedProduct;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class Scanner {
    private List<ScannedProduct> requestLine = new ArrayList<ScannedProduct>();
    private boolean active;
    private String id;
    private boolean doubleScan;

    Scanner(String id){
        this.id = id;
    }

    protected boolean isActive() {
        return active;
    }

    protected void scan(String barcode) {
        ScannedProduct prod = new ScannedProduct();
        prod.setBarcode(barcode);
        requestLine.add(prod);
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
        return new Report(id, requestLine);
    }

    protected void cancel() {
        requestLine.clear();
        active = false;
    }
}
