/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.Random; //to be deleted



/**
 *
 * @author Mateusz
 */
public class IOmaps {
    double calculateDistance(String idestination, String location) {
        /*
         *
         * temorary script - to be replaced with Google Maps etc.
         *
         */
        Random randomno = new Random();
        double distance = randomno.nextDouble()*10;
        return distance;
    }

    String getLocation(String iuserType) {      
        switch (iuserType) {
            case "DRIVER":
                String driverStatus = "AVAILABLE";
                /*database table - driver*/
                break;
            case "CUSTOMER":
                /*database table - customer*/
                break;
            case "DISPATCHER":
                /*database table - dispatcher*/
        }
           
         /*
         *
         * temorary script - to be replaced with Google Maps etc.
         *
         */
        return "Cork";
    }
}


