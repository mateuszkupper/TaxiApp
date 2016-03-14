/**
* <h1>CUSTOMER HOMEPAGE</h1>
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

public class FormCustomerHomepage extends javax.swing.JFrame {
    
    
    //CLASS VARIABLES
    private Customer customer;
    private Order order;
    private Notification notification;
    private Notification notificationCancel;

    
    public FormCustomerHomepage() {
        initComponents();
        
        //start timer
        //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
        
        //set components
        lblTime.setVisible(false);
        lblTimeTime.setVisible(false);
        lblDriver.setVisible(false);
        lblDriverName.setVisible(false);
        
        //do nothing on close
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //log out customer on close
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                //if user found
                if(customer.getID()!=0){
                    try {
                        //if order found - cancel order
                        if(order!=null && order.getOrderID()!=0) {
                            customer.logOut(order.getOrderID());
                        } else {
                            customer.logOut(0);
                        }
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null, "Error logging out!", "Error" + "Login", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                JFrame frame = (JFrame)e.getSource();
                //close frame
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            }
        });
    }

    
    //set timer - perform checks notification every 2secs 
    //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
    Timer timer = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            //set customer name
            lblCustomerName.setText(getCustomer().getUserName());
            IOdb database = new IOdb();
            try {
                //check for notification
                notification = database.checkForNotification("CUSTOMER", customer.getID());
                //if notification exists
                if(notification.getNotificationID()!=0) {
                    //if message is not null
                    if(null != notification.getMessage()) {
                        switch (notification.getMessage()) {
                            //if trip accepted perform actions
                            case "TRIP_ACCEPTED":
                                notificationCancel=notification;
                                JOptionPane.showMessageDialog(null, "Trip accepted!", "Trip",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                customer.handleNotification(notification);
                                order = database.getOrder(notification.getOrderID());
                                //set components
                                txtPickUpPoint.setEnabled(false);
                                txtDestination.setEnabled(false);
                                lblDriver.setVisible(true);
                                lblTime.setVisible(true);
                                chkPickMeUphere.setEnabled(false);
                                lblTimeTime.setVisible(true);
                                lblDriverName.setVisible(true);
                                lblTimeTime.setText(String.valueOf(order.getTravelTime()) + " minutes");
                                lblDriverName.setText(String.valueOf(notification.getDriverID()));
                                btnRequest.setEnabled(false);
                                btnCancelOrder.setEnabled(true);
                                break;
                            case "NO_DRIVERS":
                                order=null;
                                notificationCancel=notification;
                                JOptionPane.showMessageDialog(null, "The trip was cancelled by a driver - "
                                                            + "there are no other drivers available!", "Error", 
                                                             JOptionPane.INFORMATION_MESSAGE);
                                customer.handleNotification(notification);
                                
                                //set components
                                txtPickUpPoint.setEnabled(true);
                                txtDestination.setEnabled(true);
                                lblDriver.setVisible(false);
                                lblTime.setVisible(false);
                                chkPickMeUphere.setEnabled(true);
                                lblTimeTime.setVisible(false);
                                lblDriverName.setVisible(false);
                                lblTimeTime.setText("");
                                lblDriverName.setText("");
                                btnRequest.setEnabled(true);
                                btnCancelOrder.setEnabled(false);
                                break;
                            case "AT_PICK_UP_POINT":
                                notificationCancel=notification;
                                JOptionPane.showMessageDialog(null, "The driver arrived at the pick up point!", 
                                                            "Order", JOptionPane.INFORMATION_MESSAGE);
                                customer.handleNotification(notification);
                                order = database.getOrder(notification.getOrderID());
                                //set components
                                txtPickUpPoint.setEnabled(true);
                                txtDestination.setEnabled(true);
                                lblDriver.setVisible(false);
                                lblTime.setVisible(false);
                                chkPickMeUphere.setEnabled(true);
                                lblTimeTime.setVisible(false);
                                lblDriverName.setVisible(false);
                                lblTimeTime.setText("");
                                lblDriverName.setText("");
                                btnRequest.setEnabled(false);
                                btnCancelOrder.setEnabled(false);
                                break;
                            case "TRIP_FINISHED":
                                order=null;
                                notificationCancel=notification;
                                customer.handleNotification(notification);
                                //set components
                                btnRequest.setEnabled(true);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error getting a notification!", "Error", 
                                                JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPickUpPoint = new javax.swing.JLabel();
        lblDestination = new javax.swing.JLabel();
        txtPickUpPoint = new javax.swing.JTextField();
        txtDestination = new javax.swing.JTextField();
        btnRequest = new javax.swing.JButton();
        chkPickMeUphere = new javax.swing.JCheckBox();
        lblDriver = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDriverName = new javax.swing.JLabel();
        lblTimeTime = new javax.swing.JLabel();
        btnCancelOrder = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblCustomerName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPickUpPoint.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblPickUpPoint.setText("Pick Up Point");

        lblDestination.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDestination.setText("Destination");

        btnRequest.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnRequest.setText("Request");
        btnRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestActionPerformed(evt);
            }
        });

        chkPickMeUphere.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        chkPickMeUphere.setText("Pick Me Up Here");
        chkPickMeUphere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPickMeUphereActionPerformed(evt);
            }
        });

        lblDriver.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDriver.setText("Driver ID:");

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTime.setText("Time:");

        lblDriverName.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDriverName.setText("jLabel1");

        lblTimeTime.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTimeTime.setText("jLabel1");

        btnCancelOrder.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnCancelOrder.setText("Cancel Order");
        btnCancelOrder.setEnabled(false);
        btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelOrderActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Welcome");

        lblCustomerName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPickUpPoint)
                            .addComponent(lblDestination)
                            .addComponent(lblTime)
                            .addComponent(lblDriver))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDriverName)
                            .addComponent(chkPickMeUphere)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPickUpPoint, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtDestination, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTimeTime))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriver)
                    .addComponent(lblDriverName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(lblTimeTime))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPickUpPoint)
                    .addComponent(txtPickUpPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestination)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkPickMeUphere)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRequest)
                    .addComponent(btnCancelOrder))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        String pickUpPoint = txtPickUpPoint.getText();
        String destination = txtDestination.getText();
        //if not blank
        if (!"".equals(pickUpPoint) && !"".equals(destination)) {
            JOptionPane.showMessageDialog(null, "Wait for a driver to respond", "Wait", 
                                            JOptionPane.INFORMATION_MESSAGE);
            customer.orderTaxi(pickUpPoint, destination);
        } else {
            JOptionPane.showMessageDialog(null, "Fill in all fields!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnRequestActionPerformed

    //if check box checked/unchecked
    private void chkPickMeUphereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPickMeUphereActionPerformed
        //checked
        if(chkPickMeUphere.isSelected()==false) {
            txtPickUpPoint.setText("");
            txtPickUpPoint.setEnabled(true);
        //unchecked
        } else {
            txtPickUpPoint.setText("Here");
            txtPickUpPoint.setEnabled(false);            
        }           
    }//GEN-LAST:event_chkPickMeUphereActionPerformed

    private void btnCancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelOrderActionPerformed
        try {
            //cancel taxi
            customer.cancelTaxi(notificationCancel.getOrderID());
            //set components
            txtPickUpPoint.setEnabled(true);
            txtDestination.setEnabled(true);
            lblDriver.setVisible(false);
            lblTime.setVisible(false);
            chkPickMeUphere.setEnabled(true);
            lblTimeTime.setVisible(false);
            lblDriverName.setVisible(false);
            lblTimeTime.setText("");
            lblDriverName.setText("");
            btnRequest.setEnabled(true);
            btnCancelOrder.setEnabled(false);
            txtPickUpPoint.setText("");
            txtDestination.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error getting a notification!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCancelOrderActionPerformed

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
            java.util.logging.Logger.getLogger(FormCustomerHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCustomerHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCustomerHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCustomerHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCustomerHomepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnRequest;
    private javax.swing.JCheckBox chkPickMeUphere;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblDriver;
    private javax.swing.JLabel lblDriverName;
    private javax.swing.JLabel lblPickUpPoint;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTimeTime;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtPickUpPoint;
    // End of variables declaration//GEN-END:variables

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
