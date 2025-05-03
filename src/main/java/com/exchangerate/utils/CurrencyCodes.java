package com.exchangerate.utils;

import java.util.*;

public abstract class CurrencyCodes {

    private static final Map<String, String> CURRENCY_CODES;

        static {
            CURRENCY_CODES = new HashMap<>();

            //Modify this dictionary to add more supported currencies from the Exchange Rate API.
            // Format: CURRENCY_CODES.put(display_name, currency_code);

            CURRENCY_CODES.put("Dólares estadounidenses","USD");
            CURRENCY_CODES.put("Pesos argentinos","ARS");
            CURRENCY_CODES.put("Reales brasileños","BRL");
            CURRENCY_CODES.put("Pesos colombianos", "COP");
        }

    public static String getCode(String name){
            return CURRENCY_CODES.get(name);
        }

    public static List<String> getNameList() {
            return new ArrayList<>(CURRENCY_CODES.keySet());
        }

}
