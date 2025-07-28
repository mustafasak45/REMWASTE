package com.opensource.pages.orangehrm.enums;

public enum Status {

    ENABLED("Enabled"),
    DISABLED("Disabled");

    public final String text;

    Status(String text){
        this.text = text;
    }

}
