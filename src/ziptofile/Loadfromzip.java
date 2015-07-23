/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ziptofile;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Antonenko Andrey
 */
public class Loadfromzip {

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
        // TODO code application logic here

        /*ZipInputStream zin = new ZipInputStream(new FileInputStream("newzip.zip"));
         ZipEntry entry;
         System.out.println("Before while");
         while ((entry = zin.getNextEntry()) != null) {
         //анализ entry
         //считывание содежимого                        
            
         System.out.println("In while "+ entry.getName());
         zin.closeEntry();
         }
         zin.close();
         System.out.println("After while");*/
        Enumeration entries = null;
        ZipFile zip = null;

        String str = "passwd.txt";

        try {
            zip = new ZipFile("newzip.jbdx");
            entries = zip.entries();
            System.out.println("Before while");
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (str.equals(entry.getName().toString())) {
                    System.out.println("Extracting:" + entry.getName());
                    write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream("file.txt")));
                }
            }
            System.out.println("After while");
            zip.close();
        } catch (IOException e) {
            System.out.println("Exception:");
            e.printStackTrace();
            return;
        }
    }

    public void GetElementsFromZip() {
        Enumeration entries = null;
        ZipFile zip = null;

        String str = "passwd.txt";

        try {
            zip = new ZipFile("newzip.jbdx");
            entries = zip.entries();
            System.out.println("Before while");
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (str.equals(entry.getName().toString())) {
                    System.out.println("Extracting:" + entry.getName());
                    write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream("file.txt")));
                }
            }
            System.out.println("After while");
            zip.close();
        } catch (IOException e) {
            System.out.println("Exception:");
            e.printStackTrace();
            return;
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
