/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Order {
    private String time;
    private String pickupPointLocation;
    private String destination;
    private String travelTime;
    private double distance;
    private double paymentAmount;
    private final IOdb database = new IOdb();
    
    public Order(String ipickupPointLocation, String idestination, double idistance) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        setTime(sdf.format(cal.getTime()));
        setPickupPointLocation(ipickupPointLocation);
        setDestination(idestination);
        setDistance(idistance);
        
        database.recordTripDetails(this.destination, this.pickupPointLocation, this.distance);
    }

    /*
    * Getters & Setters
    */
    public String getTime() {
        return time;
    }

    public final void setTime(String time) {
        this.time = time;
    }

    public String getPickupPointLocation() {
        return pickupPointLocation;
    }

    public final void setPickupPointLocation(String pickupPointLocation) {
        this.pickupPointLocation = pickupPointLocation;
    }

    public String getDestination() {
        return destination;
    }

    public final void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public final void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public double getDistance() {
        return distance;
    }

    public final void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public final void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    
}
