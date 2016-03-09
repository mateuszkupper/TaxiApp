/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mateusz
 */
public class Notification {
    private IOdb database;
    private int notificationID;
    private int driverID;
    private int customerID;
    private int orderID;
    private String message;
    
    Notification() {
       database = new IOdb(); 
    }
    
    Notification(int iorderID, int idriverID, int icustomerID, String imessage) {
        try {
            database = new IOdb();
            this.setCustomerID(icustomerID);
            this.setDriverID(idriverID);
            this.setMessage(imessage);
            this.setOrderID(iorderID);
            database.executeUpdateQuery("INSERT INTO NAME.Notifications(CustomerID, DriverID, OrderID, Message) VALUES (" + icustomerID + ", " + idriverID + ", " + iorderID + ", '" + imessage + "')");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    Notification(int inotificationID, int iorderID, int idriverID, int icustomerID, String imessage) {
        this.setCustomerID(icustomerID);
        this.setDriverID(idriverID);
        this.setMessage(imessage);
        this.setOrderID(iorderID);
        this.setNotificationID(inotificationID);
    }
    
    public void deleteNotification(int inotificationID) {
        try {
            database.executeUpdateQuery("DELETE FROM Notifications WHERE NotificationID=" + inotificationID);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!" + ex.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    } 

    /**
     * @return the notificationID
     */
    public int getNotificationID() {
        return notificationID;
    }

    /**
     * @param notificationID the notificationID to set
     */
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    /**
     * @return the driverID
     */
    public int getDriverID() {
        return driverID;
    }

    /**
     * @param driverID the driverID to set
     */
    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the OrderID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * @param OrderID the OrderID to set
     */
    public void setOrderID(int OrderID) {
        this.orderID = OrderID;
    }
}
