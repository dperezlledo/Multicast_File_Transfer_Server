/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author dapelle
 */
public class MFTServer {
    public static void main(String[] args) {
         Thread udpReceiver = new UDPReceiver();
         udpReceiver.start();
    }
}
