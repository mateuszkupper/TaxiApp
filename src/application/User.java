/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javax.swing.JOptionPane;

public class User {
    protected String userName;
    protected String password;
    protected IOdb database;
    private int ID;
    
    public User(String iuserName, String ipassword){
        database = new IOdb();
        setUserName(iuserName);
        setPassword(ipassword);
    }
    
    protected void logIn(String iuserType, String ilocation) throws Exception {
        try {
            database.logIn(iuserType, this.userName, this.password, ilocation);
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

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
