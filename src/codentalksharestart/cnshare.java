
package codentalksharestart;

import Server.ServerThread;
import javax.swing.JFrame;

import udetails.UserDetails;

/**
 * class to start the server thread makes the startup place visible
 *
 * @author Kunal
 */
class cnshare extends Thread {

    /**
     * private JFrame instance to hold the startup place JFrame object
     */
    private JFrame startupFrame;
    /**
     * private String object to hold local user name
     */
    private String userName;

    public cnshare(JFrame startupFrame) {
        this.startupFrame = startupFrame;
        userName = UserDetails.getUserName();
    }

    /**
     * overrides the run method starts the server thread and makes the startup
     * place visible
     */
    @Override
    public void run() {
        startupFrame.setSize(300, 400);
        startupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startupFrame.setVisible(true);
        ServerThread server = new ServerThread(10001, userName);
        server.start();
    }
}