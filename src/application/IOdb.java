/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author Mateusz
 */
public class IOdb {
    
    public IOdb() {
        /*
        *
        * database login
        *
        */
    }
    
   public void logIn(String userType, String iuserName, String ipassword, String ilocation) throws Exception {
       if(null != userType) switch (userType) {
            case "DRIVER":
                String driverStatus = "AVAILABLE";
                /*database table - driver*/
                break;
            case "CUSTOMER":
                /*database table - customer*/
                break;
            case "DISPATCHER":
                /*database table - dispatcher*/
                break;
        }
       
       /*
        *
        * database handling - check if password and username match
        *
        */
        String dbResult = "TO BE FINISHED";

        if(dbResult != null) {
            /*
            *
            * database handling - update driver's location and status
            *
            */
        } else {
            throw new Exception();
        }
    }
   
    public void logOut(String userName) {
        try {
            String driverStatus = "LOGGED_OUT";
            /*
            *
            * database handling - update driver's location and status
            * reschedule associated orders
            * 
            */
        } catch(Exception e) {
            
        }
    }

    public void changeDriverStatus(String status, String userName) {
        try {
            /*
            *
            * database handling - update driver's status
            * 
            */
        } catch(Exception e) {
            
        }        
    }

    public void recordTripDetails(String idestination, String location, double distance) {
        try {
            /*
            *
            * database handling - record trip
            * 
            */
        } catch(Exception e) {
            
        }       
    }
    
}
