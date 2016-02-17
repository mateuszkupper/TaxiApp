/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javax.swing.JOptionPane;

public class Driver extends User {
    private String location;
    private String driverStatus;
    IOmaps geolocation; 
    IOdb database;
    
    public Driver(String iuserName, String ipassword) {
        super(iuserName, ipassword);
        database = new IOdb();
        geolocation = new IOmaps();
        this.location = geolocation.getLocation();
        driverStatus = "AVAILABLE";
        this.logIn();
    }
    
    public final void logIn() {
        try {
            database.logIn("DRIVER", this.userName, this.password, this.location);
        } catch(Exception e) {
             JOptionPane.showMessageDialog(null, "Login and password do not match!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void logOut() throws Throwable {
        database.logOut(this.userName);
        this.finalize();
    }
    
    //only available / unavailable
    public void changeStatus(String idriverStatus) {
        this.setDriverStatus(idriverStatus);
        database.changeDriverStatus(this.driverStatus, this.userName);
    }
    
    public void recordTripDetails(String idestination) {
        double distance = geolocation.calculateDistance(idestination, this.location);
        Order trip = new Order(this.location, idestination, distance);       
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
}
