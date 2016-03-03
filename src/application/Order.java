/*
 * ORDER CLASS
 * Attributes: time, pickup point location, destination, travel time, driver user name, 
 *              customer user name, distance, status, payment amount
 * Methods: recordOrder()
 * The class represents an order in an application
 * Instances created when a driver records a trip or a user requests a taxi
 *
 */
package application;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Order {
    
    //CLASS VARIABLES    
    private String time;
    private String pickupPointLocation;
    private String destination;
    private String travelTime;
    private int driverID;
    private int customerID;
    private double distance;
    private String status; // PENDING || IN_PROGRESS || FINISHED
    private double paymentAmount;
    private final IOdb database = new IOdb();  

    //int recordOrder(pick up point location, destination, distance, driver user name, customer user uame, status)
    //
    //Gets current time
    //Sets and saves order details
    public int recordOrder(String ipickupPointLocation, String idestination, double idistance, int idriverID, int icustomerID, String istatus) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            int orderID;
            
            setTime(sdf.format(cal.getTime()));
            setPickupPointLocation(ipickupPointLocation);
            setDestination(idestination);
            setDistance(idistance);
            setStatus(istatus);
            setDriverID(idriverID);
            setCustomerID(icustomerID);
            orderID = database.recordTripDetails(this.destination, this.pickupPointLocation, this.distance, this.driverID, this.customerID, istatus);
            return orderID;
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

    public String getStatus() {
        return status;
    }

    public final void setStatus(String status) {
        this.status = status;
    }  
}
