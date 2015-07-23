/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filetozip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Masster
 */
public class Savetozip {

    /**
     * @param args the command line arguments
     */
    public static void write(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {

        //File toHide = new File("newzip");
        //Runtime.getRuntime().exec("attrib +H " + toHide);
        FileInputStream fis;// = new FileInputStream("newzip.jbdx");
        FileOutputStream fos;// = new FileOutputStream("newzip1.jbdx");
        byte[] buffer = new byte[16];
        int n = 0;
        /*while (-1 != (n = fis.read(buffer))) 
         {
         System.out.println("Before "+buffer[2]);
         buffer[2] = (byte)~buffer[2];
         System.out.println("After "+buffer[2]);
         fos.write(buffer, 0, n);
         }
         fos.close();
         fis.close();*/
        File toHide = new File("temp.jbdx");
        Runtime.getRuntime().exec("attrib +H " + toHide);
        fis = new FileInputStream("newzip1.jbdx");
        fos = new FileOutputStream("temp.jbdx");
        buffer = new byte[16];
        n = 0;
        while (-1 != (n = fis.read(buffer))) {
            buffer[2] = (byte) ~buffer[2];
            fos.write(buffer, 0, n);
        }
        fos.close();
        fis.close();
        toHide.delete();
    }

    public void CriptArch() {
        FileInputStream fis;// = new FileInputStream("newzip.jbdx");
        FileOutputStream fos;// = new FileOutputStream("newzip1.jbdx");
        byte[] buffer = new byte[16];
        int n = 0;        
        File toHide = new File("temp.jbdx");
        try {
            Runtime.getRuntime().exec("attrib +H " + toHide);
            fis = new FileInputStream("newzip1.jbdx");
            fos = new FileOutputStream("temp.jbdx");

            buffer = new byte[16];
            n = 0;
            while (-1 != (n = fis.read(buffer))) {
                buffer[2] = (byte) ~buffer[2];
                fos.write(buffer, 0, n);
            }
            fos.close();
            fis.close();
            toHide.delete();
        } catch (IOException ex) {
            Logger.getLogger(Savetozip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

}
