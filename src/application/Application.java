/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mateusz
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
		/*try { https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyBB5o7ib_q9Q5Jk16daxVUHEhIB7LZjHyU
			URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=UCC+Cork&destinations=Midleton&key=AIzaSyBB5o7ib_q9Q5Jk16daxVUHEhIB7LZjHyU");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				System.out.println(strTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
                
                //Driver d = new Driver("drisfaver", "pascs");
                Dispatcher c = new Dispatcher("dispatcher", "pass");
                try {
                    //d.logOut();
                } catch(Exception e) {
                    
                } catch (Throwable ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
                
                
    }    
}
