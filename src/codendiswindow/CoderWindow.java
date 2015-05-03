
package codendiswindow;

import Register.IPRegister;
import Sharing.ReceiveFileThread;
import Sharing.SendFileThread;
import Styles.CodeDocStyle;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Kunal
 */
public class CoderWindow extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9217005922358869394L;
	private JPanel talkerPanel, userPanel, contentPanel, myPanel, remotePanel;
    private JMenuBar talkerMenuBar;
    private JMenu fileMenu;
    private JMenuItem editorItem, sendFileItem, exitItem;
    private JLabel myLabel, friendLabel;
    private JTextPane historyPane, inputPane;
    private JScrollPane historyScrollPane, inputScrollPane;
    private String friendName;
    private GridBagConstraints userPanelCons, contentPanelCons, myPanelCons, remotePanelCons, historyPaneCons, inputPaneCons;
    private final Socket talkSocket;
    private StyledDocument doc;
    private String historyUpdater;
    private DataOutputStream dos = null;
    private File fileToSend = null;
    private String[] recievingFileDetails = new String[3];

    public CoderWindow(String friendName, Socket socket) {
        this.friendName = friendName;
        this.talkSocket = socket;
        historyUpdater = "";
      //System.out.println("Hello");
        try {
            this.dos = new DataOutputStream(talkSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CoderWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    public void startTalker() {
        initializeComponents();
        initializeConstraints();
        addComponents();
        attatchHandler();
        this.setSize(350, 350);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeComponents() {
        // panels
        talkerPanel = new JPanel();
        userPanel = new JPanel();
        contentPanel = new JPanel();
        myPanel = new JPanel();
        remotePanel = new JPanel();

        // menubar
        talkerMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editorItem=new JMenuItem("Editor");
        sendFileItem = new JMenuItem("Send and Compile");
        exitItem = new JMenuItem("Exit");

        historyPane = new JTextPane();
        historyPane.setEditable(false);
        inputPane = new JTextPane();

        myLabel = new JLabel("I Coder");
        friendLabel = new JLabel("Friend Coder");

    }

    private void initializeConstraints() {
        /*try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
         System.out.println(e);
         }*/
        talkerPanel.setLayout(new GridBagLayout());
        userPanel.setLayout(new GridBagLayout());
        contentPanel.setLayout(new GridBagLayout());

        //cons for user panel
        userPanelCons = new GridBagConstraints();
        userPanelCons.gridx = 0;
        userPanelCons.gridy = 0;
        userPanelCons.gridwidth = 1;
        userPanelCons.gridheight = 1;

        // cons for content panel
        contentPanelCons = new GridBagConstraints();
        contentPanelCons.gridx = 0;
        contentPanelCons.gridy = 1;
        contentPanelCons.gridwidth = 1;
        contentPanelCons.gridheight = 1;

        // cons for mypanel
        myPanelCons = new GridBagConstraints();
        myPanelCons.gridx = 0;
        myPanelCons.gridy = 0;
        myPanelCons.gridwidth = 1;
        myPanelCons.gridheight = 1;

        // cons for remotepanel
        remotePanelCons = new GridBagConstraints();
        remotePanelCons.gridx = 1;
        remotePanelCons.gridy = 0;
        remotePanelCons.gridwidth = 1;
        remotePanelCons.gridheight = 1;

        // cons for history pane
        historyPaneCons = new GridBagConstraints();
        historyPaneCons.gridx = 0;
        historyPaneCons.gridy = 0;
        historyPaneCons.gridwidth = 1;
        historyPaneCons.gridheight = 1;
        historyPaneCons.ipadx = 300;
        historyPaneCons.ipady = 150;

        // cons for history pane
        inputPaneCons = new GridBagConstraints();
        inputPaneCons.gridx = 0;
        inputPaneCons.gridy = 1;
        inputPaneCons.gridwidth = 1;
        inputPaneCons.gridheight = 1;
        inputPaneCons.ipadx = 300;
        inputPaneCons.ipady = 50;

        // styled doc
        doc = historyPane.getStyledDocument();
        CodeDocStyle.addStylesToDocument(doc);
    }

    private void addComponents() {
        this.setJMenuBar(talkerMenuBar);
        this.add(talkerPanel);

        talkerMenuBar.add(fileMenu);
        fileMenu.add(editorItem);
        fileMenu.add(sendFileItem);
        fileMenu.add(exitItem);

        userPanel.add(myPanel, myPanelCons);
        userPanel.add(remotePanel, remotePanelCons);
        myPanel.add(myLabel);
        remotePanel.add(friendLabel);

        talkerPanel.add(userPanel, userPanelCons);
        talkerPanel.add(contentPanel, contentPanelCons);

        historyScrollPane = new JScrollPane(historyPane);
        historyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        historyScrollPane.getViewport().scrollRectToVisible(historyPane.getBounds());
        historyScrollPane.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black)));
        contentPanel.add(historyScrollPane, historyPaneCons);

        inputScrollPane = new JScrollPane(inputPane);
        inputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inputScrollPane.getViewport().scrollRectToVisible(inputPane.getBounds());
        inputScrollPane.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black)));
        contentPanel.add(inputScrollPane, inputPaneCons);
    }

    private void attatchHandler() {
        TalkerHandler talkHandler = new TalkerHandler();
        this.sendFileItem.addActionListener(talkHandler);
        this.editorItem.addActionListener(talkHandler);
        this.exitItem.addActionListener(talkHandler);
        //this.attatchHandler();
        SendMessageKeyListener smkl = new SendMessageKeyListener();
        this.inputPane.addKeyListener(smkl);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    try {
                        sendMessage("CLOSING_TALK##$$");
                        frame.dispose();
                    } catch (Exception ex) {
                        frame.dispose();
                      //System.out.println("Hello CNS");
                        try {
                            talkSocket.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(CoderWindow.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                }
            }
        });
    }

    public void setFriendName(String friendName) {
        this.setTitle(friendName);
    }

    public void updateHistory(String updater, String recievedMessage, int msgType) throws BadLocationException {
        switch (msgType) {
            case 0:
                if (!historyUpdater.equals(updater)) {
                    historyUpdater = updater;

                } else {
                    updater = "...";
                }
                doc.insertString(doc.getLength(), "\n" + updater + ": ", doc.getStyle("infoMsg"));
                doc.insertString(doc.getLength(), recievedMessage, doc.getStyle("regular"));
                break;
            case 1:
                doc.insertString(doc.getLength(), "\n Info: " + recievedMessage, doc.getStyle("infoMsg"));
                break;
        }
    }

    public void notifyForRequest(String recievedMessage, String fname, String add, String size) throws IOException {
        //
        switch (recievedMessage) {
            case "REQUEST_TO_SEND_FILE":
                // show the message to the user
                System.out.println(fname);
                recievingFileDetails[0] = fname;
                recievingFileDetails[1] = add;
                recievingFileDetails[2] = size;
                int accept = JOptionPane.showConfirmDialog(myPanel, "Save File ?");
                if (accept == JOptionPane.YES_OPTION) {
                    // recieve file
                    try {
                        JFileChooser saveDialog = new JFileChooser();
                        saveDialog.setSelectedFile(new File(fname));
                        int result = saveDialog.showSaveDialog(saveDialog);
                        ServerSocket fsSocket;
                        Socket fSocket;
                        switch (result) {
                            case JFileChooser.APPROVE_OPTION: {
                                String saveDirectory = saveDialog.getCurrentDirectory().getAbsolutePath();
                                fsSocket = new ServerSocket(4646);
                                sendMessage("CLEAR_TO_SEND_FILE##$$");
                                fSocket = fsSocket.accept();
                                ReceiveFileThread rfThread = new ReceiveFileThread(fSocket, saveDirectory, fname, recievingFileDetails[2]);
                                rfThread.start();
                                fsSocket.close();
                                break;
                            }
                            case JFileChooser.CANCEL_OPTION:
                                break;
                            case JFileChooser.ERROR_OPTION:
                                break;
                        }
                    } catch (HeadlessException | IOException e) {
                        System.out.println("Error in TalkerWindow while sending CLEAR_TO_SEND_FILE" + e);
                    }
                }
                break;
            case "CLEAR_TO_SEND_FILE":
                File FTS = fileToSend;
                Socket fSendSocket;
                try {
                    fSendSocket = new Socket(talkSocket.getInetAddress(), 4646);
                    SendFileThread sfThread = new SendFileThread(fSendSocket, FTS);
                    sfThread.start();
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Either the specified IP Address is offline or the host is not in your network:\n " + E, " \n Host Not Found ", JOptionPane.WARNING_MESSAGE);
                } finally {
                    fileToSend = null;
                }
                break;
            case "CLOSING_TALK":
                IPRegister.removeIP(talkSocket.getInetAddress().toString().substring(1));
                dos.close();
        }
    }

    private void sendMessage(String sentMessage) {
        try {
            if ((sentMessage != null || !"\n".equals(sentMessage)) && !talkSocket.isClosed()) {
                dos.writeUTF(sentMessage);
            }
        } catch (IOException ex) {
            Logger.getLogger(CoderWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class TalkerHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object eventSource = e.getSource();
            if (eventSource == exitItem) {
                System.exit(1);
            }
            else if(eventSource==editorItem)
            {
            	new Editor();
            	
            }
            else if (eventSource == sendFileItem) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(fileChooser);
                switch (result) {
                    case JFileChooser.APPROVE_OPTION: {
                        File fileToSend = fileChooser.getSelectedFile();
                        addFileToSend(fileToSend);
                        sendMessage("REQUEST_TO_SEND_FILE" + "##" + fileToSend.getName() + "$$" + "$_$" + talkSocket.getLocalAddress() + "##" + fileToSend.length() + "$$");
                    }
                    case JFileChooser.CANCEL_OPTION: {
                        break;
                    }
                    case JFileChooser.ERROR_OPTION: {
                        break;
                    }
                }
            } else if (eventSource == sendFileItem) {
                //File fts = fileToSend;
                Socket fSendSocket;
                try {
                    fSendSocket = new Socket(talkSocket.getInetAddress(), 10002);

                    SendFileThread sfThread = new SendFileThread(fSendSocket, fileToSend);
                    sfThread.start();
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Either the specified IP Address is offline or the host is not in your network:\n " + E, " \nHost Not Found ", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private class SendMessageKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    String input = inputPane.getText();
                    sendMessage("##" + input + "$$");
                    updateHistory("Me", input, 0);
                    inputPane.setText("");
                } catch (Exception ea) {
                }
            } else if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                inputPane.setText(inputPane.getText() + "\n");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}