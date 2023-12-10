package tp09_1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Socket s;
    private ArrayList<Socket> players;
    private String playerName;
    private int magicNumber;

    public ClientHandler(Socket s, ArrayList<Socket> players, int magicNumber) {
        this.s = s;
        this.players = players;
        this.magicNumber = magicNumber;
    }
    private void broadcast() {
        players.forEach(socket -> {
            if(s != socket){
                try {
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    PrintWriter pw = new PrintWriter(osw,true);
                    pw.println(playerName+" Win.");
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void run() {
        try {
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            PrintWriter pw = new PrintWriter(osw,true);
            pw.println("Enter your name : ");
            playerName = br.readLine();
            int guessedNumber;
            while (true){
                pw.println("guess number : ");
                guessedNumber = Integer.parseInt(br.readLine());
                if(guessedNumber > magicNumber)
                    pw.println("lower ");
                else if(guessedNumber < magicNumber)
                    pw.println("higher ");
                else {
                    pw.println("congratulations, you found the number !");
                    broadcast();
                    s.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
