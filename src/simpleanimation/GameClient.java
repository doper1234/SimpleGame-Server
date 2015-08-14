/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleanimation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GameClient {

    Color p1Color = Color.red;
    Color p2Color = Color.blue;
    Player p1;
    Player p2;
    int frameWidth = 300, frameHeight = 300;
    int keyPressed;
    Socket socket;
    OutputStream outStream;
    ObjectOutputStream objOutStream;
    Thread readerThread;
    BufferedReader reader;
    PrintWriter writer;

    public static void main(String[] args) {
        GameClient gui = new GameClient();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(drawPanel);
        drawPanel.addKeyListener(new MyKeyListener());
        drawPanel.setFocusable(true);
        drawPanel.requestFocus();
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);

        setUpNetworking();
        readerThread = new Thread(new IncomingReader());
        readerThread.start();
        while (true) {
            drawPanel.repaint();
////            try{
////                Thread.sleep(50);
//            }catch(Exception ex){
//            }
        }
    }

    public class MyDrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {

            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.green);
            
            if(p1 != null){
            
                p1.drawPlayer(g);
            }
            
            //p2.drawPlayer(g);

        }

    }

    public void setUpNetworking() {
        try {
            socket = new Socket("localhost", 3074);
            outStream = socket.getOutputStream();

            objOutStream = new ObjectOutputStream(outStream);

            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("networking established");
//            writer.println(1);
//            writer.flush();
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            try {

                int keyPressed = ke.getKeyCode();
                writer.println(keyPressed);
                writer.flush();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }
    }
    
    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String message;
            try {
                

                while ((message = reader.readLine()) != null) {
                
                    System.out.println(message);
                    if(p1 != null){
                    
                        String[] result = reader.readLine().split(",");
                        p1.setX(Integer.parseInt(result[0]));
                        p1.setY(Integer.parseInt(result[1]));
                        System.out.println("the player should move");
                    }
                    else if(message.equalsIgnoreCase("you are player 1")){
                        p1 = new Player(100,100, p1Color);
                    }
                    
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
