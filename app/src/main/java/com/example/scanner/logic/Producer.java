package com.example.scanner.logic;

import com.example.scanner.view.Consumer;


public interface Producer {
    public void subscribe(Consumer consumer);
}
