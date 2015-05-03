
package Sharing;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author Kunal
 */
public class SendFileThread extends Thread {

    private Socket sock;
    private File filetoSend;
    private JButton CancelSend;
    private JProgressBar jpb;
    private JLabel fileName;
    private JFrame jf;
    private FileInputStream fis;
    private BufferedInputStream bis;
    private OutputStream os;

    public SendFileThread(Socket fSendSocket, File fileToSend) {
        this.sock = fSendSocket;
        this.filetoSend = fileToSend;
    }

    @Override
    public void run() {
        try {
            FileSendListener fileSendListener = new FileSendListener(this);
            CancelSend = new JButton("Cancel");
            CancelSend.addActionListener(fileSendListener);
            long size = filetoSend.length() / 1024;
            jpb = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int) size / 1024);
            fileName = new JLabel("File: " + filetoSend.getName());
            jf = new JFrame("Sending");

            jf.setSize(400, 200);
            jf.add(fileName, BorderLayout.NORTH);
            jf.add(jpb, BorderLayout.CENTER);
            jf.add(CancelSend, BorderLayout.SOUTH);
            jf.setVisible(true);
            int bytesread;
            byte[] mybytearray = new byte[1024];
            fis = new FileInputStream(filetoSend);
            bis = new BufferedInputStream(fis);
            os = sock.getOutputStream();
            long r = 0l;
            jpb.setStringPainted(true);
//            jpb.setValue(0);
            //int val = 0;
            while ((bytesread = bis.read(mybytearray, 0, mybytearray.length)) != -1) {
                r = r + bytesread;
                if (bytesread > 0) {
                    jpb.setString(" " + r / 1024  + " KB of " + size  + " KB sent");
//                    val = val + (int) r / 1024 / 1024;
                }
                jpb.setValue((int)r/1024/1024);
                os.write(mybytearray, 0, bytesread);
                
            }
            os.flush();
            os.close();
            sock.close();
            CancelSend.removeActionListener(fileSendListener);
            CancelSend.setText("Close");
            CancelSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jf.dispose();
                }
            });
        } catch (HeadlessException | IOException e) {
            jf.dispose();
            JOptionPane.showMessageDialog(jf, e, "Error occured while sending file", JOptionPane.ERROR_MESSAGE);
        }
    }
}
