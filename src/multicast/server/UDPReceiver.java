/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xeustechnologies.jtar.*;

/**
 *
 * @author dapelle
 */
public class UDPReceiver extends Thread {
     
    
    @Override
    public void run() {

        String salida = null;        
        String comando = "cmd /c commands\\udp-receiver.exe -f tmp.tar --exit-wait 1000";
        //String comando = "udp-receiver -f tmp.tar --exit-wait 1000";

        try {
            while (true) {
                // Ejecucion Basica del Comando
                Process proceso = Runtime.getRuntime().exec(comando);

                InputStreamReader entrada = new InputStreamReader(proceso.getInputStream());
                BufferedReader stdInput = new BufferedReader(entrada);

                //Si el comando tiene una salida la mostramos
                if ((salida = stdInput.readLine()) != null) {
                    System.out.println("Comando ejecutado Correctamente");
                    while ((salida = stdInput.readLine()) != null) {
                        System.out.println(salida);
                    }
                } else {
                    // Descomprimimos archivo
                    sleep(5000);
                    unTar("tmp.tar", "");
                    sleep(3000);
                    System.out.println("Esperando siguiente archivo...");
                }
            }
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void unTar(String tarFile, String destFolder) {
        try {
            String salida = null;
            String comando = "cmd /c commands\\tar.exe -xf tmp.tar --directory=Multicast";
            //String comando = "tar -xf tmp.tar";
            Process proceso = Runtime.getRuntime().exec(comando);
            
            InputStreamReader entrada = new InputStreamReader(proceso.getInputStream());
            BufferedReader stdInput = new BufferedReader(entrada);
            
            //Si el comando tiene una salida la mostramos
            if ((salida = stdInput.readLine()) != null) {
                System.out.println("Comando ejecutado Correctamente");
                while ((salida = stdInput.readLine()) != null) {
                    System.out.println(salida);
                }
            } else {
                // Descomprimimos archivo                
                System.out.println("Fichero desempaquetado correctamente");
            }
        } catch (IOException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
