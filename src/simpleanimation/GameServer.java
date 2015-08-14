/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleanimation;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer {

    ArrayList playerOutStreams;
    ArrayList<Player> players;
    Player p1;
    Player p2;
    Color p1Color = Color.red;
    Color p2Color = Color.blue;
    int playerCount = 0;

    public class ClientHandler implements Runnable {

        Socket sock;
        InputStream inStream;
        ObjectInputStream objInStream;
        BufferedReader reader;

        public ClientHandler(Socket clientSocket) {

            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void run() {
            int key;
            String message;
            try {
                
                while ((message = reader.readLine()) != null) {
                    key = Integer.parseInt(message);
                    
                    if (key == KeyEvent.VK_UP) {
                        System.out.println("up");
                        p1.movePlayerUp(0);
                    }
                    if (key == KeyEvent.VK_DOWN) {
                        System.out.println("down");
                        p1.movePlayerDown(300);
                    }
                    if (key == KeyEvent.VK_LEFT) {
                        System.out.println("left");
                        p1.movePlayerLeft(0);
                    }
                    if (key == KeyEvent.VK_RIGHT) {
                        System.out.println("right");
                        p1.movePlayerRight(300);
                        
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        System.out.println("fire!");
                    }
                    System.out.println(p1.getX() + ", " + p1.getY());
                    updatePlayerLocations(p1.getX(), p1.getY());
                }
                

                

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GameServer().go();
    }

    public void go() {
        playerOutStreams = new ArrayList();
        players = new ArrayList<>();
        
        try {
            ServerSocket serverSocket = new ServerSocket(3074);
            while (true) {

                
                Socket playerSocket = serverSocket.accept();
                InputStream inStream = playerSocket.getInputStream();
                ObjectInputStream objInStream = new ObjectInputStream(inStream);
                PrintWriter writer = new PrintWriter(playerSocket.getOutputStream());
                playerOutStreams.add(writer);
 
                if(playerCount == 0){
                    p1 = new Player(100,100, p1Color);
                    players.add(p1);
                    playerCount++;
                    
                }
                System.out.println("creating player " + (playerCount));
                writer.println("You are player " + playerCount);
                writer.flush();
                Thread t = new Thread(new ClientHandler((playerSocket)));
                t.start();
                
                

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void updatePlayerLocations(int x, int y) {

       Iterator it = playerOutStreams.iterator();
       
       while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(p1.getX() + "," + p1.getY());
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    
    }
}
