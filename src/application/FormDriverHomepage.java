/**
* <h1>DRIVER HOMEPAGE</h1>
* <p>
* 
*
* @author Mateusz Kupper, Luke Merriman, Eoin Feeney
* @version 1.0
* @since   2016-03-13 
*/

package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class FormDriverHomepage extends javax.swing.JFrame  {
    
    
    //CLASS VARIABLES
    private Driver driver;
    private Order order;
    private final IOdb database;
    private Notification notification;
    private Notification notificationCancel;

    
    public FormDriverHomepage() {
        initComponents();
        database = new IOdb();
        //set timer
        //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
        
        //do nothing on close        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //log out driver on close
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                //if user found
                if(driver.getID()!=0){
                    try {
                        driver.logOut();
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null, "Error logging out!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                JFrame frame = (JFrame)e.getSource();
                //close frame
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblDistance = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        txtDistance = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        btnConfirmLift = new javax.swing.JButton();
        lblPickUpPoint = new javax.swing.JLabel();
        lblDestination = new javax.swing.JLabel();
        txtPickUpPoint = new javax.swing.JTextField();
        txtDestination = new javax.swing.JTextField();
        lblCost = new javax.swing.JLabel();
        txtCost = new javax.swing.JTextField();
        btnArrived = new javax.swing.JButton();
        btnRecordTrip = new javax.swing.JButton();
        btnCancelTrip = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblDriverName = new javax.swing.JLabel();
        btnConfirmAtStand = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Welcome");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblDistance.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDistance.setText("Distance");

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTime.setText("Time");

        txtDistance.setEnabled(false);

        txtTime.setEnabled(false);

        btnConfirmLift.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnConfirmLift.setText("Finish Lift");
        btnConfirmLift.setEnabled(false);
        btnConfirmLift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmLiftActionPerformed(evt);
            }
        });

        lblPickUpPoint.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblPickUpPoint.setText("Pick Up Point");

        lblDestination.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDestination.setText("Destination");

        txtPickUpPoint.setEnabled(false);

        lblCost.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblCost.setText("Cost");

        txtCost.setEnabled(false);

        btnArrived.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnArrived.setText("Arrived At Pick Up Point");
        btnArrived.setEnabled(false);
        btnArrived.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArrivedActionPerformed(evt);
            }
        });

        btnRecordTrip.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnRecordTrip.setText("Record Trip");
        btnRecordTrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordTripActionPerformed(evt);
            }
        });

        btnCancelTrip.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnCancelTrip.setText("Cancel Trip");
        btnCancelTrip.setEnabled(false);
        btnCancelTrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelTripActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Welcome");

        lblDriverName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnConfirmAtStand.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnConfirmAtStand.setText("At Stand");
        btnConfirmAtStand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmAtStandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRecordTrip, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelTrip, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmAtStand, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnArrived, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfirmLift, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDestination)
                                    .addComponent(lblDistance)
                                    .addComponent(lblPickUpPoint))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDestination, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                                    .addComponent(txtPickUpPoint)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCost)
                                    .addComponent(lblTime)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDriverName)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblDriverName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPickUpPoint)
                    .addComponent(txtPickUpPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestination)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDistance))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCost)
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRecordTrip)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelTrip)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnArrived)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmLift))
                    .addComponent(btnConfirmAtStand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnCancelTripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelTripActionPerformed
        try {
            //cancel trip
            driver.cancelOrder(order.getOrderID());
            database.executeUpdateQuery("UPDATE NAME.Drivers SET Status='AVAILABLE' WHERE DriverID=" + 
                                        notificationCancel.getDriverID());
            //set components
            txtDestination.setEnabled(true);
            btnCancelTrip.setEnabled(false);
            btnArrived.setEnabled(false);
            btnRecordTrip.setEnabled(true);
            btnConfirmLift.setEnabled(false);
            btnConfirmAtStand.setEnabled(true);
            
            txtPickUpPoint.setText("");
            txtDestination.setText("");
            txtDistance.setText("");
            txtTime.setText("");
            txtCost.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_btnCancelTripActionPerformed

    private void btnRecordTripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordTripActionPerformed
        try {                                              
            String destination = txtDestination.getText();
            //if not blank
            if (!"".equals(destination)) {
                //record trip
                int orderID = driver.recordTripDetails(destination);
                //set components
                txtDestination.setEnabled(false);
                btnCancelTrip.setEnabled(false);
                btnArrived.setEnabled(false);
                btnRecordTrip.setEnabled(false);
                btnConfirmLift.setEnabled(true);
                btnConfirmAtStand.setEnabled(false);

                try {
                    //get order
                    IOdb db = new IOdb();
                    Order order1 = db.getOrder(orderID);
                    order = db.getOrder(orderID);
                    //set components
                    txtPickUpPoint.setText(order1.getPickupPointLocation());
                    txtDestination.setText(destination);
                    txtDistance.setText(String.valueOf(order1.getDistance()) + " km");
                    txtTime.setText(String.valueOf(order1.getTravelTime()) + " minutes");
                    txtCost.setText("€" + String.valueOf(order1.getDistance()*2));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Enter destination!", "Error", 
                                                JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnRecordTripActionPerformed

    private void btnArrivedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArrivedActionPerformed
        try {
            //confirm arrival at pick up point
            driver.confirmArrivalAtPickUpPoint(order.getOrderID());
            //set components
            btnCancelTrip.setEnabled(false);
            btnConfirmLift.setEnabled(true);
            btnArrived.setEnabled(false);
            btnConfirmAtStand.setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnArrivedActionPerformed

    private void btnConfirmLiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmLiftActionPerformed
        try {
            //confirm completed trip
            driver.confirmCompletedTrip(order.getOrderID());
            //set components
            btnCancelTrip.setEnabled(false);
            btnRecordTrip.setEnabled(true);
            btnArrived.setEnabled(false);
            btnConfirmLift.setEnabled(false);
            btnConfirmAtStand.setEnabled(true);
            txtDestination.setEnabled(true);
            txtPickUpPoint.setText("");
            txtDestination.setText("");
            txtCost.setText("");
            txtTime.setText("");
            txtDistance.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmLiftActionPerformed

    private void btnConfirmAtStandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmAtStandActionPerformed
        driver.confirmAtDesignatedStand();
        JOptionPane.showMessageDialog(null, "Updated location!", "Location", 
                                        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnConfirmAtStandActionPerformed

    //set timer - perform checks notification every 2secs
    //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
    Timer timer = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            //set driver name
            lblDriverName.setText(getDriver().getUserName());
            IOdb database = new IOdb();
            try {
                //check for notification
                notification = database.checkForNotification("DRIVER", driver.getID());
                //if notification exists
                if(notification.getNotificationID()!=0) {
                    //if trip requested perform actions
                    if("TRIP_REQUEST".equals(notification.getMessage())) {
                        order = database.getOrder(notification.getOrderID());
                        order.setOrderID(notification.getOrderID());
                        notificationCancel=notification;
                        int reply = JOptionPane.showConfirmDialog(null, "New trip request from: " + 
                                                                    order.getPickupPointLocation() + ", to: " + 
                                                                    order.getDestination() + 
                                                                    ". Accept?", "Trip request", 
                                                                    JOptionPane.YES_NO_OPTION);
                        //if accepted
                        if(reply==JOptionPane.YES_OPTION) {
                            driver.handleNotification(notification, reply);
                            //set components
                            txtDestination.setEnabled(false);
                            btnCancelTrip.setEnabled(true);
                            btnArrived.setEnabled(true);
                            btnRecordTrip.setEnabled(false);                           

                            txtPickUpPoint.setText(order.getPickupPointLocation());
                            txtDestination.setText(order.getDestination());
                            txtDistance.setText(String.valueOf(order.getDistance()) + " km");
                            txtTime.setText(String.valueOf(order.getTravelTime()) + " minutes");
                            txtCost.setText("€" + String.valueOf(order.getDistance()*2));
                        //if rejected
                        } else {
                            driver.handleNotification(notification, reply);
                        }
                    //if order cancelled by customer
                    } else if("CANCELLED".equals(notification.getMessage())) {
                        JOptionPane.showMessageDialog(null, "The order has been cancelled!", 
                                                        "Order Cancelled", 
                                                        JOptionPane.INFORMATION_MESSAGE);
                        driver.handleNotification(notification, 0);
                        //set components
                        txtDestination.setEnabled(true);
                        btnCancelTrip.setEnabled(false);
                        btnArrived.setEnabled(false);
                        btnRecordTrip.setEnabled(true);

                        txtPickUpPoint.setText("");
                        txtDestination.setText("");
                        txtDistance.setText("");
                        txtTime.setText("");
                        txtCost.setText("");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error getting a notification!", "Error", 
                                                JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDriverHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDriverHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDriverHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDriverHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDriverHomepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArrived;
    private javax.swing.JButton btnCancelTrip;
    private javax.swing.JButton btnConfirmAtStand;
    private javax.swing.JButton btnConfirmLift;
    private javax.swing.JButton btnRecordTrip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblDistance;
    private javax.swing.JLabel lblDriverName;
    private javax.swing.JLabel lblPickUpPoint;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtDistance;
    private javax.swing.JTextField txtPickUpPoint;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
