package com.example.scanner.logic.datatypes.responseTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Warehouse {
    private int id;
    private int corporationId;
    private String nameView;
    private boolean isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("corporation_id")
    public int getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(int corporationId) {
        this.corporationId = corporationId;
    }

    @JsonProperty("name_view")
    public String getNameView() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView = nameView;
    }

    @JsonProperty("is_delete")
    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
