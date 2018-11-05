package com.project.presentation.client.console.log;

/**
 * Created by john on 05.07.2015.
 */
public class Console {

    public static void log(String message){
        if(message != null && message.length() > 0){
            logger(message);
        }
    }

    private static native void logger (String message) /*-{
        console.log(message);
    }-*/;
}
