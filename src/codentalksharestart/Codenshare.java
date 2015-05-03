
package codentalksharestart;


import Startup.Startupwindow;

import javax.swing.JFrame;

/**
 *
 * @author Kunal
 */
public class Codenshare {

    
    public static void main(String[] args) {
        JFrame startupFrame = Startupwindow.getStartupPlace();
        cnshare cns = new cnshare(startupFrame);
        cns.start();
    }
}
