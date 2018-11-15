package com.man.katalogmovie.utils;

import java.util.Locale;

public class Language {
    public static String getLanguage(){
        String lang = Locale.getDefault().getCountry().toLowerCase();
        switch (lang){
            case "id":
                break;
            default:
                lang = "en";
                break;
        }
        return lang;
    }
}
