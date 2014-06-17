/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
package eu.factorx.awac.common;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AccountStatusType {
    UNACTIVE(0),
    ACTIVE(1);
    
    public final int value;

    private AccountStatusType(int value) {
            this.value = value;
    }
       
    /**
     * Return a list of accountStatusTypes to select from
     * 
     **/
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();

        for (AccountStatusType asType : AccountStatusType.values()) {
            options.put(asType.name(), asType.name());
        }
                
        return options;
    }

};   
