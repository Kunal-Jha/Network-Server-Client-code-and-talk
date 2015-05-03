
package Sharing;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Kunal
 */
public class ReceiveFileThread extends Thread {
    Socket sock;
    String saveDir;
    String receiveFileDetails1 = new String();
    int fileSize = 0;
    JButton CancelSend;
    JProgressBar jpb;
    JLabel fileName;
    //int size = ;
    JFrame jf;
    public ReceiveFileThread(Socket sock, String saveDir, String fileDetails1, String fileDetails2) {
        
        this.sock = sock;
        this.saveDir = saveDir;
        //this.receiveFileDetails[0] = fileDetails[0];
        this.receiveFileDetails1 = fileDetails1;
        this.fileSize = Integer.parseInt(fileDetails2);
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            int bytesRead;
            int curr = 0;
            // receive file
            jf = new JFrame();
            jpb = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int)fileSize/1024);
            jpb.setStringPainted(true);
            fileName = new JLabel("File: "+receiveFileDetails1);
            jf = new JFrame("Recieving");
            CancelSend = new JButton("Cancel");
            //jf.setLayout(BorderLayout.);
            jf.setSize(400, 200);
            jf.add(fileName, BorderLayout.NORTH);
            jf.add(jpb, BorderLayout.CENTER);
            jf.add(CancelSend, BorderLayout.SOUTH);
            jf.setVisible(true);
            byte mybytearray[] = new byte[fileSize];
            long size = (int)fileSize / 1024;
            final InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream(saveDir +"/"+ receiveFileDetails1.substring(0, receiveFileDetails1.length()));
            //System.out.println("Hello CNS");
            final BufferedOutputStream bos = new BufferedOutputStream(fos);
            CancelSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        sock.close();
                        is.close();
                        bos.close();
                        jf.dispose();
                        //throw new UnsupportedOperationException("Not supported yet.");
                    } catch (IOException ex) {
                        Logger.getLogger(ReceiveFileThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            curr = bytesRead;
            int val = 0;
            do {
                bytesRead =
                        is.read(mybytearray, curr, (mybytearray.length - curr));
                if (bytesRead >= 0) {
                    jpb.setString(" "+curr/1024 + " KB of "+size+" KB recieved");
                    curr += bytesRead;
                    val = val+curr/1024/1024;
                    jpb.setValue(curr/1024);
                }
            } while (bytesRead > 0);
            bos.write(mybytearray, 0, curr);
            bos.flush();
            CancelSend.setText("close");
            bos.close();
            sock.close();
        } catch (HeadlessException | IOException e) {
            //System.out.println("Receive File Thread Error" + e);
            jf.dispose();
            JOptionPane.showMessageDialog(jf, e, "An error occured while sending file", JOptionPane.ERROR_MESSAGE);
        }
    }
}
