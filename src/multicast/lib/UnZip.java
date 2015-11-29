/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.lib;
import java.io.*;
import java.util.zip.*;

public class UnZip extends Thread{
   private BufferedOutputStream dest;
   private FileInputStream fis;
   private ZipInputStream zis;  
   private static final int BUFFER = 4096;
   private String fichero;

   public UnZip(String fichero) {
       this.fichero = fichero;
   }
        
   @Override
   public void run() {
      int count;
      byte data[];
      
      try {
         dest = null;
         fis = new FileInputStream(fichero);
         zis = new ZipInputStream(new BufferedInputStream(fis));
         ZipEntry entry;
         
         while((entry = zis.getNextEntry()) != null) {                       
            data = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(entry.getName());
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = zis.read(data, 0, BUFFER))               != -1) {
               dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
            System.out.println("Fichero descomprimido: " +entry); 
         }
         zis.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }}

