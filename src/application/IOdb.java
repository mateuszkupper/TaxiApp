/*
 * IODB CLASS
 * Attributes: -
 * Methods: logIn(), changeDriverStatus(), findDriver(), logOut(), recordTripDetails(), rescheduleOrder(),
 *          signUp(), updateOrderStatus()
 * The class is a connection between the business classes and a database
 * Instances created when there is a need to retrieve/update/modify/delete records in the database
 *
 */
package application;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Mateusz
 */
public class IOdb {
    private Connection con;
    
    private java.sql.Statement loginToDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/taxiDB", "name", "password");
        java.sql.Statement stmt = con.createStatement();       
        return stmt;
    }
    
    //void logIn(type of a user, username, password, location)
    //depending on a user type, checks password and a username provided
    public void logIn(String iuserType, String iuserName, String ipassword, String ilocation) throws Exception {   
        java.sql.Statement stmt = this.loginToDB();
        ResultSet dbResults;
        String fetchQuery = "";
        String updateQuery = "";
        if(null != iuserType) {
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT * FROM NAME.Drivers";
                    updateQuery = "UPDATE NAME.Drivers SET Status='" + "AVAILABLE" + "', Location='" + ilocation + "' WHERE UserName='" + iuserName + "'";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT * FROM NAME.Customers";
                    updateQuery = "UPDATE NAME.Customers SET Status='" + "LOGGED_IN" + "' WHERE UserName='" + iuserName + "'";
                    break;
                case "DISPATCHER":
                    fetchQuery = "SELECT * FROM NAME.Dispatchers";
                    updateQuery = "";                    
                    break;
            }
                            
            dbResults = stmt.executeQuery(fetchQuery);
            boolean userFoundFlag = false;
            while (dbResults.next()) {
                if(iuserName.equals(dbResults.getString("UserName")) && ipassword.equals(dbResults.getString("Password"))) {
                        userFoundFlag = true;
                }
            }
                
            if(userFoundFlag) {
                if(!"".equals(updateQuery))
                    stmt.executeUpdate(updateQuery);
                JOptionPane.showMessageDialog(null, "Login successful!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Password and username do not match!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        con.close();
    }
    
    //void signUp(user type, user name, password)
    //signs up a user depending on a user type
    public boolean signUp(String iuserType, String iuserName, String ipassword, String iname) throws Exception {
        java.sql.Statement stmt = this.loginToDB();
        ResultSet dbResults;
        String fetchQuery = "";
        String updateQuery = "";        
        if(null != iuserType) {
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT * FROM NAME.Drivers WHERE UserName='" + iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Drivers(UserName, Password, Status, Name) VALUES ('" + iuserName + "', '" + ipassword + "', 'LOGGED_OUT', '" + iname +"')";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT * FROM NAME.Customers WHERE UserName='" + iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Customers(UserName, Password, Status, Name) VALUES ('" + iuserName + "', '" + ipassword + "', 'LOGGED_OUT', '" + iname + "')";
                    break;
                case "DISPATCHER":
                    fetchQuery = "SELECT * FROM NAME.Dispatchers WHERE UserName='" + iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Dispatchers(UserName, Password, Name) VALUES ('" + iuserName + "', '" + ipassword + "', '" + iname +"')";                    
                    break;
            }                    
                                                
            dbResults = stmt.executeQuery(fetchQuery);
            if (dbResults.next()) {
                JOptionPane.showMessageDialog(null, "Username already exists! Choose different username!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } else {
                stmt.executeUpdate(updateQuery);
                JOptionPane.showMessageDialog(null, "Sign up successful!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        con.close();
        return false;
    }         

   
    //void logOut(user name, user type)
    //logs out a user
    public void logOut(String iuserName, String iuserType) {
        try {
            java.sql.Statement stmt = this.loginToDB();
            String driverStatus = "LOGGED_OUT";
            String updateQuery = "";
            
            switch (iuserType) {
                case "DRIVER":
                    updateQuery = "UPDATE NAME.Drivers SET Status='" + "LOGGED_OUT" + "' WHERE UserName='" + iuserName + "'";
                    break;
                case "CUSTOMER":
                    updateQuery = "UPDATE NAME.Customers SET Status='" + "LOGGED_OUT" + "' WHERE UserName='" + iuserName + "'";
                    break;
            }              
            
            stmt.executeUpdate(updateQuery);
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error logging out! " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //void changeDriverStatus(status, driver's user name)
    //changes driver's status - AVAILABLE, UNAVAILABLE, LOGGED_IN, LOGGED_OUT
    public void changeDriverStatus(String istatus, String iuserName) {
        try {
            java.sql.Statement stmt = this.loginToDB();
                        
            String updateQuery = "UPDATE NAME.Drivers SET Status='" + istatus + "' WHERE UserName='" + iuserName + "'";            
            
            stmt.executeUpdate(updateQuery);
            con.close();            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error changing status: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);            
        }        
    }
    
    //int recordTripDetails(trip details)
    //records a trip and retrieves its ID given by a database (autonumber)
    public int recordTripDetails(String idestination, String ilocation, double idistance, int idriverID, int icustomerID, String iStatus) {
        try {
            java.sql.Statement stmt = this.loginToDB();       
            int orderID = 0;        
            String updateQuery = "INSERT INTO NAME.Orders(Destination, PickUpPointLocation, Status, CustomerID, DriverID, Distance) VALUES ('" + idestination + "', '" + ilocation + "', '" + iStatus + "', " + String.valueOf(icustomerID) + ", " + String.valueOf(idriverID) + ", " + String.valueOf(idistance) + ")";
            stmt.executeUpdate(updateQuery);
            
            String fetchQuery = "SELECT OrderID FROM NAME.Orders WHERE Destination='" + idestination + "' AND CustomerID=" + icustomerID + " AND DriverID=" + idriverID;
            stmt.executeQuery(fetchQuery);
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);
            if(dbIDResults.next()) {
                orderID = dbIDResults.getInt("OrderID");
            }
            con.close();
            
            return orderID;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error recording a trip: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }       
    }

    //void updateOrderStatus(status, orderID)
    //updates order status eg. PENDING, ACCEPTED, IN_PROGRESS
    public void updateOrderStatus(String istatus, int iorderID) {
        try {        
            java.sql.Statement stmt = this.loginToDB();
                        
            String updateQuery = "UPDATE NAME.Orders SET Status='" + istatus + "' WHERE UserName='" + istatus + "'";            
            
            stmt.executeUpdate(updateQuery);
            con.close();             
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error changing trip status: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);            
        }       
    }
    
    //void rescheduleOrder(orderID)
    //reschedules an order after a driver rejects a trip
    //finds new driver to accept an order
    public void rescheduleOrder(int iorderID) {
        try {
            java.sql.Statement stmt = this.loginToDB();
            ResultSet dbResults;
            
            String fetchQuery = "SELECT PickUpPointLocation FROM NAME.Orders WHERE OrderID=" + iorderID + "";                                                                
            dbResults = stmt.executeQuery(fetchQuery);
            
            String pickUpPoint = "";
            if (dbResults.next()) {
                pickUpPoint = dbResults.getString("PickUpPointLocation");
            }           
            
            int driverID = this.findClosestDriver(pickUpPoint);            
            
            String updateQuery = "UPDATE NAME.Orders SET DriverID=" + driverID + " WHERE OrderID=" + iorderID + "";            
            stmt.executeUpdate(updateQuery);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }            
    }
    
    //String findDriver(location)
    //finds the nearest driver and returns its username 
    public int findClosestDriver(String ipickUpPoint) /**scheduling orders*/ {
        try {
            java.sql.Statement stmt = this.loginToDB();
            ResultSet dbResults;
            int newDriverID = 0;

            int total = 0;            
            String numOfRowsQuery = "SELECT COUNT(*) FROM NAME.Drivers";
            ResultSet dbNumOfRowsResult = stmt.executeQuery(numOfRowsQuery);
            if(dbNumOfRowsResult.next()) {
                total = dbNumOfRowsResult.getInt(1);
            }
            
            String driversFetchQuery = "SELECT Location, DriverID FROM Drivers WHERE Status='AVAILABLE'";
            ResultSet dbDriversResults = stmt.executeQuery(driversFetchQuery);

            int i=0;
            double[] distance = new double[total];
            double shortestDistance = 0;
            while (dbDriversResults.next()) {
                IOmaps maps = new IOmaps();
                distance[i] = maps.calculateDistance(dbDriversResults.getString("Location"), ipickUpPoint);
                if(i>0){
                    if(distance[i]<distance[i-1]) {
                        shortestDistance = distance[i];
                        newDriverID = dbDriversResults.getInt("DriverID");
                    }
                } else {
                   shortestDistance = distance[i];
                   newDriverID = dbDriversResults.getInt("DriverID");
                }
                i++;
            }         

            con.close();
            return newDriverID;
        } catch(Exception e) {
            return 0;
        }
    }
    
    int findOrderCustomerDriverID(int iorderID, String iuserType) {
        try {           
            java.sql.Statement stmt = this.loginToDB();                                           
            String fetchQuery = "";
            
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT DriverID FROM NAME.Notifications WHERE OrderID=" + iorderID + "";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT CustomerID FROM NAME.Notifications WHERE OrderID=" + iorderID + "";
                    break;
            }

            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);            
            
            int ID = 0;
            if(dbIDResults.next()) {
                switch (iuserType) {
                    case "DRIVER":
                        ID = dbIDResults.getInt("DriverID");
                        break;
                    case "CUSTOMER":
                        ID = dbIDResults.getInt("CustomerID");
                        break;
                }
            }
            con.close();
            
            return ID;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting a driver: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }     
    }
    
    public void cancelOrder(int iorderID) {
        try {        
            java.sql.Statement stmt = this.loginToDB();
                        
            String updateQuery = "UPDATE NAME.Orders SET Status='CANCELLED' WHERE OrderID='" + iorderID + "'";            
            
            stmt.executeUpdate(updateQuery);
            con.close();             
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error cancelling an order: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);            
        }         
    }

    void createNewNotification(int iorderID, int idriverID, int icustomerID, String imessage) {
        try {
            java.sql.Statement stmt = this.loginToDB();                       
            String updateQuery = "INSERT INTO NAME.Notifications(CustomerID, DriverID, OrderID, Message) VALUES (" + icustomerID + ", " + idriverID + ", " + iorderID + ", '" + imessage + "')";
            stmt.executeUpdate(updateQuery);
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating a notification: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);            
        }        
    }

    void deleteNotification(int inotificationID) {
        try {           
            java.sql.Statement stmt = this.loginToDB();        
            String updateQuery = "DELETE FROM Notifications WHERE NotificationID=" + inotificationID;
            stmt.executeUpdate(updateQuery);
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting a notification: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);            
        }              
    }  

    int getID(String iuserName, String iuserType) {
        try {           
            java.sql.Statement stmt = this.loginToDB();                                           
            String fetchQuery = "";
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT DriverID FROM NAME.Drivers WHERE UserName=" + iuserName + "";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT CustomerID FROM NAME.Customers WHERE UserName=" + iuserName + "";
                    break;
            }
            
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);            
            int ID = 0;
            if(dbIDResults.next()) {
                switch (iuserType) {
                    case "DRIVER":
                        ID = dbIDResults.getInt("DriverID");
                        break;
                    case "CUSTOMER":
                        ID = dbIDResults.getInt("CustomerID");
                        break;
                }
            }
            con.close();
            
            return ID;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting a driver: " + e.getMessage(), "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }     
    }   
}
