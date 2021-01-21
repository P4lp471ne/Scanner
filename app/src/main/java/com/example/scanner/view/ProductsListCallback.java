package com.example.scanner.view;

import com.example.scanner.logic.datatypes.responseTypes.ProductRequestLine;
import com.example.scanner.logic.datatypes.responseTypes.RequestData;

import java.util.List;

public interface ProductsListCallback {
    void action(RequestData data);
}
