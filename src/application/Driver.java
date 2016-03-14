/**
* <h1>DRIVER CLASS</h1>
* The class represents a physical driver in an application
* Instances created when driver logs in to the application
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

public class Driver extends User {
    
    
    //CLASS VARIABLES
    private String location;
    private String driverStatus;
    private IOmap geolocation; 
    private String name;
    
    
    /**
    * CONSTRUCTOR
    * Used to login dispatcher
    * @param iuserName
    * @param ipassword
    * @throws Exception
    */
    public Driver(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);
        this.database = new IOdb();
        
        //set location
        this.geolocation = new IOmap();
        this.location = geolocation.getLocation("DRIVER");
        //set status
        this.driverStatus = "AVAILABLE";
        //try to log in
        try {    
            this.logIn("DRIVER", this.location);
        } catch(Exception e) {
            throw new Exception();
        }
    }

    
    /**
    * CONSTRUCTOR
    * Used to facilitate populating dispatcher's tables
    */    
    public Driver() {
        
    }
    
    
    /**
    * LOGOUT
    * Used to log driver out
    * @throws Throwable - finalizing THIS customer
    * @throws Exception
    */   
    public void logOut() throws Throwable, Exception {
        //log driver out
        database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='LOGGED_OUT' WHERE UserName='" + 
                                    this.userName + "'");
        
        //get driver's active orders
        Order orderObj = new Order();
        int[] orders = orderObj.getOrders(this.getID());
        
        //cancel orders
        for (int order : orders) {
            Order orderIsInProgress = database.getOrder(order);
            
            //cancel if in progress and if ordered by a customer
            //using an app
            if("IN_PROGRESS".equals(orderIsInProgress.getStatus()) && orderIsInProgress.getCustomerID()!=0) {
                this.cancelOrder(order);               
            //cancel if recorded by THIS driver 
            //at the designated stand
            } else if (orderIsInProgress.getCustomerID()==0 && !"COMPLETED".equals(orderIsInProgress.getStatus())) {
                database.executeUpdateQuery("UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID=" + order);
            }
        }
        this.finalize();
    }
    
    
    /**
    * ORDERTAXI
    * Used to record trip ordered at the designated stand
    * @param idestination
    * @return orderID
    * @throws Exception
    */  
    public int recordTripDetails(String idestination) throws Exception {
        try {
            //get distance and ETA
            double distance = geolocation.calculateDistance(idestination + ",Cork,Ireland", 
                                                            this.getLocation() + ",Cork,Ireland");
            double travelTime = geolocation.calculateDuration(idestination + ",Cork,Ireland", 
                                                              this.getLocation() + ",Cork,Ireland");
            
            //set driver's status - unavailable for other orders
            database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='UNAVAILABLE' WHERE DriverID=" 
                                        + this.getID());
            
            //record trip
            Order trip = new Order();
            return trip.recordOrder(this.getLocation() + ",Cork,Ireland",
                                    idestination + ",Cork,Ireland",
                                    distance,
                                    this.getID(),
                                    0,
                                    "IN_PROGRESS",
                                    travelTime);           
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    
    /**
    * ACCEPTORDER
    * Used to accept orders requested via the application
    * @param iorderID
    * @throws Exception
    */  
    public void acceptOrder(int iorderID) throws Exception {
        try {
            //set order's status
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='IN_PROGRESS' WHERE OrderID=" + 
                                        iorderID + "");
                        
            //set driver's status
            database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='UNAVAILABLE' WHERE DriverID=" + 
                                        this.getID());
            
            //get customer ID
            int customerID = database.findOrderCustomerID(iorderID);
            //create a notification for a driver
            Notification notification = new Notification(iorderID, 
                                                    this.getID(), 
                                                    customerID,
                                                    "TRIP_ACCEPTED");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
            throw new Exception();
        }
    }
    
    
    /**
    * REJECTORDER
    * Used to reject orders requested via the application
    * @param iorderID
    * @throws Exception
    */  
    public void rejectOrder(int iorderID) throws Exception {
        try {
            //reschedule order - find another driver
            Order order = new Order();
            order.rescheduleOrder(iorderID, this.getID());
        } catch (Exception ex) {
            //if no other driver found
            JOptionPane.showMessageDialog(null, "Error rescheduling the order! No available drivers!", 
                                        "Error", JOptionPane.INFORMATION_MESSAGE);
            
            //cancel order
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID=" + 
                                        iorderID);
            
            //find customer ID
            int customerID = database.findOrderCustomerID(iorderID);
            //send notification to a customer
            Notification notification = new Notification(iorderID, this.getID(), 
                                                        customerID, "NO_DRIVERS");            
        }
    }
 
    
    /**
    * HANDLENOTIFICATION
    * Used to handle notifications
    * Depending on notification.message property
    * The action is taken
    * @param notification
    * @param imessageBoxResponse
    * @throws Exception
    */         
    public void handleNotification(Notification notification, int imessageBoxResponse) throws Exception {
        switch (notification.getMessage()) {
            case "TRIP_REQUEST":
                
                //if trip requested
                if(imessageBoxResponse==JOptionPane.YES_OPTION) {
                   //accept order
                   this.acceptOrder(notification.getOrderID());
                   
                   //set driver's status
                   database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='UNAVAILABLE' WHERE DriverID=" + 
                                                this.getID());
                   
                   //delete notification
                   Notification newNotification = new Notification();
                   newNotification.deleteNotification(notification.getNotificationID());
                   
                //trip rejected
                } else if(imessageBoxResponse==JOptionPane.NO_OPTION) {
                    
                   //reject order
                   this.rejectOrder(notification.getOrderID());
                   
                   //delete notification
                   Notification newNotification = new Notification();
                   newNotification.deleteNotification(notification.getNotificationID());
                }             
                break;
            case "CANCELLED":
                
                //set driver's status
                database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='AVAILABLE' WHERE DriverID=" + 
                                            this.getID());
                
                //delete notification
                Notification newNotification = new Notification();
                newNotification.deleteNotification(notification.getNotificationID());
                break;
        }
    }
    
    
    /**
    * CANCELORDER
    * Used to cancel order
    * Sends a notification to a customer
    * @param iorderID
    * @throws Exception - database error
    */       
    public void cancelOrder(int iorderID) throws Exception {
        try {
            //reschedule order and find a new driver
            Order order = new Order();
            order.rescheduleOrder(iorderID, this.getID());
        } catch (Exception ex) {
            //if no other driver available
            JOptionPane.showMessageDialog(null, "There are no other available drivers!", "Drivers", 
                                            JOptionPane.INFORMATION_MESSAGE);
            
            //get customer ID
            int customerID = database.findOrderCustomerID(iorderID);
            //send notification to a customer
            Notification notification = new Notification(iorderID, this.getID(), customerID, 
                                                        "NO_DRIVERS");
            //cancel order
            database.executeUpdateQuery("UPDATE Orders SET Status='CANCELLED' WHERE OrderID=" + 
                                        iorderID + "");
        }
    }

    
    /**
    * CONFIRMCOMPLETEDTRIP
    * Used to confirm completed trip
    * @param iorderID
    * @throws Exception - database error
    */       
    public void confirmCompletedTrip(int iorderID) throws Exception {
        
        //set driver's status
        database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='AVAILABLE' WHERE DriverID=" + 
                                    this.getID()); 
        
        //get order ID
        Order orderStand = database.getOrder(iorderID);
        //set driver's location
        database.executeUpdateQuery("UPDATE Drivers SET Location='" + 
                                    orderStand.getDestination() + 
                                    "' WHERE DriverID=" + this.getID());
        
        //if trip requested by a customer via the application
        if(orderStand.getCustomerID()!=0) {
            //find customer ID
            int customerID = database.findOrderCustomerID(iorderID);
            //send notification to a customer
            Notification notification = new Notification(iorderID, this.getID(), customerID, "TRIP_FINISHED");
        }
        try {
            //complete order
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='COMPLETED' WHERE OrderID=" + 
                                        iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /**
    * CONFIRMATDESIGNATEDSTAND
    * Used to change driver's location to Parnell Place, Cork
    */     
    public void confirmAtDesignatedStand() {
        try {
            //change location
            database.executeUpdateQuery("UPDATE NAME.Drivers SET Location='Parnell Place' WHERE DriverID=" + this.getID() + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    
    /**
    * CONFIRMARRIVALATPICKUPPOINT
    * Used to confirm arrival at the pick up point 
    * requested by the customer
    * @param iorderID
    * @throws Exception - database error
    */        
    public void confirmArrivalAtPickUpPoint(int iorderID) throws Exception {
        //get order ID
        Order orderStand = database.getOrder(iorderID);
        //set driver's location
        database.executeUpdateQuery("UPDATE Drivers SET Location='" + 
                                    orderStand.getPickupPointLocation() + 
                                    "' WHERE DriverID=" + this.getID());
        
        //get customer ID
        int customerID = database.findOrderCustomerID(iorderID);
        //send notification to a driver
        Notification notification = new Notification(iorderID, this.getID(),
                                                        customerID, "AT_PICK_UP_POINT");
        try {
            //set order status
            database.executeUpdateQuery("UPDATE NAME.Orders SET Status='PICK_UP_POINT' WHERE OrderID=" + 
                                        iorderID + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    /*
    * Getters & Setters
    */    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
