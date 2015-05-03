/*
 * The startup place
 * Essentially a jframe object with all the components loaded
 * and all the components registered with the event handler
 */
package Startup;
import Communication.Listener;
import Register.IPRegister;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import udetails.UserDetails;

import codendiswindow.CoderWindow;

/**
 * 
 * @author Kunal
 */
public class Startupwindow {

    private static JFrame startupFrame;
    private static JPanel startupPanel, headerPanel;
    private static JMenuBar startupMenuBar;
    private static JMenu fileMenu, viewMenu, toolMenu, helpMenu;
    private static JLabel clientIPLabel, userNameLabel;
    private static JTextField clientIPField, userNameField;
    private static JButton connectButton, exitButton;
    private static GridBagConstraints headerPanelCons, clientIPLabelCons, userNameLabelCons, clientIPFieldCons, userNameFieldCons, connectButtonCons, exitButtonCons;
    private static StartupHandler startupHandler;
    private static JMenuItem exitMenuItem;

    /**
     * Returns a JFrame object(startup place)
     * with all the components and their event handlers
     * added
     * @return JFrame startupFrame
     */
    public static JFrame getStartupPlace() {
        initializeComponents();
        initializeConstraints();
        addComponents();
        attachHandlers();
        return startupFrame;
      //System.out.println("Hello CNS");
    }
    /**
     * Private static method to initialize
     * components(including the main JFrame object) of the startup place
     * @return void
     */
    private static void initializeComponents() {
        startupFrame = new JFrame("CodeNTalk");
        startupPanel = new JPanel();
        headerPanel = new JPanel();
        startupMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");
        toolMenu = new JMenu("Tool");
        helpMenu = new JMenu("Help");
        exitMenuItem = new JMenuItem("Exit");

        clientIPLabel = new JLabel("Client IP: ");
        userNameLabel = new JLabel("Your Name: ");

        clientIPField = new JTextField("127.0.0.1");
        userNameField = new JTextField("Your Name");

        connectButton = new JButton("Connect");
        exitButton = new JButton("Exit");

        headerPanelCons = new GridBagConstraints();
        clientIPLabelCons = new GridBagConstraints();
        userNameLabelCons = new GridBagConstraints();
        clientIPFieldCons = new GridBagConstraints();
        userNameFieldCons = new GridBagConstraints();
        connectButtonCons = new GridBagConstraints();
        exitButtonCons = new GridBagConstraints();
    }
    /**
     * private static method to initialize
     * GridbagLayout constraints for the components
     * of the startup place
     * @return void
     */
    private static void initializeConstraints() {
        // constraints for header panel
        headerPanelCons.gridx = 0;
        headerPanelCons.gridy = 0;
        headerPanelCons.gridheight = 1;
        headerPanelCons.gridwidth = 2;
        //headerPanelCons.ipady = 20;
        headerPanelCons.insets = new Insets(0, 0, 0, 0);

        // constraints for client ip label
        clientIPLabelCons.gridx = 0;
        clientIPLabelCons.gridy = 2;
        clientIPLabelCons.gridwidth = 1;
        clientIPLabelCons.gridheight = 1;
        clientIPLabelCons.insets = new Insets(15, 0, 55, 10);
        clientIPLabelCons.ipadx = 30;
        //clientIPLabelCons.ipady = 50;

        // constraints for client ip field
        clientIPFieldCons.gridx = 1;
        clientIPFieldCons.gridy = 2;
        clientIPFieldCons.gridwidth = 1;
        clientIPFieldCons.gridheight = 1;
        clientIPFieldCons.ipadx = 30;
        clientIPFieldCons.insets = new Insets(15, 10, 55, 10);

        // constraints for username label
        userNameLabelCons.gridx = 0;
        userNameLabelCons.gridy = 1;
        userNameLabelCons.gridwidth = 1;
        userNameLabelCons.gridheight = 1;
        userNameLabelCons.ipadx = 30;
        userNameLabelCons.insets = new Insets(0, 10, 55, 10);

        // constraints for user name field
        userNameFieldCons.gridx = 1;
        userNameFieldCons.gridy = 1;
        userNameFieldCons.gridwidth = 1;
        userNameFieldCons.gridheight = 1;
        userNameFieldCons.ipadx = 30;
        userNameFieldCons.insets = new Insets(0, 10, 55, 10);

        // constraints for connect button
        connectButtonCons.gridx = 0;
        connectButtonCons.gridy = 3;
        connectButtonCons.gridwidth = 1;
        connectButtonCons.gridheight = 1;

        // constraints for exit button
        exitButtonCons.gridx = 1;
        exitButtonCons.gridy = 3;
        exitButtonCons.gridwidth = 1;
        exitButtonCons.gridheight = 1;

        startupPanel.setLayout(new GridBagLayout());
    }
    /**
     * private static method to add
     * components to the startup place (JFrame) window
     * @return void
     */
    private static void addComponents() {
        fileMenu.add(exitMenuItem);
        startupMenuBar.add(fileMenu);
        //startupMenuBar.add(viewMenu);
        //startupMenuBar.add(toolMenu);
        //startupMenuBar.add(helpMenu);
        startupFrame.setJMenuBar(startupMenuBar);
        startupFrame.add(startupPanel);
        startupPanel.add(headerPanel, headerPanelCons);
        startupPanel.add(userNameLabel, userNameLabelCons);
        startupPanel.add(userNameField, userNameFieldCons);
        startupPanel.add(clientIPLabel, clientIPLabelCons);
        startupPanel.add(clientIPField, clientIPFieldCons);
        startupPanel.add(connectButton, connectButtonCons);
        startupPanel.add(exitButton, exitButtonCons);
    }
    /**
     * Private static method to add
     * handlers for the components of the startup place
     * @return void
     */
    private static void attachHandlers() {
        startupHandler = new StartupHandler();
        connectButton.addActionListener(startupHandler);
        exitMenuItem.addActionListener(startupHandler);
        exitButton.addActionListener(startupHandler);
    }

