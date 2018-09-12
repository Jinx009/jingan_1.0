package com.protops.gateway.constants;

import java.util.ResourceBundle;

public enum PlaceHolder {

    PLATENUMBER, INTIME, OUTTIME, MEMBERNUMBER, PARKIP, MOBILE, NAME, ADDRESS;

    public String display() {
        return getPlaceHolder(this.name());
    }

    private String getPlaceHolder(String key) {
        return ResourceBundle.getBundle("placeholder").getString(key);
    }

    public static String display(String key) {
        return ResourceBundle.getBundle("placeholder").getString(key);
    }
}