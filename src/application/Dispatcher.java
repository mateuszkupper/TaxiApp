/**
* <h1>DISPATCHER CLASS</h1>
* The class represents a physical dispatcher in an application
* Instances created when dispatcher logs in to the application
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

public class Dispatcher extends User {
    
    
    //CLASS VARIABLES
    private IOmap geolocation;
    
    
    /**
    * CONSTRUCTOR
    * Used to login dispatcher
    * @param iuserName
    * @param ipassword
    * @throws Exception
    */    
    public Dispatcher(String iuserName, String ipassword) throws Exception {
        super(iuserName, ipassword);
        
        //create instance of IOmap class
        geolocation = new IOmap();      
        
        //try to log in dispatcher
        try {
            this.logIn("DISPATCHER", "");
        } catch (Exception ex) {
            throw new Exception();
        }
    }
    
    
    /**
    * SIGNUP
    * Used to sign up drivers
    * @param iuserName
    * @param ipassword
    * @param iname
    * @param iuserType - "DRIVER" or "DISPATCHER
    * @throws Exception - database error
    */      
    public final void signUpUser(String iuserName, String ipassword, String iname, 
                                    String iuserType) throws Exception {
        try {
            database.signUp(iuserType, iuserName, ipassword, iname);
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    
    /**
    * ORDERTAXI
    * Used to order taxi by a dispatcher
    * Used to facilitate handling phone orders
    * @param ipickUpPoint
    * @param idestination
    */     
    public void orderTaxi(String ipickUpPoint, String idestination) {
        
        //ensure that the destination and 
        //pick up point are in Cork        
        idestination += ",Cork,Ireland";
        ipickUpPoint += ",Cork,Ireland";
        
        //try to save the trip        
        try {
            
            //get distance and ETA            
            Order trip = new Order();
            double distance = geolocation.calculateDistance(idestination, ipickUpPoint);
            double time = geolocation.calculateDuration(idestination, ipickUpPoint);
           
            //find closest driver            
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
}
