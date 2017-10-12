package ChatApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChatApplication.ChatClient;

public class GraphicalChat extends JFrame {
    
    private static final long serialVersionUID = 1L;
    protected JTextArea chatArea = null;
    protected JTextArea chatInput = null;
    protected JTextField ipInputText = null;
    protected JButton inputSend = null;
    protected GridBagConstraints gbc1 = new GridBagConstraints();
    protected Font hv = new Font("Helvetica", Font.PLAIN, 14);
    protected Font co = new Font("Courier", Font.ITALIC, 12);
    
    protected boolean clientConnected = false;
    ChatClient client = null;
    
    
    public GraphicalChat(String textToDisplay) { 
        showIPAddressInput();            
    }

    private void initUI() {
        setTitle("CS3230 Graphical Chat - Jonathan Mirabile");
        setLayout(new GridBagLayout());
        setSize(400,515);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    
    private void showIPAddressInput() {
        JFrame inputBox = new JFrame();
        inputBox.setTitle("CS3230 Graphical Chat - Jonathan Mirabile");
        inputBox.setLayout(new FlowLayout());
        inputBox.setSize(400, 100);
        inputBox.setLocationRelativeTo(null);
        inputBox.setDefaultCloseOperation(EXIT_ON_CLOSE);
        inputBox.setVisible(true);
               
        ipInputText = new JTextField("Enter the IP Address of the server: ");
        ipInputText.setEditable(false);
        
        JTextField ipInput = new JTextField(10);
        JButton submit = new JButton("Submit");
        
        submit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //Pass the value from the ipInput box to the ChatClient and test the connection
                if(ipInput.getText() != "") {
                     client = new ChatClient(ipInput.getText());
                    if(client.connected == true) {
                        //If the connection is good, hide the window and open the chat box
                        inputBox.dispose();
                        initUI();
                        showTextArea();
                        addScrollBar();
                        showChatInput();
                        showInputButton();
                    }
                    else {
                        ipInput.setText("");
                    }
                }
            }
        });
              
        inputBox.add(ipInputText);
        inputBox.add(ipInput);
        inputBox.add(submit);
            
    }
    
    private void showTextArea() {
        chatArea = new JTextArea(null, 10, 25);
        chatArea.setFont(hv);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 6;
        gbc1.gridheight = 7;
        gbc1.weightx = 1;
        gbc1.weighty = 1;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        
        add(chatArea, gbc1);
                
    }
    
    private void addScrollBar() {
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 1;
        gbc1.gridheight = 1;
        gbc1.weightx = 1;
        gbc1.weighty = 1;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        
        add(scroll, gbc1);
        
    }
    
    private void showChatInput() {
        chatInput = new JTextArea("Enter chat text here", 15, 10);
        chatInput.setFont(co);
        chatInput.setLineWrap(true);
        chatInput.setWrapStyleWord(true);
        chatInput.setEditable(true);
        
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.gridwidth = 6;
        gbc1.gridheight = 1;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        
        add(chatInput, gbc1);
        
        chatInput.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(chatInput.getText().trim().equals("Enter chat text here")) {
                    chatInput.setText("");
                }
            }
        });
        
        chatInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == KeyEvent.CTRL_MASK)) {
                    //Send the message to the chat server
                    client.sendTextToServer(chatInput.getText());
                    //Clear the input box
                    chatInput.setText("");
                    //Scroll to the bottom of the chat box
                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
                }
            }
        });
                      
    }
    
    protected void showInputButton() {
        inputSend = new JButton("Send");
        
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 6;
        gbc1.gridheight = 3;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        add(inputSend, gbc1);
        
        inputSend.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //Send the message to the chat server
                client.sendTextToServer(chatInput.getText());
                
                //Clear the input box
                chatInput.setText("");
                //Scroll to the bottom of the chat box
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            }
        });
             
    }
       
}
