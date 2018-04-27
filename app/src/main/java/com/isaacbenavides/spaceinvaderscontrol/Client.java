package com.isaacbenavides.spaceinvaderscontrol;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Client extends AsyncTask<String, Void, Void> {
    private Socket socket;
    private PrintWriter writter;
    private DataInputStream din;
    private static String received;

    /**
     * Manda un mensaje al servidor.
     * @param voids Argumento por override.
     * @return
     */
    @Override
    protected Void doInBackground(String... voids) {
        String message = voids[0];

        try{
            //172.18.195.176 tec
            //192.168.100.5 casa 192.168.100.6
            //172.18.42.103 biblioteca
            socket = new Socket("172.18.17.63", 8000);

            //Manda mensaje al cliente.
            writter = new PrintWriter(socket.getOutputStream(), true);
            writter.write(message);

            //Lee el mensaje del cliente.
            din = new DataInputStream(socket.getInputStream());
            received = din.readUTF();

            writter.close();
            socket.close();

        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene el mensaje que fue enviado del servidor.
     * @return El mensaje del servidor.
     */
    public String getReceived(){
        return received;
    }

}




















