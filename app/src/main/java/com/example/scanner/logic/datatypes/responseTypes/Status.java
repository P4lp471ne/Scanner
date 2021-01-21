package com.example.scanner.logic.datatypes.responseTypes;

public enum Status {
    NEW("new"),
    IN_PROGRESS("in_progress"),
    FINISH("finish"),
    CANCELED("canceled");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
