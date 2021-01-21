package com.example.scanner.view;

import com.example.scanner.logic.datatypes.responseTypes.ShortRequestDescription;

import java.util.List;

public interface ReqListCallback {
    void action(List<ShortRequestDescription> lst);
}
