/**
* <h1>DISPATCHER HOMEPAGE</h1>
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class FormDispatcherHomepage extends javax.swing.JFrame {
    
    
    //CLASS VARIABLES
    private Dispatcher dispatcher;
    private final IOdb database;
    private Order order;
    private Notification notification;
    private Notification notificationCancel;    

    
    public FormDispatcherHomepage() {
        database = new IOdb();
        initComponents();
        
        //start timer
        //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
        //dispose on close
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
    //set timer - perform checks notification every 2secs
    //Code from http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java    
    Timer timer = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            IOdb database = new IOdb();
            try {
                //check for notification
                notification = database.checkForNotification("CUSTOMER", dispatcher.getID());
                //if notification exists
                if(notification.getNotificationID()!=0) {                    
                        if(null != notification.getMessage()) {
                            
                            switch (notification.getMessage()) {
                                case "TRIP_ACCEPTED":
                                    notificationCancel=notification;
                                    JOptionPane.showMessageDialog(null, "Trip accepted!", "Trip",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    dispatcher.handleNotification(notification);
                                    order = database.getOrder(notification.getOrderID());
                                    break;
                                case "NO_DRIVERS":
                                    notificationCancel=notification;
                                    JOptionPane.showMessageDialog(null, "The trip (" + notification.getOrderID() +
                                            ") was cancelled by a driver - there are no other "
                                            + "drivers available!", "Order",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    dispatcher.handleNotification(notification);
                                    order = database.getOrder(notification.getOrderID());
                                    break;
                                case "AT_PICK_UP_POINT":
                                    dispatcher.handleNotification(notification);
                                    break;
                                case "TRIP_FINISHED": 
                                    dispatcher.handleNotification(notification);
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
            
            //BUILD TABLES
            try {
                //TABLE ORDERS
                //get table model
                DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
                //clean table
                model.setRowCount(0);
                model = (DefaultTableModel) tblOrders.getModel();
                //get orders
                Order[] orders = database.getOrdersForTable();
                //populate table
                for (Order orderTable : orders) {
                    model.addRow(new Object[]{orderTable.getOrderID(), 
                                                orderTable.getStatus(), 
                                                orderTable.getPickupPointLocation(), 
                                                orderTable.getDestination(), 
                                                orderTable.getDriverID(), 
                                                orderTable.getCustomerID()});
                }                          
                
                //TABLE DRIVERS
                //get table model
                DefaultTableModel modelDrivers = (DefaultTableModel) tblDrivers.getModel();
                //clean table
                modelDrivers.setRowCount(0);
                modelDrivers = (DefaultTableModel) tblDrivers.getModel();
                //get drivers
                Driver[] drivers = database.getDriversForTable();
                //populate table
                for (Driver driverTable : drivers) {
                    modelDrivers.addRow(new Object[]{driverTable.getID(), 
                                                driverTable.getDriverStatus(), 
                                                driverTable.getName(), 
                                                driverTable.getLocation()});
                }             
            } catch (Exception ex) {

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnStatistics = new javax.swing.JButton();
        btnSignUpDriver = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDrivers = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

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

        tblOrders.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OrderID", "Status", "Pick Up Point", "Destination", "DriverID", "CustomerID"
            }
        ));
        tblOrders.setColumnSelectionAllowed(true);
        tblOrders.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblOrders);
        tblOrders.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblOrders.getColumnModel().getColumnCount() > 0) {
            tblOrders.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel1.setText("Order Queue");

        btnStatistics.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnStatistics.setText("Statistics");
        btnStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsActionPerformed(evt);
            }
        });

        btnSignUpDriver.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnSignUpDriver.setText("Sign Up Driver");
        btnSignUpDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpDriverActionPerformed(evt);
            }
        });

        tblDrivers.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tblDrivers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DriverID", "Status", "Name", "Location"
            }
        ));
        tblDrivers.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDrivers);
        tblDrivers.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel2.setText("Drivers logged in");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPickUpPoint)
                            .addComponent(lblDestination))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPickUpPoint, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDestination, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSignUpDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStatistics, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPickUpPoint)
                    .addComponent(txtPickUpPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestination)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btnSignUpDriver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
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
            dispatcher.orderTaxi(pickUpPoint, destination);
        } else {
            JOptionPane.showMessageDialog(null, "Fill in all fields!", "Error", 
                                            JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnRequestActionPerformed

    private void btnStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsActionPerformed
        //open stats form
        FormDriverStats newFrame = new FormDriverStats();
        newFrame.setVisible(true);
    }//GEN-LAST:event_btnStatisticsActionPerformed

    private void btnSignUpDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpDriverActionPerformed
        //open sign up driver form
        FormDriverRegistration newFrame = new FormDriverRegistration();
        newFrame.setDispatcher(dispatcher);
        newFrame.setVisible(true);        
    }//GEN-LAST:event_btnSignUpDriverActionPerformed


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
            java.util.logging.Logger.getLogger(FormDispatcherHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDispatcherHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDispatcherHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDispatcherHomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDispatcherHomepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRequest;
    private javax.swing.JButton btnSignUpDriver;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblPickUpPoint;
    private javax.persistence.Query ordersQuery;
    private javax.persistence.Query ordersQuery1;
    private javax.persistence.EntityManager taxiDBPUEntityManager;
    private javax.swing.JTable tblDrivers;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtPickUpPoint;
    // End of variables declaration//GEN-END:variables

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
