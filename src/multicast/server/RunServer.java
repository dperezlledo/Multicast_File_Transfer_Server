/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import multicast.lib.*;


public class RunServer {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException {

        FileOutputStream fileOutput;
        BufferedOutputStream bufferedOutput;
        DatagramPacket msgPacket;
        int indice=1;
        Thread unzip;
        String nombreArchivo;
                
        try {
            InetAddress address = InetAddress.getByName(INET_ADDR);
            byte[] datos = new byte[4096];
            // Se abre el fichero donde se hará la copia
            nombreArchivo = "File" + indice + ".zip";
            fileOutput = new FileOutputStream(nombreArchivo);
            bufferedOutput = new BufferedOutputStream(fileOutput);
            System.out.println("Esperando recibir datos del cliente...");
            try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
                clientSocket.joinGroup(address);
                do {
                    msgPacket = new DatagramPacket(datos, datos.length);
                    clientSocket.receive(msgPacket);
                    if (bufferedOutput==null) { // Otro fichero
                        nombreArchivo = "File" + (++indice) + ".zip";
                        fileOutput = new FileOutputStream(nombreArchivo);
                        bufferedOutput = new BufferedOutputStream(fileOutput);
                    }
                    if (msgPacket.getData()[0]==-1) { // Fin del fichero
                        bufferedOutput.close();
                        fileOutput = null; bufferedOutput = null;
                        nombreArchivo = "\nFile" + indice + ".zip Recibido";
                        System.out.println(nombreArchivo);
                        // Descomprimir archivo
                        unzip = new UnZip("File" + indice + ".zip");
                        unzip.start();
                    } else {
                        System.out.print(".");
                        bufferedOutput.write(msgPacket.getData(), 0, msgPacket.getData().length);
                    }
                } while (true);       
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
   
}