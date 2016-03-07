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

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Customer extends User {
    
    //CLASS VARIABLES
    private String location;
    private IOmap geolocation;
    
    //CONSTRUCTOR
    //Parameters: user name, password, "is it a 'log in' or a 'sign up' event"
    //login_signup - value: "SIGNUP" - for sign up, "LOGIN" - for log in
    //
    //gets the location, if it is a signup event it will create a new account
    //logs the user in
    public Customer(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);
        
        geolocation = new IOmap();
        String location = geolocation.getLocation("CUSTOMER");
        setLocation(location);              
        try {
            this.logIn("CUSTOMER", location);
        } catch (Exception ex) {
            throw new Exception();
        }
    }
  
    public Customer(String iuserName, String ipassword, String iname) throws Exception {
        super(iuserName, ipassword);
        
        geolocation = new IOmap();
        String location = geolocation.getLocation("CUSTOMER");
        setLocation(location);               
        
        try {
            this.signUp(iname, location);
        } catch(Exception e) {
            throw new Exception();
        }
    }    
    
    //void orderTaxi(pick up point, destination)
    //
    //Gets a pick up point and a destination
    //(if the pick up point is not specified, it sets it to the current location of a customer),
    //finds the nearest driver, records a trip and creates a new notification for a driver
    //to either accept or reject an order
    public void orderTaxi(String ipickUpPoint, String idestination) {
        idestination += ",Cork,Ireland";
        if("Here".equals(ipickUpPoint)) {
            ipickUpPoint = this.getLocation();
        } else {
            ipickUpPoint += ",Cork,Ireland";
        }
        
        Order trip = new Order();
        double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
        double time = geolocation.calculateDuration(idestination, ipickUpPoint);
        int driverID;
        try {
            driverID = database.findClosestDriver(ipickUpPoint, 0);
            int orderID = trip.recordOrder(ipickUpPoint, idestination, distance, driverID, this.getID(), "PENDING", time);
            Notification notification = new Notification(orderID, driverID, this.getID(), "TRIP_REQUEST");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No available drivers! Try again later.", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //void signUp()
    //signs up a user
    private void signUp(String iname, String ilocation) throws Exception {
        try {
            database.signUp("CUSTOMER", this.userName, this.password, iname);
            this.logIn("CUSTOMER", ilocation); 
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    //void logOut()
    //logs out the user    
    public void logOut() throws Throwable {
        database.executeUpdateQuery("UPDATE NAME.Customers SET Status='LOGGED_OUT' WHERE UserName='" + this.userName + "'");
        this.finalize();
    }
    
    //void cancelTaxi(orderID)
    //cancels an order
    //creates a notification for a driver
    public void cancelTaxi(int iorderID) {
        try {            
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID=" + iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
        int driverID = database.findOrderDriverID(iorderID);
        Notification notification = new Notification(iorderID, driverID, this.getID(), "CANCELLED");
    }
    
    //handleNotification(message "ALTERED, CANCELLED, etc.", response - from a messagebox)
    //deals with notifications according to their type/message
    public void handleNotification(Notification notification, int imessageBoxResponse) {
        Notification newNotification = new Notification();
        switch (notification.getMessage()) {
            case "TRIP_ACCEPTED":                 
                notification.deleteNotification(notification.getNotificationID());            
                break;
            case "NO_DRIVERS": 
                JOptionPane.showMessageDialog(null, "No available drivers!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
                notification.deleteNotification(notification.getNotificationID());                 
                break;
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
