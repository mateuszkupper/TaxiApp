/**
* <h1>CUSTOMER CLASS</h1>
* The class represents a physical customer in an application
* Instances created when customer logs in to the application
* The class inherits from the user class
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/

package application;

import javax.swing.JOptionPane;

public class Customer extends User { 
    
    
    //CLASS VARIABLES
    private String location;
    private IOmap geolocation;
    
    
    /**
    * CONSTRUCTOR
    * Used to login customer
    * @param iuserName
    * @param ipassword
    * @throws Exception
    */
    public Customer(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);
        
        //set location
        geolocation = new IOmap();
        String locationCust = geolocation.getLocation("CUSTOMER");
        setLocation(locationCust);
        
        //try to login
        try {
            this.logIn("CUSTOMER", locationCust);
        } catch (Exception ex) {
            throw new Exception();
        }
    }
    
    
    /**
    * CONSTRUCTOR
    * Used to sign up user
    * @param iuserName
    * @param ipassword
    * @param iname This is the actual first and second name
    * @throws Exception
    */  
    public Customer(String iuserName, String ipassword, String iname) throws Exception {
        super(iuserName, ipassword);
        
        //set location
        geolocation = new IOmap();
        String locationCust = geolocation.getLocation("CUSTOMER");
        setLocation(locationCust);               
        
        //try to sign up
        try {
            this.signUp(iname, locationCust);
        } catch(Exception e) {
            throw new Exception();
        }
    }    
    
    
    /**
    * ORDERTAXI
    * Used to order taxi by a customer
    * @param ipickUpPoint
    * @param idestination
    */  
    public void orderTaxi(String ipickUpPoint, String idestination) {
        
        //ensure that the destination and 
        //pick up point are in Cork
        idestination += ",Cork,Ireland";
        if("Here".equals(ipickUpPoint)) {
            //if pick up point not specified 
            //get location
            ipickUpPoint = this.getLocation();
        } else {
            ipickUpPoint += ",Cork,Ireland";
        }
        
        //try to save the trip
        try {
            //get distance and ETA
            Order trip = new Order();
            double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
            double time = geolocation.calculateDuration(idestination, ipickUpPoint);
            
            //fin closest driver
            int driverID;
            driverID = database.findClosestDriver(ipickUpPoint, 0);
            
            //save the trip
            int orderID = trip.recordOrder(ipickUpPoint, 
                                            idestination, 
                                            distance, 
                                            driverID, 
                                            this.getID(), 
                                            "PENDING", 
                                            time);
            //create notification to driver
            Notification notification = new Notification(orderID, 
                                            driverID, 
                                            this.getID(), 
                                            "TRIP_REQUEST");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No available drivers or requested trip is outside the Cork city!", 
                                                "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /**
    * SIGNUP
    * Used to sign up user
    * Used only in the second CONSTRUCTOR
    * @param ipickUpPoint
    * @param idestination
    * @throws Exception - database error
    */  
    private void signUp(String iname, String ilocation) throws Exception {
        try {
            //sign up customer
            database.signUp("CUSTOMER", this.userName, this.password, iname);
            //if successful log in
            this.logIn("CUSTOMER", ilocation); 
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    
    /**
    * LOGOUT
    * Used to log out customer
    * @param iorderID
    * @throws Throwable - finalizing THIS customer
    */      
    public void logOut(int iorderID) throws Throwable {
        //update database - customer - logged out
        database.executeUpdateQuery("UPDATE NAME.Customers SET Status='LOGGED_OUT' WHERE UserName='" 
                                    + this.userName + "'");
        //if customer has an active trip
        //it needs to be cancelled
        if (iorderID!=0) {
            this.cancelTaxi(iorderID);
        }
        //destroy object
        this.finalize();
    }
    
    
    /**
    * CANCELTAXI
    * Used to cancel taxi by a customer
    * Sends a notification to a driver
    * @param iorderID
    * @throws Exception - database error
    */    
    public void cancelTaxi(int iorderID) throws Exception {
        try {
            //cancel order
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID=" 
                                        + iorderID + "");
            //get driver's ID
            int driverID = database.findOrderDriverID(iorderID);
            //send a notification to a driver
            Notification notification = new Notification(iorderID, driverID, this.getID(), "CANCELLED");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "Error",
                                        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /**
    * HANDLENOTIFICATION
    * Used to handle notifications
    * Depending on notification.message property
    * The action is taken
    * @param notification
    */  
    public void handleNotification(Notification notification) {
        Notification newNotification = new Notification();
        //delete notifications              
        newNotification.deleteNotification(notification.getNotificationID());                            
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
