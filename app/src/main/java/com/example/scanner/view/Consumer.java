package com.example.scanner.view;

import com.example.scanner.logic.datatypes.responseTypes.Product;

public interface Consumer {
    public void listen(Product product);
}
