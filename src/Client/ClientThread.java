
package Client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

import codendiswindow.CoderWindow;

/**
 *
 * @author Kunal
 */
public class ClientThread extends Thread {

    CoderWindow Cwindow;
    
    String fcName;
    
    Socket comSocket;
    BufferedReader input;
    BufferedReader uinput;
    PrintWriter output;
    
    PrintWriter pw;
    InputStream ins;
    DataInputStream dins;
    OutputStream os;
    DataOutputStream dos;
    private final String userName;
    public ClientThread(String username, CoderWindow cwindow, Socket talkerSocket) {
        this.Cwindow = cwindow;
        this.comSocket = talkerSocket;
        this.userName = username;
    }

   
    public void run() {
        try {
            Cwindow.startTalker();
            pw = new PrintWriter(comSocket.getOutputStream(), true);
            pw.println(userName);
            ins = comSocket.getInputStream();
            dins = new DataInputStream(ins);
            input = new BufferedReader(new InputStreamReader(comSocket.getInputStream()));
            fcName = input.readLine();
            Cwindow.setFriendName(fcName);
            do{
                String recievedMessage = dins.readUTF();
                try {
                    Cwindow.updateHistory(fcName, recievedMessage, 0);
//                    inputPane.setText("");
                    //process the recieved message
                } catch (BadLocationException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(fcName != null);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}