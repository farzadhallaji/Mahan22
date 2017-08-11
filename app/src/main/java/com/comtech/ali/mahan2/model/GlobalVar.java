package com.comtech.ali.mahan2.model;

/**
 * Created by Techno Service on 7/20/2017.
 */
public class GlobalVar {

    public static int SELECTED_ITEM_SICH_OLUB=-1;
    private static String UserID="";
    public static void setUserID(String userID) {UserID = userID;}
    public static String getUserID() {
        return UserID;
    }

    private static String SerViceType="";
    public static void setSerViceType(String userID) {SerViceType = userID;}
    public static String getSerViceType() {
        return SerViceType;
    }


}
