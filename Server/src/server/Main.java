package server;

import Methods.Methods;
import java.text.ParseException;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/*******************
 * @author Lalwani *
 *******************/
public class Main extends javax.swing.JFrame {
   
    public Main() {
        initComponents();
    }
    
    ArrayList clientOutputStreams;
    ArrayList<String> users;
    
    public class ServerStart implements Runnable {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try
            {
                ServerSocket serverSock = new ServerSocket(Integer.parseInt(PortTextBox.getText()));
                ServerStatus.setForeground(Color.GREEN);
                ServerStatus.setText("Server Online");
                Logs.append("Server Started\n");

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				Logs.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                Logs.append("Error making a connection. \n");
            }
        }
    }
    
       public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (IOException ex) 
            {
                Logs.append("Unexpected error... \n");
            }

       }
       
        public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        Logs.append("Before " + name + " added. \n");
        users.add(name);
        Logs.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
       
       public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		Logs.append("Sending: " + message + "\n");
                writer.flush();
                Logs.setCaretPosition(Logs.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		Logs.append("Error telling everyone. \n");
            }
        } 
    }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    Logs.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        Logs.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        tellEveryone(message);
                    } 
                    else 
                    {
                        Logs.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (IOException ex) 
             {
                Logs.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StartButton = new javax.swing.JButton();
        StopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Logs = new javax.swing.JTextArea();
        ServerStatus = new javax.swing.JLabel();
        PortTextBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        StartButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        StartButton.setText("Start");
        StartButton.setMinimumSize(new java.awt.Dimension(70, 40));
        StartButton.setPreferredSize(new java.awt.Dimension(70, 40));
        StartButton.setRequestFocusEnabled(false);
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

        StopButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        StopButton.setText("Stop");
        StopButton.setPreferredSize(new java.awt.Dimension(70, 40));
        StopButton.setRequestFocusEnabled(false);
        StopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopButtonActionPerformed(evt);
            }
        });

        Logs.setColumns(20);
        Logs.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        Logs.setRows(5);
        jScrollPane1.setViewportView(Logs);

        ServerStatus.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        ServerStatus.setForeground(new java.awt.Color(255, 0, 0));
        ServerStatus.setText("Server Offline");
        ServerStatus.setPreferredSize(new java.awt.Dimension(41, 40));

        PortTextBox.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        PortTextBox.setForeground(new java.awt.Color(204, 204, 204));
        PortTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PortTextBox.setText("Port");
        PortTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PortTextBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PortTextBoxFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(StartButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(StopButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PortTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ServerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(StartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(StopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ServerStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PortTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void StopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopButtonActionPerformed
        try 
        {
            Thread.sleep(500);
            ServerStatus.setForeground(Color.RED);
            ServerStatus.setText("Server Offline");
            Logs.append("Server Stopped\n");
        } 
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }//GEN-LAST:event_StopButtonActionPerformed

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartButtonActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
    }//GEN-LAST:event_StartButtonActionPerformed

    private void PortTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PortTextBoxFocusGained
        Methods.textFieldFocus(PortTextBox,"Port");
    }//GEN-LAST:event_PortTextBoxFocusGained

    private void PortTextBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PortTextBoxFocusLost
        Methods.textFieldFocusLost(PortTextBox,"Port");
    }//GEN-LAST:event_PortTextBoxFocusLost

    public static void main(String args[]) {
        
        try{
              UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
          }
       catch (ParseException | UnsupportedLookAndFeelException ex){
              JOptionPane.showMessageDialog(null,ex.getLocalizedMessage());
           }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Logs;
    private javax.swing.JTextField PortTextBox;
    private javax.swing.JLabel ServerStatus;
    private javax.swing.JButton StartButton;
    private javax.swing.JButton StopButton;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
