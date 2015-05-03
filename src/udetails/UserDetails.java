
package udetails;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Kunal
 */
public class UserDetails {
    protected static String UserName = "My Name";//getUserName();
    protected static String UserIPAddress = "127.0.0.1";//getUserIP();
    protected static int UserPortNumber = 10001;
    static String UserImage = "";//getUserAvtar();
    public static Boolean online = true;
    
    /**
     * private constructor to prevent the invoking of the default constructor.
     * Hence prevent any instance creation
     * @return void
     */
    private UserDetails(){
    }

    /**
     * public static method return the relative path to the user profile pic.
     * @return String avtarPath
     */
    public static String getUserAvtar(){
        String avtarPath = "";
        File f = new File("userAvtar/");
        
        File[] s ;
        s = f.listFiles();
        try {
            avtarPath = s[0].getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(UserDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avtarPath;
    }
    /**
     * 
     */
    private static String getUserIP() {
        String IPAddress = "";
        try {
            InetAddress localIp = InetAddress.getLocalHost();
            IPAddress = localIp.getHostAddress();
            UserName = localIp.getHostName();
        }
        catch(Exception e) {
            System.out.println("IP Error" + e);
        }
        return IPAddress;
    }
    /**
     * public static method return the username (user's host name).
     * Throws Exception if there is any problem in getting host name of local
     * machine.
     * @throws Exception e
     * @return String avtarPath
     */    
    public static String getUserName() {
        String uName = new String();
        try {
            InetAddress localIp = InetAddress.getLocalHost();
            uName = localIp.getHostName();
        }
        catch(Exception e) {
            System.out.println("IP Error" + e);
        }
        return uName;
    }
    /**
     * 
     */
    public static void setUserName(String userName) {
        UserName = userName;
    }
    /**
     * 
     */
    public static int getUserPort() {
        return UserPortNumber;
    }
}
