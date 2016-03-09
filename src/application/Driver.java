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

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Driver extends User {
    
    //CLASS VARIABLES
    private String location;
    private String driverStatus;
    private IOmap geolocation; 
    
    //CONSTRUCTOR
    //Parameters: user name, password
    //gets the location, if it is a signup event it will create a new account
    //logs the user in
    public Driver(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);
        this.database = new IOdb();
        this.geolocation = new IOmap();
        this.location = geolocation.getLocation("DRIVER");
        this.driverStatus = "AVAILABLE";
        try {    
            this.logIn("DRIVER", this.location);
        } catch(Exception e) {
            throw new Exception();
        }
    }

    //void logOut()
    //logs out the user    
    public void logOut() throws Throwable {
        database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='LOGGED_OUT' WHERE UserName='" + this.userName + "'");
        Order orderObj = new Order();
        int[] orders = orderObj.getOrders(this.getID());
        for (int order : orders) {
            this.cancelOrder(order);
        }
        this.finalize();
    }
    
    //void recordTripDetails(trip destination)
    //records trip requested at designated stands
    public void recordTripDetails(String idestination) {
        double distance = geolocation.calculateDistance(idestination, this.getLocation());
        Order trip = new Order();
        trip.recordOrder(this.getLocation(), idestination, distance, this.getID(), 0, "IN_PROGRESS", 0);
    }

    //void acceptOrder(orderID)
    //updates driver's and order status 
    public void acceptOrder(int iorderID) {
        try {
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='IN_PROGRESS' WHERE OrderID=" + iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
        int customerID = database.findOrderCustomerID(iorderID);
        Notification notification = new Notification(iorderID, this.getID(), customerID, "TRIP_ACCEPTED");
    }
    
    //void rejectOrder(orderID)
    //reschadules an order and finds a new driver  
    public void rejectOrder(int iorderID) {
        try {
            Order order = new Order();
            order.rescheduleOrder(iorderID, this.getID());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error rescheduling the order! No available drivers!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            int customerID = database.findOrderCustomerID(iorderID);
            Notification notification = new Notification(iorderID, this.getID(), customerID, "NO_DRIVERS");            
        }
    }
 
    //handleNotification(message "ALTERED, CANCELLED, etc.", response - from a messagebox)
    //deals with notifications according to their type/message    
    public void handleNotification(Notification notification, int imessageBoxResponse) {
        switch (notification.getMessage()) {
            case "TRIP_REQUEST":
                if(imessageBoxResponse==JOptionPane.YES_OPTION) {
                   this.acceptOrder(notification.getOrderID());
                   Notification newNotification = new Notification();
                   newNotification.deleteNotification(notification.getNotificationID());
                } else {
                   this.rejectOrder(notification.getOrderID());
                   Notification newNotification = new Notification();
                   newNotification.deleteNotification(notification.getNotificationID());
                }             
                break;
            case "CANCELLED":
                Notification newNotification = new Notification();
                newNotification.deleteNotification(notification.getNotificationID());
                break;
        }
    }
    
   public void cancelOrder(int iorderID) {
        try {
            Order order = new Order();
            order.rescheduleOrder(iorderID, this.getID());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error rescheduling the order! No available drivers!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            int customerID = database.findOrderCustomerID(iorderID);
            Notification notification = new Notification(iorderID, this.getID(), customerID, "NO_DRIVERS");
        }
   }

    public void confirmCompletedTrip(int iorderID) {
        try {
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='COMPLETED' WHERE OrderID=" + iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void confirmAtDesignatedStand() {
        try {
            database.executeUpdateQuery("UPDATE NAME.Drivers SET Location='Parnell Place' WHERE DriverID=" + this.getID() + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    public void confirmArrivalAtPickUpPoint(int iorderID) {
        int customerID = database.findOrderCustomerID(iorderID);
        Notification notification = new Notification(iorderID, this.getID(), customerID, "AT_PICK_UP_POINT");
        try {
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='PICK_UP_POINT' WHERE OrderID=" + iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    /*
    * Getters & Setters
    */    

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the driverStatus
     */
    public String getDriverStatus() {
        return driverStatus;
    }

    /**
     * @param driverStatus the driverStatus to set
     */
    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }
}
