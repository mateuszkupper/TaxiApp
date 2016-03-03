/*
 * DRIVER CLASS
 * Attributes: location, (userName, password) - inherited from User class, driver's status
 *             Objects: database, geolocation
 * Methods: logIn(), logOut(), changeStatus(), recordTripDetails() - for requests from designated pick up points
 *          handleNotification(), acceptOrder(), rejectOrder(), cancelOrder()
 * The class represents a physical driver in an application
 * Instances created when driver logs in to the application
 *
 */
package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Driver extends User {
    
    //CLASS VARIABLES
    private final String location;
    private String driverStatus;
    private int driverID;
    private IOmaps geolocation; 
    private IOdb database;
    
    //CONSTRUCTOR
    //Parameters: user name, password
    //gets the location, if it is a signup event it will create a new account
    //logs the user in
    public Driver(String iuserName, String ipassword) {
        super(iuserName, ipassword);
        this.database = new IOdb();
        this.geolocation = new IOmaps();
        this.location = geolocation.getLocation("DRIVER");
        this.driverStatus = "AVAILABLE";
        this.logIn();
    }
    
    //void logIn()
    //logs in the driver   
    public final void logIn() {
        try {
            database.logIn("DRIVER", this.userName, this.password, this.location);
            this.driverID = database.getID(this.userName, "DRIVER");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //void logOut()
    //logs out the user    
    public void logOut() throws Throwable {
        database.logOut(this.userName, "DRIVER");
        this.finalize();
    }
    
    //void changeStatus(status - either "AVAILABLE" or "UNAVAILABLE")
    //changes driver's status
    public void changeStatus(String idriverStatus) {
        this.setDriverStatus(idriverStatus);
        database.changeDriverStatus(this.driverStatus, this.userName);
    }
    
    //void recordTripDetails(trip destination)
    //records trip requested at designated stands
    public void recordTripDetails(String idestination) {
        double distance = geolocation.calculateDistance(idestination, this.location);
        Order trip = new Order();
        int a = trip.recordOrder(this.location, idestination, distance, this.driverID, 0, "IN_PROGRESS");
    }
    
    /*
    * Getters & Setters
    */    
    public String getlocation() {
        return this.location;
    }
    
    public String getDriverStatus() {
        return this.driverStatus;
    }
    
    public void setDriverStatus(String idriverStatus) {
        this.driverStatus = idriverStatus;
    }
    
    /**********non-essential methods***********/
    
    //void acceptOrder(orderID)
    //updates driver's and order status 
    public void acceptOrder(int iorderID) {
        database.updateOrderStatus("IN_PROGRESS", iorderID);
        int customerID = database.findOrderCustomerDriverID(iorderID, "CUSTOMER");
        IOnotifications notification = new IOnotifications(iorderID, this.driverID, customerID, "TRIP_ACCEPTED");
    }
    
    //void rejectOrder(orderID)
    //reschadules an order and finds a new driver  
    public void rejectOrder(int iorderID) {
        try {
            database.rescheduleOrder(iorderID, this.driverID);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error rescheduling the order! No available drivers!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            int customerID = database.findOrderCustomerDriverID(iorderID, "CUSTOMER");
            IOnotifications notification = new IOnotifications(iorderID, this.driverID, customerID, "NO_DRIVERS");            
        }
    }
 
    //handleNotification(message "ALTERED, CANCELLED, etc.", response - from a messagebox)
    //deals with notifications according to their type/message    
    public void handleNotification(String inotificationMessage, int imessageBoxResponse, int inotificationID, int iorderID) {
        switch (inotificationMessage) {
            case "TRIP_REQUEST":
                if(imessageBoxResponse==JOptionPane.YES_OPTION) {
                   this.acceptOrder(iorderID);
                   database.deleteNotification(inotificationID);
                } else {
                   this.rejectOrder(iorderID);
                   database.deleteNotification(inotificationID);
                }             
                break;
            case "CANCELLED":
                
                break;
        }
    }
    
   public void cancelOrder(int iorderID) {
        try {
            database.rescheduleOrder(iorderID, this.driverID);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error rescheduling the order! No available drivers!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            int customerID = database.findOrderCustomerDriverID(iorderID, "CUSTOMER");
            IOnotifications notification = new IOnotifications(iorderID, this.driverID, customerID, "NO_DRIVERS");
        }
   }

    public void confirmCompletedTrip(int iorderID) {
        database.changeOrderStatus(iorderID, "COMPLETED");
    }
    
    public void confirmAtDesignatedStand() {
        database.confirmAtDesignatedStand(this.driverID);
    }
    
    public void confirmArrivalAtPickUpPoint(int iorderID) {
        int customerID = database.findOrderCustomerDriverID(iorderID, "CUSTOMER");
        IOnotifications notification = new IOnotifications(iorderID, this.driverID, customerID, "AT_PICK_UP_POINT");
        database.changeOrderStatus(iorderID, "PICK_UP_POINT");
    }
}
