/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import org.json.*;

/**
 *
 * @author Mateusz
 */
public class IOmap {
    public double calculateDistance(String idestination, String ilocation) {
	try {
            String response = this.sendRequest(idestination, ilocation);
               
            JSONObject obj = new JSONObject(response)
                                        .getJSONArray("rows")
                                        .getJSONObject(0)
                                        .getJSONArray ("elements")
                                        .getJSONObject(0)
                                        .getJSONObject("distance");
            return obj.getDouble("value")/1000;
	} catch (Exception ex) {
            
	}
        return 0;
    }
    
    double calculateDuration(String idestination, String ilocation) {
	try {
            String response = this.sendRequest(idestination, ilocation);

            JSONObject obj = new JSONObject(response)
                                        .getJSONArray("rows")
                                        .getJSONObject(0)
                                        .getJSONArray ("elements")
                                        .getJSONObject(0)
                                        .getJSONObject("duration");
            return obj.getInt("value")/60;
	} catch (Exception ex) {
            
	}
        return 0;
    }
        
    private String sendRequest(String idestination, String ilocation) throws Exception {
        try {
            String location=ilocation.replace(" ",",");
            String destination=idestination.replace(" ",",");
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
                    location + "&destinations=" + destination + "&key=AIzaSyBB5o7ib_q9Q5Jk16daxVUHEhIB7LZjHyU");
            Scanner scan = new Scanner(url.openStream());
            String response = new String();
            while (scan.hasNext()) {
                response += scan.nextLine();
            }
            scan.close();
            return response;
        } catch(Exception e) {
            throw new Exception();
        }
    }
    
    public String getLocation(String iuserType) {
        String location = "";
        switch (iuserType) {
            case "DRIVER":
                String[] locationArray = {"South Mall", "Douglas", "Parnell Place", "Grand Parade", "College Road"};
                int idx = new Random().nextInt(locationArray.length);
                location = (locationArray[idx]);
                break;
            case "CUSTOMER":
                try {                
                    URL url = new URL("https://context.skyhookwireless.com/accelerator/ip?version=2.0&key=eJwVwcENACAIBLC3w5CcIgJPomEp4-5qW0vFJ824bDX2SAiJDiXuPAhzBnUs5OMRci4OAAsq&user=eval");
                    Scanner scan = new Scanner(url.openStream());
                    String response = new String();
                    while (scan.hasNext()) {
                        response += scan.nextLine();
                    }
                    scan.close();


                    JSONObject obj = new JSONObject(response)
                                                .getJSONObject("data")
                                                .getJSONObject("location");
                    location = String.valueOf(obj.getDouble("latitude")) + "," 
                            +  String.valueOf(obj.getDouble("longitude"));
                    break;
                } catch(Exception e) {
                    //throw new Exception();
                }
        }
        return location;
    }
}


