/**
* <h1>NOTIFICATION CLASS</h1>
* The class represents notification in the application
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/
package application;

import javax.swing.JOptionPane;

public class Notification {
    
    
    //CLASS VARIABLES
    private IOdb database;
    private int notificationID;
    private int driverID;
    private int customerID;
    private int orderID;
    private String message;
    
    
    /**
    * CONSTRUCTOR
    * Connects to db
    */    
    Notification() {
       database = new IOdb(); 
    }
 
    
    /**
    * CONSTRUCTOR
    * Connects to db
    * Sets values
    * @param iorderID
    * @param idriverID
    * @param icustomerID
    * @param imessage
    */     
    Notification(int iorderID, int idriverID, int icustomerID, String imessage) {
        try {
            //set values
            database = new IOdb();
            this.setCustomerID(icustomerID);
            this.setDriverID(idriverID);
            this.setMessage(imessage);
            this.setOrderID(iorderID);
            //save notification
            database.executeUpdateQuery("INSERT INTO NAME.Notifications(CustomerID, " + 
                                        "DriverID, OrderID, Message) VALUES (" + 
                                        icustomerID + ", " + idriverID + ", " + iorderID + 
                                        ", '" + imessage + "')");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /**
    * CONSTRUCTOR
    * Sets values
    * @param iorderID
    * @param idriverID
    * @param icustomerID
    * @param imessage
    */    
    Notification(int inotificationID, int iorderID, int idriverID, int icustomerID, String imessage) {
        this.setCustomerID(icustomerID);
        this.setDriverID(idriverID);
        this.setMessage(imessage);
        this.setOrderID(iorderID);
        this.setNotificationID(inotificationID);
    }
    
    
    /**
    * DELETENOTIFICATION
    * Deletes notification from the db
    * @param inotificationID
    */    
    public void deleteNotification(int inotificationID) {
        try {
            database.executeUpdateQuery("DELETE FROM Notifications WHERE NotificationID=" + inotificationID);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!" + ex.getMessage(), "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    } 

    
    /*
    * Getters & Setters
    */
    public int getNotificationID() {
        return notificationID;
    }

    public final void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getDriverID() {
        return driverID;
    }

    public final void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public final void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public int getOrderID() {
        return orderID;
    }

    public final void setOrderID(int OrderID) {
        this.orderID = OrderID;
    }
}
