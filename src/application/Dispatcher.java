/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;
import javax.swing.JOptionPane;

public class Dispatcher extends User {
    
    //CLASS VARIABLES
    IOdb database;
    IOmaps geolocation;
    
    public Dispatcher(String iuserName, String ipassword) {
        super(iuserName, ipassword);        
        geolocation = new IOmaps();
        database = new IOdb();
        this.logIn();
    }

    public final void logIn() {
        try {
            database.logIn("DISPATCHER", this.userName, this.password, "DISPATCHER");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Login and password do not match!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public final void signUpUser(String iuserName, String ipassword, String iname) {
        try {
            database.signUp("CUSTOMER", iuserName, ipassword, iname);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
            
    public void cancelTaxi(int iorderID) {
        database.cancelOrder(iorderID);
        int driverID = database.findOrderCustomerDriverID(iorderID, "DRIVER");
        IOnotifications notification = new IOnotifications(iorderID, driverID, 0, "CANCELLED");
    }


    public void orderTaxi(String ipickUpPoint, String idestination) { 
        Order trip = new Order();
        double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
        int driverID = database.findClosestDriver(ipickUpPoint);
        int orderID = trip.recordOrder(ipickUpPoint, idestination, distance, driverID, 0, "PENDING");
        IOnotifications notification = new IOnotifications(orderID, driverID, 0, "TRIP_REQUEST");
    }   
}
