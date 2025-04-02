package com.agz.pokeapi.utils;

public class UtilsPath {


    public static String ObtainId(String url){
        String[] urlParts = url.split("/");
        return urlParts[urlParts.length - 1];
    }

    public static boolean isNumber(String id){
       return  id.matches("\\d+");
    }

    public static boolean isString(String id){
        return id.matches("^[a-zA-Z]+$");
    }
    
    public static String ObtainOffsetAndLimit(String url) {
    	if(url != null) {
    	  	String[] offsetAndLimit = url.split("\\?");
    	  	return offsetAndLimit[offsetAndLimit.length - 1];
    	}
    	return null;
 	
    }

}
