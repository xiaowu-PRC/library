package com.library.library;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class book{
    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty desc = new SimpleStringProperty();

    public int getBookID() {
        return this.id.get();
    }

    public void setBookID(int id) {
        this.name.set(String.valueOf(id));
    }

    public String getName() {
        return name.get();
    }

    public void setName(String firstNameStr) {
        name.set(firstNameStr);
    }

    public String getDesc() {
        return desc.get();
    }

    public void setDesc(String lastNameStr) {
        desc.set(lastNameStr);
    }
}
