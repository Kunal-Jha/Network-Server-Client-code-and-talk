
package Communication;

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
public class Listener extends Thread {

    CoderWindow cWindow;
    String fcName;
    Socket csocket;
    BufferedReader incoming;
    BufferedReader uInput;
    PrintWriter outgoing;
    PrintWriter pw;
    InputStream iS;
    DataInputStream dIStream;
    OutputStream oStream;
    DataOutputStream dos;
    private final String userName;

    public Listener(String username, CoderWindow talkerWindow, Socket talkerSocket) {
        this.cWindow = talkerWindow;
        this.csocket = talkerSocket;
        this.userName = username;
    }

    
    public void run() {
        try {
            cWindow.startTalker();
            pw = new PrintWriter(csocket.getOutputStream(), true);
            pw.println(userName);
            iS = csocket.getInputStream();
            dIStream = new DataInputStream(iS);
            incoming = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
            fcName = incoming.readLine();
            cWindow.setFriendName(fcName);
            do {
                String recievedMessage = dIStream.readUTF();
                String messageType = recievedMessage.substring(0, recievedMessage.indexOf("##"));
                String message = recievedMessage.substring(recievedMessage.indexOf("##") + 2, recievedMessage.indexOf("$$"));
                try {
                    if (messageType.length() != 0) {
                        switch (messageType) {
                            case "REQUEST_TO_SEND_FILE":
                                String a = recievedMessage.substring(recievedMessage.indexOf("##") + 2, recievedMessage.toString().indexOf("$$"));
                                String b = recievedMessage.substring(recievedMessage.toString().indexOf("$_$") + 4, recievedMessage.toString().lastIndexOf("##"));
                                String c = recievedMessage.substring(recievedMessage.toString().lastIndexOf("##") + 2, recievedMessage.length() - 2);
                                cWindow.notifyForRequest(messageType, a, b, c);
                                break;
                            case "CLEAR_TO_SEND_FILE":
                                cWindow.notifyForRequest(messageType, "", "", "");
                                break;
                            case "CLOSING_TALK":
                                cWindow.updateHistory(fcName, "Your friend has closed the chat.\n He/she will not recieve any messages from you", 1);
                                cWindow.notifyForRequest(messageType, "", "", "");
                        }
                    } else {
                        cWindow.updateHistory(fcName, message, 0);
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(Client.ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (fcName != null);
        } catch (IOException ex) {
            //Logger.getLogger(Client.ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
