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

    String getLocation() {
         /*
         *
         * temorary script - to be replaced with Google Maps etc.
         *
         */
        return "Cork";
    }
}


