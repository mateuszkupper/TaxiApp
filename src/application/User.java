/**
* <h1>USER CLASS</h1>
* The class represents a user driver in an application
* Instances created when user logs in to the application
* The class is inherited by dispatcher, driver and customer class
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/
package application;

import javax.swing.JOptionPane;

public class User {
    
    
    //CLASS VARIABLES
    protected String userName;
    protected String password;
    protected IOdb database;
    private int ID;
    
    
    /**
    * CONSTRUCTOR
    */        
    public User() {
    
    }


    /**
    * CONSTRUCTOR
    * Sets variables and connects to db
    * @param iuserName
    * @param ipassword
    */      
    public User(String iuserName, String ipassword){
        database = new IOdb();
        setUserName(iuserName);
        setPassword(ipassword);
    }
    
    
    /**
    * LOGIN
    * Logs in user
    * @param iuserType
    * @param ilocation
    * @throws Exception
    */    
    protected void logIn(String iuserType, String ilocation) throws Exception {
        try {
            //login in user
            database.logIn(iuserType, this.userName, this.password, ilocation);
            //get user ID
            this.setID(database.getID(this.userName, iuserType));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Login and password do not match!", "InfoBox: " + "Login", JOptionPane.INFORMATION_MESSAGE);
            throw new Exception();
        }
    }
    
    
    /*
    * Getters & Setters
    */
    public String getUserName() {
        return this.userName;
    }
    
    public final void setUserName(String iuserName) {
        this.userName = iuserName;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public final void setPassword(String ipassword) {
        this.password = ipassword;
    } 

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
