/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author Mateusz
 */
public class IOnotifications {
    private IOdb database;
    
    IOnotifications() {
       database = new IOdb(); 
    }
    
    IOnotifications(int iorderID, int idriverID, int icustomerID, String imessage) {
        database = new IOdb();
        database.createNewNotification(iorderID, idriverID, icustomerID, imessage);
    }
    
    public void deleteNotification(int inotificationID) {
        database.deleteNotification(inotificationID);
    }
    
}
