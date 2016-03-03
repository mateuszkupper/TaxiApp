/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

public class User {
    protected String userName;
    protected String password;
    private String name;
    
    public User(String iuserName, String ipassword){
        setUserName(iuserName);
        setPassword(ipassword);
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
}
