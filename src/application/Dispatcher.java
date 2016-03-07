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

public class Dispatcher extends User {
    
    //CLASS VARIABLES
    private IOmap geolocation;
    
    public Dispatcher(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);        
        geolocation = new IOmap();      
        
        try {
            this.logIn("DISPATCHER", "");
        } catch (Exception ex) {
            throw new Exception();
        }
    }
    
    public final void signUpUser(String iuserName, String ipassword, String iname, String iuserType) {
        try {
            database.signUp(iuserType, iuserName, ipassword, iname);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
            
    public void cancelTaxi(int iorderID) {
        try {
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID=" + iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
        int driverID = database.findOrderDriverID(iorderID);
        Notification notification = new Notification(iorderID, driverID, 0, "CANCELLED");
    }


    public void orderTaxi(String ipickUpPoint, String idestination) {
        idestination += ",Cork,Ireland";
        ipickUpPoint += ",Cork,Ireland";
        Order trip = new Order();
        double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
        double time = geolocation.calculateDuration(idestination, ipickUpPoint);        
        try {
            int driverID = database.findClosestDriver(ipickUpPoint, 0);
            int orderID = trip.recordOrder(ipickUpPoint, idestination, distance, driverID, 0, "PENDING", time);
            Notification notification = new Notification(orderID, driverID, 0, "TRIP_REQUEST");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Driver was not found!" + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }   
}
