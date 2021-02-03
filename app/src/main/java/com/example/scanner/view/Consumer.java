package com.example.scanner.view;

import com.example.scanner.logic.datatypes.responseTypes.Product;

public interface Consumer {
    void notifyScan();

    public boolean listen(Product product);
}
