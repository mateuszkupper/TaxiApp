/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

public class Customer extends User {
    private String location;
    
    public Customer(String iuserName, String ipassword, String ilocation) {
        super(iuserName, ipassword);
        setLocation(ilocation);
    }
    
    public void orderTaxi(String ilocation, String idestination) {
        if (ilocation ==  null) {
            IOmaps geolocation = new IOmaps();
            ilocation = geolocation.getLocation();
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
