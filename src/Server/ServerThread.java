/*
 * Server thread to listen in comming requests
 * starts the talker window and a listener after the request is accepted
 */
package Server;

import Communication.Listener;
import javax.swing.*;

import udetails.UserDetails;

import codentalksharestart.*;

import java.awt.*;
import java.net.*;
import java.io.*;

/**
 * 
 * @author Kunal
 */
public class ServerThread extends Thread {

    private ServerSocket sSocket;
    private Socket chatSocket;
    private int SPort;
    private String userName;
/**
 * constructor
 * @param int port
 * @param String userName
 */
    public ServerThread(int port, String userName) {
        SPort = port;
        this.userName = userName;
    }
/**
 * Method overrides the superclass run method
 */
    @Override
    public void run() {
        try {
            UserDetails.setUserName(userName);
            sSocket = new ServerSocket(SPort);
            
            while (true) {
                chatSocket = sSocket.accept();
                InetAddress inet = chatSocket.getInetAddress();
                String ip = inet.getHostAddress();
              //System.out.println("Hello CNS");
                        if (!chatSocket.isClosed() && UserDetails.online) {
                            //int accept = JOptionPane.showConfirmDialog(null, "Somebody at " + chatSocket.getInetAddress() + " wants to talk to you. Want to talk?");
                            //if (accept == JOptionPane.YES_OPTION) {
                                codendiswindow.CoderWindow talkerWindow = new codendiswindow.CoderWindow(userName, chatSocket);
                                Listener listener = new Listener(userName, talkerWindow, chatSocket);
                                listener.start();
                            
                        } else {
                            chatSocket.close();
                        }
            }
        } catch (IOException | HeadlessException ioe) {
            System.out.println("Server Thread Error" + ioe);
        }
    }
}