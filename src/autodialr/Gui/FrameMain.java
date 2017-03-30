/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autodialr.Gui;

import autodialr.Engine.DialThread;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalbo
 */
public class FrameMain extends javax.swing.JFrame {

    /**
     * Creates new form FrameMain
     */
    public FrameMain() {
        initComponents();
//        initThread();
        
        initAll();
        oStatus.setText("Ready!!!");
    }
    
    public void initAll(){
        initThread();
        reloadButton();
        invokeDelay();
        invokeTrial();
//        connectedTime();
    }
    
    int delay;
    public void invokeDelay(){
        delay = slideDelay.getValue();
        oDelayVal.setText(delay+" ms");
        dialThread.setDelay(delay);
    }
    
    int trial;
    public void invokeTrial(){
        trial = slideTrial.getValue();
        oTrialVal.setText(trial+" X");
        dialThread.setMaxTrial(trial);
    }
    
    boolean alive;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public void connectedTime(){
        new Thread(new Runnable() {
            long delta = 0;
            long start;
            long end;
            boolean init = true;
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrameMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if(alive && init){
                        start = System.currentTimeMillis();
                        init = false;
                    } else if(alive && !init){
                        end = System.currentTimeMillis();
                    } else if(!alive){
                        start = 0;
                        end = 0;
                        init = true;
                    }
                    delta = end-start;
                    oDuration.setText(sdf.format(new Date(delta)));
                } 
            }
        }).start();
    }
    
    DialThread dialThread;
    boolean isStarted = false;
    public void initThread(){
        dialThread = new DialThread(iConnName.getText()) {
            @Override
            public void onCommandResult(String result) {
                oStatus.setText(result);
            }
            
            @Override
            public void onConnectionCheck(boolean connected, String msg) {
                oStatus.setText(msg);
                if(connected){
                    oStatus.setForeground(Color.GREEN);
                } else {
                    oStatus.setForeground(Color.red);
                }
            }

            @Override
            public void onConnectionCheck(boolean connected, String msg, int trial) {
                alive = connected;
                oStatus.setText("("+trial+") "+msg);
                if(connected){
                    oStatus.setForeground(Color.GREEN);
                } else {
                    oStatus.setForeground(Color.red);
                }
            }

            @Override
            public void onInfoChanged(String info) {
                oStatus.setText(info);
                oStatus.setForeground(Color.black);
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bStop = new javax.swing.JButton();
        bStart = new javax.swing.JButton();
        oStatus = new javax.swing.JLabel();
        iConnName = new javax.swing.JTextField();
        slideDelay = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        oDelayVal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        slideTrial = new javax.swing.JSlider();
        oTrialVal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        oDuration = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Axis autodialer");

        bStop.setText("Stop");
        bStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStopActionPerformed(evt);
            }
        });

        bStart.setText("Start");
        bStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });

        oStatus.setBackground(new java.awt.Color(255, 255, 255));
        oStatus.setText("Status");

        iConnName.setText("The Dials");

        slideDelay.setMajorTickSpacing(1000);
        slideDelay.setMaximum(10000);
        slideDelay.setMinimum(1000);
        slideDelay.setPaintTicks(true);
        slideDelay.setSnapToTicks(true);
        slideDelay.setValue(3000);
        slideDelay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideDelayStateChanged(evt);
            }
        });

        jLabel1.setText("Delay");

        oDelayVal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        oDelayVal.setText("jLabel2");

        jLabel2.setText("Max try");

        slideTrial.setMajorTickSpacing(1);
        slideTrial.setMaximum(10);
        slideTrial.setPaintLabels(true);
        slideTrial.setPaintTicks(true);
        slideTrial.setSnapToTicks(true);
        slideTrial.setValue(3);
        slideTrial.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideTrialStateChanged(evt);
            }
        });

        oTrialVal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        oTrialVal.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(iConnName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bStop))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(oStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oDuration))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(slideTrial, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                            .addComponent(slideDelay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(oTrialVal, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(oDelayVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bStop)
                    .addComponent(bStart)
                    .addComponent(iConnName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(slideDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oDelayVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(oTrialVal)
                    .addComponent(slideTrial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oStatus)
                    .addComponent(oDuration))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void bStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStartActionPerformed
        // TODO add your handling code here:
        dialThread.runCommand("rasphone -d \"" + iConnName.getText() + "\"");
        new Thread(dialThread).start();
        isStarted = true;
        reloadButton();
    }//GEN-LAST:event_bStartActionPerformed

    private void bStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStopActionPerformed
        // TODO add your handling code here:
        dialThread.stop();
        isStarted = false;
        reloadButton();
    }//GEN-LAST:event_bStopActionPerformed

    private void slideDelayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideDelayStateChanged
        // TODO add your handling code here:
        invokeDelay();
    }//GEN-LAST:event_slideDelayStateChanged

    private void slideTrialStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideTrialStateChanged
        // TODO add your handling code here:
        invokeTrial();
    }//GEN-LAST:event_slideTrialStateChanged

    public void reloadButton(){
        if(isStarted){
            bStart.setEnabled(false);
            bStop.setEnabled(true);
            
        } else {
            bStart.setEnabled(true);
            bStop.setEnabled(false);
        }
    }
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
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bStart;
    private javax.swing.JButton bStop;
    private javax.swing.JTextField iConnName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel oDelayVal;
    private javax.swing.JLabel oDuration;
    private javax.swing.JLabel oStatus;
    private javax.swing.JLabel oTrialVal;
    private javax.swing.JSlider slideDelay;
    private javax.swing.JSlider slideTrial;
    // End of variables declaration//GEN-END:variables
}