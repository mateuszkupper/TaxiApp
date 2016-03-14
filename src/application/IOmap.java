/**
* <h1>IOMAP CLASS</h1>
* The class facilitates connection to geolocating web services
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/

package application;

import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import org.json.*;

public class IOmap {
    
    
    /**
    * CALCULATEDISTANCE
    * Calculates distance between two points
    * @param idestination
    * @param ilocation
    * @return distance
    * @throws Exception
    * Code from http://stackoverflow.com/questions/17598074/google-map-in-java-swing
    */     
    public double calculateDistance(String idestination, String ilocation) throws Exception {
	try {
            //send request to server
            String response = this.sendRequest(idestination, ilocation);
            
            //parse JSON object
            JSONObject obj = new JSONObject(response)
                                        .getJSONArray("rows")
                                        .getJSONObject(0)
                                        .getJSONArray ("elements")
                                        .getJSONObject(0)
                                        .getJSONObject("distance");//distance in meters
            
            //if distance > 100km
            if(obj.getDouble("value")/1000>100) {
                throw new Exception();
            } else {
                return obj.getDouble("value")/1000; 
            }
	} catch (Exception ex) {
            throw new Exception();
	}
    }


    /**
    * CALCULATEDURATION
    * Calculates ETA between two points
    * @param idestination
    * @param ilocation
    * @return duration
    * @throws Exception
    * Code from http://stackoverflow.com/questions/17598074/google-map-in-java-swing
    */         
    double calculateDuration(String idestination, String ilocation) {
	try {
            //send request to server
            String response = this.sendRequest(idestination, ilocation);
            
            //parse JSON object
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

    
    /**
    * SENDREQUEST
    * Connects to Google Maps API
    * @param idestination
    * @param ilocation
    * @return response
    * @throws Exception
    * Code from http://stackoverflow.com/questions/17598074/google-map-in-java-swing
    */   
    private String sendRequest(String idestination, String ilocation) throws Exception {
        try {
            String location=ilocation.replace(" ",",");
            String destination=idestination.replace(" ",",");
            
            //send request
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
                    location + "&destinations=" + destination + "&key=AIzaSyBB5o7ib_q9Q5Jk16daxVUHEhIB7LZjHyU");
            Scanner scan = new Scanner(url.openStream());
            
            //parse response - text (JSON)
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
   
    
    /**
    * GETLOCATION
    * Gets location based on IP location services
    * facilitated by ISPs
    * skyhookwireless.com service
    * @param iuserType
    * @return location - if error - location set to UCC, Cork, Ireland
    * Code from https://my.skyhookwireless.com/
    */    
    public String getLocation(String iuserType) {
        String location = "";
        
        //check user type
        switch (iuserType) {
            case "DRIVER":
                //select random location from an array for driver
                String[] locationArray = {"South Mall", "Douglas", "Parnell Place", "Grand Parade", "College Road"};
                int idx = new Random().nextInt(locationArray.length);
                location = (locationArray[idx]);
                break;
            case "CUSTOMER":
                try {
                    //send request to skyhookwireless.com
                    URL url = new URL("https://context.skyhookwireless.com/accelerator/ip?version=2.0" +
                                        "&key=eJwVwcENACAIBLC3w5CcIgJPomEp4-5qW0vFJ824bDX2SAiJDiXuPAhzBnUs5OMRci4OAA" +
                                        "sq&user=eval");
                    
                    //parse response (JSON)
                    Scanner scan = new Scanner(url.openStream());
                    String response = new String();
                    while (scan.hasNext()) {
                        response += scan.nextLine();
                    }
                    scan.close();

                    //get location
                    JSONObject obj = new JSONObject(response)
                                                .getJSONObject("data")
                                                .getJSONObject("location");
                    location = String.valueOf(obj.getDouble("latitude")) + "," 
                            +  String.valueOf(obj.getDouble("longitude"));
                    break;
                } catch(Exception e) {
                    //if error occured set to UCC
                    location="UCC,Cork,Ireland";
                }
        }
        return location;
    }
}