    /**
     *
     * class nested class to implement the action handler for the components in
     * the sign-in window
     */
    private static class StartupHandler implements ActionListener {

        /**
         *
         * Overrides the actionPerformed method in the ActionListener interface
         * Call the respective methods for handling the events on the components
         *
         * @param ActionEvent (e)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();                    // get the event source object
            if (source == connectButton) {                    // if connect button is clicked
                String clientIP = clientIPField.getText();    // remote ip
                String myUserName = userNameField.getText();  // local username
                int UserPortNumber = UserDetails.getUserPort(); // default port
                boolean clientFound = false;
                Socket codeSocket;
                if (IPRegister.isRegistered(clientIP)) {       // check if remote client's ip is already registered
                    JOptionPane.showMessageDialog(null, "You are already connected to this user. \n If you are unable to communicate with this user try closing the respective window and try again" + clientIP);
                } else {                                       // if remote client's ip not registered
                    try {
                        codeSocket = new Socket(clientIP, UserPortNumber);       // send request to the remote client
                        clientFound = true;
                        //codeSocket.connect();
                        CoderWindow coderWindow = new CoderWindow(myUserName, codeSocket);     // create the code window
                        Listener listener = new Listener(myUserName, coderWindow, codeSocket);   // create listener
                        listener.start();    // start the listener thread
                    } catch (UnknownHostException uhe) {
                        JOptionPane.showMessageDialog(null, "Host not found. Can't continue." + uhe);
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(null, "Either the specified IP Address is offline or the host is not in your network: " + ioe, " \nHost Not Found ", JOptionPane.WARNING_MESSAGE);
                    }
                    if (clientFound) {
                        IPRegister.registerIP(clientIP);
                    }
                }
            } else if (source == exitButton || source == exitMenuItem) { // id exit button or exit menu item is clicked
                System.exit(0);                // exit from the program
            }
        }
    }
}