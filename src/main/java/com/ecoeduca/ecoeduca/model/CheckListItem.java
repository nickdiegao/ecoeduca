package com.ecoeduca.ecoeduca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CheckListItem {
    @Column(nullable = false)
    private String item;

    @Column(nullable = false)
    private boolean feito;

    public CheckListItem() {}

    public CheckListItem(String item, boolean feito) {
        this.item = item;
        this.feito = feito;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }
}
