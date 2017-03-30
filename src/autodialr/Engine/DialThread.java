/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autodialr.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalbo
 */
public abstract class DialThread implements Runnable {
    
    boolean started = true;
    int delay = 4000;
    Process p;
    String connection;
    int trial = 0;
    int maxTrial = 3;
    
    public DialThread(String connection) {
        this.connection = connection;
    }
    
    public void stop() {
        started = false;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
        onInfoChanged("Delay set to "+delay+" ms");
    }
    
    public void setMaxTrial(int maxTrial) {
        this.maxTrial = maxTrial;
        onInfoChanged("Max trial set to "+maxTrial+" time(s)");
    }
    
    @Override
    public void run() {
        while (started) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(DialThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!isConnected()) {
                trial++;
                if (trial > maxTrial) {
//                    runCommand("rasphone -h \"" + connection + "\" ;rasphone -d \"" + connection + "\"");
                    onCommandResult("Dialing " + connection);
                    runCommand("rasphone -h \"" + connection + "\"");
                    runCommand("rasphone -d \"" + connection + "\"");
                }
            } else {
                trial = 0;
            }
            
        }
    }
    
    public boolean isConnected() {
        Socket socket = null;
        long start = System.currentTimeMillis();
        try {
            socket = new Socket("www.google.com", 80);
        } catch (IOException ex) {
//            Logger.getLogger(DialThread.class.getName()).log(Level.SEVERE, null, ex);
            onConnectionCheck(false, ex.getMessage(), trial);
            return false;
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(DialThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        long end = System.currentTimeMillis();
        long delta = (end-start);
        onConnectionCheck(true, "Connected! ("+delta+" ms)");
        return true;
    }
    
    public void runCommand(String command) {
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder buff = new StringBuilder();
            String temp = "";
            while ((temp = br.readLine()) != null) {
                buff.append(temp);
            }
            onCommandResult(buff.toString());
        } catch (IOException ex) {
            Logger.getLogger(DialThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public abstract void onCommandResult(String result);

    public abstract void onConnectionCheck(boolean connected, String msg);
    public abstract void onConnectionCheck(boolean connected, String msg, int trial);
    public abstract void onInfoChanged(String info);
}
