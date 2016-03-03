/*
 * CUSTOMER CLASS
 * Attributes: location, (userName, password) - inherited from User class
 *             Objects: database, geolocation
 * Methods: orderTaxi(), logIn(), signUp(), logOut(), cancelTaxi(), changeOrderDetails()
 *          handleNotification()
 * The class represents a physical customer in an application
 * Instances created when customer logs in to the application
 *
 */
package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Customer extends User {
    
    //CLASS VARIABLES
    private String location;
    private IOdb database;
    private IOmaps geolocation;
    private int customerID;
    
    //CONSTRUCTOR
    //Parameters: user name, password, "is it a 'log in' or a 'sign up' event"
    //login_signup - value: "SIGNUP" - for sign up, "LOGIN" - for log in
    //
    //gets the location, if it is a signup event it will create a new account
    //logs the user in
    public Customer(String iuserName, String ipassword) {
        super(iuserName, ipassword);
        
        database = new IOdb();
        geolocation = new IOmaps();
        String ilocation = geolocation.getLocation("CUSTOMER");
        setLocation(ilocation);       
        
        this.logIn();
    }
  
    public Customer(String iuserName, String ipassword, String iname) {
        super(iuserName, ipassword);
        
        database = new IOdb();
        geolocation = new IOmaps();
        String ilocation = geolocation.getLocation("CUSTOMER");
        setLocation(ilocation);               
        
        this.signUp(iname);
    }    
    
    //void orderTaxi(pick up point, destination)
    //
    //Gets a pick up point and a destination
    //(if the pick up point is not specified, it sets it to the current location of a customer),
    //finds the nearest driver, records a trip and creates a new notification for a driver
    //to either accept or reject an order
    public void orderTaxi(String ipickUpPoint, String idestination) {
        if(ipickUpPoint==null) {
            ipickUpPoint = this.location;
        }        
        Order trip = new Order();
        double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
        int driverID;
        try {
            driverID = database.findClosestDriver(ipickUpPoint, 0);
            int orderID = trip.recordOrder(ipickUpPoint, idestination, distance, driverID, this.customerID, "PENDING");
            IOnotifications notification = new IOnotifications(orderID, driverID, this.customerID, "TRIP_REQUEST");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Driver was not found!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    
    //void logIn()
    //logs in the user
    public final void logIn() {
        try {
            database.logIn("CUSTOMER", this.userName, this.password, this.location);
            this.customerID = database.getID(this.userName, "CUSTOMER");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Login and password do not match!" + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //void signUp()
    //signs up a user
    public final void signUp(String iname) {
        try {
            if(database.signUp("CUSTOMER", this.userName, this.password, iname))
               this.logIn(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!" + ex.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //void logOut()
    //logs out the user    
    public void logOut() throws Throwable {
        database.logOut(this.userName, "CUSTOMER");
        this.finalize();
    }
    
    //void cancelTaxi(orderID)
    //cancels an order
    //creates a notification for a driver
    public void cancelTaxi(int iorderID) {
        database.cancelOrder(iorderID);
        int driverID = database.findOrderCustomerDriverID(iorderID, "DRIVER");
        IOnotifications notification = new IOnotifications(iorderID, driverID, this.customerID, "CANCELLED");
    }
    
    //void changeOrderDetails(orderID)
    //creates a notification for a driver
    public void changeOrderDetails(int orderID) {
        
    }
    
    //handleNotification(message "ALTERED, CANCELLED, etc.", response - from a messagebox)
    //deals with notifications according to their type/message
    public void handleNotification(String notificationMessage, String popupResponse) {
        switch (notificationMessage) {
            case "a": // in case of yes/no messageboxes 
                popupResponse = "AVAILABLE";
                /*database table - driver*/
                break;
            case "b":
                /*database table - customer*/
                break;
            case "c":
                /*database table - dispatcher*/
        }
    }
    
    /*
    * Getters & Setters
    */    
    public String getLocation() {
        return this.location;
    }
    
    public final void setLocation(String ilocation) {
        this.location = ilocation;
    }
}
