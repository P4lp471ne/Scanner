package com.example.scanner.logic.datatypes.requestTypes;

import java.util.List;

public class Report {
    private String id;
    private List<ScannedProduct> productLine;

    public Report(String id, List<ScannedProduct> line) {
        this.id = id;
        productLine = line;
    }

    public List<ScannedProduct> getProductLine() {
        return productLine;
    }

    public void setProductLine(List<ScannedProduct> productLine) {
        this.productLine = productLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
