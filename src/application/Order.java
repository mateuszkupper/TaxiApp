/**
* <h1>ORDER CLASS</h1>
* The class represents an order in an application
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/

package application;

public class Order {
    
    
    //CLASS VARIABLES    
    private String time;
    private String pickupPointLocation;
    private String destination;
    private double travelTime;
    private int orderID;
    private int driverID;
    private int customerID;
    private double distance;
    private String status;
    private double paymentAmount;
    private final IOdb database = new IOdb();  

    
    /**
    * CONSTRUCTOR
    * Used to record order
    * @param ipickupPointLocation
    * @param idestination
    * @param idistance
    * @param idriverID
    * @param icustomerID
    * @param istatus
    * @param itravelTime
    * @return orderID
    * @throws Exception
    */
    public int recordOrder(String ipickupPointLocation, String idestination, 
                            double idistance, int idriverID, int icustomerID, 
                            String istatus, double itravelTime) throws Exception {
        //assign values                    
        setTravelTime(itravelTime);
        setPickupPointLocation(ipickupPointLocation);
        setDestination(idestination);
        setDistance(idistance);
        setStatus(istatus);
        setDriverID(idriverID);
        setCustomerID(icustomerID);
        setTravelTime(itravelTime);
        try {
            //record trip and get orderID
            setOrderID(database.recordTripDetails(this));
            return getOrderID();
        } catch (Exception ex) {
            throw new Exception();
        }
    }
    
    /**
    * RESCHEDULEORDER
    * Reschedules orders
    * @param iorderID
    * @param ioldDriverID
    * @throws Exception
    */ 
    public void rescheduleOrder(int iorderID, int ioldDriverID) throws Exception {
        try {
            database.rescheduleOrder(iorderID, ioldDriverID);
        } catch(Exception e) {
            throw new Exception();
        }            
    }


    /**
    * RESCHEDULEORDER
    * Returns order IDs for a given driver
    * @param idriverID
    * @return int[] orderIDs
    * @throws Exception
    */     
    public int[] getOrders(int idriverID) throws Exception {
        try {        
            int[] orders = database.getOrders(idriverID);
            return orders;
        } catch(Exception e) {
            throw new Exception();
        }          
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

    public double getTravelTime() {
        return travelTime;
    }

    public final void setTravelTime(double travelTime) {
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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
