/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.lib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

/**
 *
 * @author Departamento
 */
public class UnTar {
     public static void unTarFile(File tarFile, File dest) throws IOException {
        dest.mkdir();
        TarInputStream tarIn = null;

        tarIn = new TarInputStream(                
                        new BufferedInputStream(
                                new FileInputStream(
                                        tarFile
                                )
                        )                
        );

        TarEntry tarEntry = tarIn.getNextEntry();
        // tarIn is a TarArchiveInputStream
        while (tarEntry != null) {// create a file with the same name as the tarEntry
            File destPath = new File(dest, tarEntry.getName());
            System.out.println("working: " + destPath.getCanonicalPath());
            if (tarEntry.isDirectory()) {
                destPath.mkdirs();
            } else {
                destPath.createNewFile();
                //byte [] btoRead = new byte[(int)tarEntry.getSize()];
                byte[] btoRead = new byte[1024];
            //FileInputStream fin 
                //  = new FileInputStream(destPath.getCanonicalPath());
                BufferedOutputStream bout
                        = new BufferedOutputStream(new FileOutputStream(destPath));
                int len = 0;

                while ((len = tarIn.read(btoRead)) != -1) {
                    bout.write(btoRead, 0, len);
                }

                bout.close();
                btoRead = null;

            }
            tarEntry = tarIn.getNextEntry();
        }
        tarIn.close();
    }
    
}
