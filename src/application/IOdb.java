/**
* <h1>IODB CLASS</h1>
* The class represents a database connection in an application
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/

package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class IOdb {
    
    
    //CLASS VARIABLES
    private Connection con;
    
    
    /**
    * LOGINTODB
    * Used to establish a connection
    * @throws ClassNotFoundException
    * @throws SQLException
    * @return java.sql.Statement - return statement
    */  
    private java.sql.Statement loginToDB() throws ClassNotFoundException, SQLException {
        //ensure driver is found
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        //connect
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/taxiDB", "name", "password");
        //create statement
        java.sql.Statement stmt = con.createStatement();       
        return stmt;
    }
    
    
    /**
    * EXECUTEUPDATEQUERY
    * Used to update the database
    * does not return a value
    * @param iquery
    * @throws ClassNotFoundException
    * @throws SQLException
    */      
    public void executeUpdateQuery(String iquery) throws SQLException, ClassNotFoundException {
        java.sql.Statement stmt = this.loginToDB();
        stmt.executeUpdate(iquery);
        con.close();     
    } 
    
    
    /**
    * LOGIN
    * Used to login users
    * Updates database
    * @param iuserType - "DRIVER" or "CUSTOMER" or "DISPATCHER"
    * @param iuserName
    * @param ipassword
    * @param ilocation
    * @throws Exception
    */  
    public void logIn(String iuserType, String iuserName, String ipassword, 
                        String ilocation) throws Exception {
        
        //login to db
        java.sql.Statement stmt = this.loginToDB();
        ResultSet dbResults;
        
        //initialise queries
        String fetchQuery = "";
        String updateQuery = "";
        
        //if usertype specified
        //update database
        //select all users of a specified type
        if(null != iuserType) {
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT * FROM NAME.Drivers";
                    updateQuery = "UPDATE NAME.Drivers SET Status='" + "AVAILABLE" + 
                                    "', Location='" + ilocation + 
                                    "' WHERE UserName='" + iuserName + "'";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT * FROM NAME.Customers";
                    updateQuery = "UPDATE NAME.Customers SET Status='" + "LOGGED_IN" + 
                                    "' WHERE UserName='" + iuserName + "'";
                    break;
                case "DISPATCHER":
                    fetchQuery = "SELECT * FROM NAME.Dispatchers";
                    updateQuery = "";                    
                    break;
            }
            
            //get users
            dbResults = stmt.executeQuery(fetchQuery);
            //user found?
            boolean userFoundFlag = false;
            
            //checks if:
            //password and login match
            //and if CUSTOMER and DRIVER
            //are not logged in
            while (dbResults.next()) {
                //if password and login match
                if(iuserName.equals(dbResults.getString("UserName")) 
                    && ipassword.equals(dbResults.getString("Password")))  {
                    //for "CUSTOMER" and "DRIVER"
                    if("CUSTOMER".equals(iuserType) || "DRIVER".equals(iuserType)) {
                        //not logged in
                        if(!"AVAILABLE".equals(dbResults.getString("Status")) 
                            && !"LOGGED_IN".equals(dbResults.getString("Status"))){
                            userFoundFlag = true;                                
                        }
                    //for "DISPATCHER"
                    } else {
                        userFoundFlag = true;
                    }
                }
            }
            
            //if user found
            if(userFoundFlag) {
                if(!"".equals(updateQuery))
                    //set "LOGGED IN"
                    this.executeUpdateQuery(updateQuery);
                JOptionPane.showMessageDialog(null, "Login successful!", "Login", 
                                                JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new Exception();
            }
        }
        con.close();
    }
    
    
    /**
    * SIGNUP
    * Used to sign users up
    * Updates database
    * @param iuserType - "DRIVER" or "CUSTOMER" or "DISPATCHER"
    * @param iuserName
    * @param ipassword
    * @param iname - first and last name
    * @throws Exception
    */  
    public void signUp(String iuserType, String iuserName, String ipassword, String iname) throws Exception {
        java.sql.Statement stmt = this.loginToDB();
        ResultSet dbResults;
        String fetchQuery = "";
        String updateQuery = "";        
        if(null != iuserType) {
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT * FROM NAME.Drivers WHERE UserName='" + 
                                    iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Drivers(UserName, Password, Status, Name) VALUES ('" + 
                                    iuserName + "', '" + ipassword + "', 'LOGGED_OUT', '" + iname +"')";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT * FROM NAME.Customers WHERE UserName='" + 
                                    iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Customers(UserName, Password, Status, Name) VALUES ('" + 
                                    iuserName + "', '" + ipassword + "', 'LOGGED_OUT', '" + iname + "')";
                    break;
                case "DISPATCHER":
                    fetchQuery = "SELECT * FROM NAME.Dispatchers WHERE UserName='" + 
                                    iuserName + "'";
                    updateQuery = "INSERT INTO NAME.Dispatchers(UserName, Password, Name) VALUES ('" + 
                                    iuserName + "', '" + ipassword + "', '" + iname +"')";                    
                    break;
            }                    
                                                
            dbResults = stmt.executeQuery(fetchQuery);
            //if username already exists
            if (dbResults.next()) {
                JOptionPane.showMessageDialog(null, "Username already exists! Choose different username!", 
                                             "Error", JOptionPane.INFORMATION_MESSAGE);
                throw new Exception();
            //proceed if username is not already used
            } else {
                this.executeUpdateQuery(updateQuery);
                JOptionPane.showMessageDialog(null, "Sign up successful!", 
                                             "Sign up", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        con.close();
    }         
    
    
    /**
    * RECORDTRIPDETAILS
    * Records details of a trip - in case of a new order
    * Updates database
    * @param iorder
    * @return orderID
    * @throws Exception
    */  
    public int recordTripDetails(Order iorder) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();       
            int orderID = 0;
            
            //insert new order
            String updateQuery = "INSERT INTO NAME.Orders(Destination, PickUpPointLocation, " +
                                 "Status, CustomerID, DriverID, Distance, TravelTime) VALUES ('" + 
                                iorder.getDestination() + "', '" + 
                                iorder.getPickupPointLocation() + "', '" + 
                                iorder.getStatus() + "', " + 
                                String.valueOf(iorder.getCustomerID()) + ", " + 
                                String.valueOf(iorder.getDriverID()) + ", " + 
                                String.valueOf(iorder.getDistance())+ ", " + 
                                iorder.getTravelTime() + ")";
            this.executeUpdateQuery(updateQuery);
            
            //get OrderID
            String fetchQuery = "SELECT OrderID FROM NAME.Orders WHERE Destination='" + 
                                iorder.getDestination() + "' AND CustomerID=" + 
                                String.valueOf(iorder.getCustomerID()) + " AND DriverID=" + 
                                String.valueOf(iorder.getDriverID()) + 
                                " AND (Status='IN_PROGRESS' OR Status='PENDING')";
            stmt.executeQuery(fetchQuery);
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);
            //if OrderID found
            if(dbIDResults.next()) {
                orderID = dbIDResults.getInt("OrderID");
            }
            con.close();
            
            return orderID;
        } catch(Exception e) {
            throw new Exception();
        }       
    }
    
    
    /**
    * RESCHEDULEORDER
    * Finds the closest driver and reschedules order
    * @param iorderID
    * @param ioldDriverID
    * @throws Exception
    */  
    public void rescheduleOrder(int iorderID, int ioldDriverID) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();
            ResultSet dbResults;
            
            //get pick up point
            String fetchQuery = "SELECT PickUpPointLocation FROM NAME.Orders WHERE OrderID=" + 
                                iorderID + "";                                                                
            dbResults = stmt.executeQuery(fetchQuery);
            
            //if pick up point location not empty
            String pickUpPoint = "";
            if (dbResults.next()) {
                pickUpPoint = dbResults.getString("PickUpPointLocation");
            }           
            
            //find closest driver based on pick up point
            int driverID = this.findClosestDriver(pickUpPoint, ioldDriverID);

            //if not found
            if(driverID==0) {
                throw new Exception();
            }
            
            //update order
            String updateQuery = "UPDATE NAME.Orders SET DriverID=" + driverID + 
                                    " WHERE OrderID=" + iorderID + "";
            this.executeUpdateQuery(updateQuery);
            
            //get customer ID
            int customerID = this.findOrderCustomerID(iorderID);
            //send notification to driver
            Notification notification = new Notification(iorderID, driverID, 
                                                            customerID, "TRIP_REQUEST");
        } catch(Exception e) {
            throw new Exception();
        }            
    }
    
    
    /**
    * FINDCLOSESTDRIVER
    * Returns the closest driver based on location
    * @param ipickUpPoint
    * @param ioldDriverID
    * @return new driver ID
    * @throws Exception
    */  
    public int findClosestDriver(String ipickUpPoint, int ioldDriverID) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();
            ResultSet dbResults;
            int newDriverID = 0;

            //count the number of drivers
            int total = 0;            
            String numOfRowsQuery = "SELECT COUNT(*) FROM NAME.Drivers";
            ResultSet dbNumOfRowsResult = stmt.executeQuery(numOfRowsQuery);
            if(dbNumOfRowsResult.next()) {
                total = dbNumOfRowsResult.getInt(1);
            }
            
            //select available drivers
            String driversFetchQuery = "SELECT Location, DriverID FROM Drivers WHERE Status='AVAILABLE'";
            ResultSet dbDriversResults = stmt.executeQuery(driversFetchQuery);
            
            //FIND DRIVER
            int i=0;
            //array of distances for all drivers
            double[] distance = new double[total];
            double shortestDistance = 0;
  
            while (dbDriversResults.next()) {
                IOmap maps = new IOmap();
                //get distance
                distance[i] = maps.calculateDistance(dbDriversResults.getString("Location")+
                            "Cork,Ireland", ipickUpPoint);
                //if there are previous array elements
                if(i>0){
                    //if requested by a customer - not accepted yet
                    if(dbDriversResults.getInt("DriverID")!=ioldDriverID && ioldDriverID!=0) {
                        shortestDistance = distance[i];                           
                        newDriverID = dbDriversResults.getInt("DriverID");
                    //if there are more than 2 drivers 
                    //and if distance for current driver 
                    //is shorter than for the previous one
                    } else if(total>2 && distance[i]<=distance[i-1] && ioldDriverID!=0) {
                        shortestDistance = distance[i];                           
                        newDriverID = dbDriversResults.getInt("DriverID");
                    //if rescheduling by a driver
                    } else if(distance[i]<=distance[i-1] && ioldDriverID==0) {
                        shortestDistance = distance[i];                           
                        newDriverID = dbDriversResults.getInt("DriverID");                       
                    }
                //if there are no previous array elements
                } else {
                    if(dbDriversResults.getInt("DriverID")!=ioldDriverID) {
                        shortestDistance = distance[i];
                        newDriverID = dbDriversResults.getInt("DriverID");
                    }
                }
                i++;
            }         
            
            //if no driver found
            if(newDriverID==0) {
                con.close();
                throw new Exception();                
            } else {
                con.close();
                return newDriverID;                
            }           
        } catch(Exception e) {
            throw new Exception();
        }
    }
    
    
    /**
    * FINDORDERDRIVERID
    * Returns driver ID for a given order
    * @param iorderID
    * @return driver ID
    * @throws Exception
    */     
    public int findOrderDriverID(int iorderID) throws Exception {
        try {           
            java.sql.Statement stmt = this.loginToDB();
            
            //select driver ID
            String fetchQuery = "SELECT DriverID FROM NAME.Orders WHERE OrderID=" + 
                                iorderID + "";
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);            
            
            //if results exist
            int ID = 0;
            if(dbIDResults.next()) {
                ID = dbIDResults.getInt("DriverID");  
            }
            
            con.close();
            
            return ID;
        } catch(Exception e) {
            throw new Exception();
        }      
    }

    
    /**
    * FINDORDERCUSTOMERID
    * Returns customer ID for a given order
    * @param iorderID
    * @return customer ID
    * @throws Exception
    */      
    public int findOrderCustomerID(int iorderID) throws Exception {
        try {           
            java.sql.Statement stmt = this.loginToDB();                                           
            String fetchQuery = "SELECT CustomerID FROM NAME.Orders WHERE OrderID=" + 
                                iorderID + "";
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);            
            
            int ID = 0;
            if(dbIDResults.next()) {
                ID = dbIDResults.getInt("CustomerID");  
            }
            
            con.close();
            
            return ID;
        } catch(Exception e) {
            throw new Exception();
        }  
    }  

    
    /**
    * GETID
    * Returns user ID based on username
    * @param iuserName
    * @param iuserType
    * @return driver ID
    * @throws Exception
    */      
    public int getID(String iuserName, String iuserType) throws Exception {
        try {           
            java.sql.Statement stmt = this.loginToDB();                                           
            String fetchQuery = "";
            
            //search for ID depending on usertype
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT DriverID FROM NAME.Drivers WHERE UserName='" + 
                                    iuserName + "'";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT CustomerID FROM NAME.Customers WHERE UserName='" + 
                                    iuserName + "'";
                    break;
                case "DISPATCHER":
                    fetchQuery = "SELECT DispatcherID FROM NAME.Dispatchers WHERE UserName='" + 
                                    iuserName + "'";
                    break;
            }
            ResultSet dbIDResults = stmt.executeQuery(fetchQuery);
            
            //get userID
            int ID = 0;
            if(dbIDResults.next()) {
                switch (iuserType) {
                    case "DRIVER":
                        ID = dbIDResults.getInt("DriverID");
                        break;
                    case "CUSTOMER":
                        ID = dbIDResults.getInt("CustomerID");
                        break;
                    case "DISPATCHER":
                        ID = dbIDResults.getInt("DispatcherID");
                        break;                        
                }
            }
            con.close();
            
            return ID;
        } catch(Exception e) {
            throw new Exception();
        }     
    }   
 
    
    /**
    * GETORDERS
    * Returns array of orderIDs for a given driverID
    * @param idriverID
    * @return int[] OrderIDs
    * @throws Exception
    */       
    public int[] getOrders(int idriverID) throws Exception {
        try {        
            java.sql.Statement stmt = this.loginToDB();
            
            //count number of orders for a given driver
            String numOfRowsQuery = "SELECT COUNT(*) AS total FROM NAME.Orders WHERE DriverID=" + idriverID + "";
            ResultSet dbNumOfRowsResult = stmt.executeQuery(numOfRowsQuery);
            int numberOfRows = 0;
            if(dbNumOfRowsResult.next()) {
                numberOfRows = dbNumOfRowsResult.getInt("total");
            }
            
            //select orders 
            String fetchQuery = "SELECT OrderID FROM Orders WHERE DriverID=" + idriverID + "";
            ResultSet dbResults = stmt.executeQuery(fetchQuery); 
            
            //process results and assign values
            int[] orders = new int[numberOfRows];
            int i=0;
            while (dbResults.next()) {
                orders[i] = dbResults.getInt("OrderID");
                i++;
            }
            return orders;
        } catch(Exception e) {
            throw new Exception();
        }          
    }
 
    
    /**
    * CHECKFORNOTIFICATION
    * Returns notification for a given user
    * @param iuserType
    * @param iuserID
    * @return notification
    * @throws Exception
    */        
    public Notification checkForNotification(String iuserType, int iuserID) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();
            String fetchQuery = "";
            
            //select notification based on user type
            switch (iuserType) {
                case "DRIVER":
                    fetchQuery = "SELECT NotificationID, CustomerID"
                            + ", DriverID, OrderID, Message FROM Notifications WHERE DriverID=" + iuserID + "";
                    break;
                case "CUSTOMER":
                    fetchQuery = "SELECT NotificationID, CustomerID"
                            + ", DriverID, OrderID, Message FROM Notifications WHERE CustomerID=" + iuserID + "";
                    break;
            }
            
            //process results
            Notification notification = new Notification();
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            while(dbResults.next()) {
                //assign values
                int notID = dbResults.getInt("NotificationID");
                int custID = dbResults.getInt("CustomerID");
                int drivID = dbResults.getInt("DriverID");
                String mess = dbResults.getString("Message");
                int ordID = dbResults.getInt("OrderID");
                
                //create new notification
                notification = new Notification(notID, ordID, drivID, custID, mess);
            }
            return notification;
        } catch(Exception e) {
            throw new Exception();
        }
    }
    
    
    /**
    * GETORDER
    * Returns an order object for a given orderID
    * @param iorderID
    * @return order
    * @throws Exception
    */       
    public Order getOrder(int iorderID) throws Exception {
        try {        
            java.sql.Statement stmt = this.loginToDB();
            
            //select order details
            String fetchQuery = "SELECT Destination, PickUpPointLocation, Status, CustomerID, DriverID, " +
                                "Distance, TravelTime FROM Orders WHERE OrderID=" + iorderID + "";
            Order order = new Order();
            
            //process results
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            if(dbResults.next()) {
                //assign values
                String dest = dbResults.getString("Destination");
                String pupl = dbResults.getString("PickUpPointLocation");
                String stat = dbResults.getString("Status");
                int cust = dbResults.getInt("CustomerID");
                int driv = dbResults.getInt("DriverID");
                double dist = dbResults.getDouble("Distance");
                double tt = dbResults.getDouble("TravelTime");
                
                //create order object
                order = new Order();
                order.setOrderID(iorderID);
                order.setCustomerID(cust);
                order.setDestination(dest);
                order.setDistance(dist);
                order.setDriverID(driv);
                order.setPickupPointLocation(pupl);
                order.setStatus(stat);
                order.setTravelTime(tt);               
            } 
            return order;
        } catch(Exception e) {
            throw new Exception();
        }
    }


    /**
    * GETORDERSFORTABLE
    * Returns order objects to be included in the dispatcher's table
    * @return order
    * @throws Exception
    */     
    public Order[] getOrdersForTable() throws Exception {
        try {        
            java.sql.Statement stmt = this.loginToDB();
            
            //count orders in progress
            String numOfRowsQuery = "SELECT COUNT(*) AS total FROM NAME.Orders WHERE Status<>'CANCELLED'" +
                                       " AND STATUS<>'COMPLETED'";
            ResultSet dbNumOfRowsResult = stmt.executeQuery(numOfRowsQuery);
            int numberOfRows = 0;
            if(dbNumOfRowsResult.next()) {
                numberOfRows = dbNumOfRowsResult.getInt("total");
            }
            
            //select orders in progress
            String fetchQuery = "SELECT * FROM Orders WHERE Status<>'CANCELLED' AND STATUS<>'COMPLETED'";
            
            //process results
            //create array of orders
            Order orders[] = new Order[numberOfRows];
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            int i = 0;
            while(dbResults.next()) {
                //assign values
                int id = dbResults.getInt("OrderID");
                String dest = dbResults.getString("Destination");
                String pupl = dbResults.getString("PickUpPointLocation");
                String stat = dbResults.getString("Status");
                int cust = dbResults.getInt("CustomerID");
                int driv = dbResults.getInt("DriverID");
                double dist = dbResults.getDouble("Distance");
                double tt = dbResults.getDouble("TravelTime");
                
                //create order objects
                orders[i] = new Order();
                orders[i].setOrderID(id);
                orders[i].setCustomerID(cust);
                orders[i].setDestination(dest);
                orders[i].setDistance(dist);
                orders[i].setDriverID(driv);
                orders[i].setPickupPointLocation(pupl);
                orders[i].setStatus(stat);
                orders[i].setTravelTime(tt);
                i++;
            } 
            return orders;
        } catch(Exception e) {
            throw new Exception();
        }   
    }
    
    
    /**
    * GETDRIVERSTATUS
    * Returns driver object based on driver's username (LIKE)
    * @param iquery
    * @return driver
    * @throws Exception
    */         
    public Driver getDriverStats(String iquery) throws Exception {
        try {        
            java.sql.Statement stmt = this.loginToDB();
            
            //select drivers based on username
            String fetchQuery = "SELECT * FROM Drivers WHERE UserName LIKE '" + iquery + "%'";
            Driver driver = new Driver();
            
            //process results
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            if(dbResults.next()) {
                //assign values
                int id = dbResults.getInt("DriverID");
                String name = dbResults.getString("Name");
                String password = dbResults.getString("Password");
                String location = dbResults.getString("Location");
                String status = dbResults.getString("Status");
                String username = dbResults.getString("UserName");
                
                //create driver object
                driver.setID(id);
                driver.setDriverStatus(status);
                driver.setLocation(location);
                driver.setPassword(password);
                driver.setName(name);
                driver.setUserName(username);
                               
            } 
            return driver;
        } catch(Exception e) {
            throw new Exception();
        }
    }
    
    
    /**
    * COUNTDRIVERORDERS
    * Returns number of orders for a driver
    * @param idriverID
    * @return number of orders for a given driver
    * @throws Exception
    */     
    public int countDriverOrders(int idriverID) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();
            
            //count orders
            String fetchQuery = "SELECT COUNT(*) AS total FROM Orders WHERE DriverID=" + 
                                idriverID + "";
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            int numberOfOrders = 0;
            if(dbResults.next()) {
                numberOfOrders = dbResults.getInt("total");
            }
            
            return numberOfOrders;
        } catch(Exception e) {
            throw new Exception();
        }
    }
    
    
    /**
    * SUMDRIVERORDERSAMOUNT
    * Sums amounts of each orders for a driver
    * @param idriverID
    * @return total amount of each orders for a driver
    * @throws Exception
    */         
    public double sumDriverOrdersAmount(int idriverID) throws Exception {
        try {
            java.sql.Statement stmt = this.loginToDB();
            
            //sum distances
            String fetchQuery = "SELECT SUM(Distance) AS total FROM Orders WHERE DriverID=" + 
                                idriverID + "";
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            double sumOfOrders = 0;
            if(dbResults.next()) {
                //multiply by two - rate per km
                sumOfOrders = dbResults.getInt("total")*2;
            }
            
            return sumOfOrders;
        } catch(Exception e) {
            throw new Exception();
        }
    }    

    
    /**
    * GETDRIVERSFORTABLE
    * Gets active drivers for dispatcher's table
    * @return array of driver objects
    * @throws Exception
    */    
    public Driver[] getDriversForTable() throws Exception {
        try {        
            java.sql.Statement stmt = this.loginToDB();
            
            //count active drivers
            String numOfRowsQuery = "SELECT COUNT(*) AS total FROM NAME.Drivers WHERE Status<>'LOGGED_OUT'";
            ResultSet dbNumOfRowsResult = stmt.executeQuery(numOfRowsQuery);
            int numberOfRows = 0;
            if(dbNumOfRowsResult.next()) {
                numberOfRows = dbNumOfRowsResult.getInt("total");
            }
            
            //select active drivers
            String fetchQuery = "SELECT * FROM Drivers WHERE Status<>'LOGGED_OUT'";
            Driver drivers[] = new Driver[numberOfRows];
            
            //process results
            ResultSet dbResults = stmt.executeQuery(fetchQuery);
            int i = 0;
            while(dbResults.next()) {
                //assign values
                int id = dbResults.getInt("DriverID");
                String name = dbResults.getString("Name");
                String location = dbResults.getString("Location");
                String stat = dbResults.getString("Status");
                String pass = dbResults.getString("Password");
                String user = dbResults.getString("UserName");
                
                //create driver object
                drivers[i] = new Driver();
                drivers[i].setID(id);
                drivers[i].setName(name);
                drivers[i].setLocation(location);
                drivers[i].setDriverStatus(stat);
                drivers[i].setUserName(user);
                drivers[i].setPassword(pass);
                i++;
            } 
            return drivers;
        } catch(Exception e) {
            throw new Exception();
        }   
    }
}