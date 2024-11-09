package com.stackbytes;

import lombok.Getter;

@Getter
public enum UtilTypes {
    OXYGEN("oxygen"),
    DEFIBRILLATOR("defibrillator"),
    MEDKIT("medkit");


    private String type;

    UtilTypes(String type){
        this.type = type;
    }
}
