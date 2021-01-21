package com.example.scanner.logic;

import com.example.scanner.view.Consumer;
import com.example.scanner.view.activities.AbstractViewHolder;


public interface Producer {
    public void subscribe(Consumer consumer);
}
