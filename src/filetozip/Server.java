/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filetozip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static ziptofile.Loadfromzip.write;

/**
 *
 * @author Masster
 */
public class Server extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    private Properties prop;
    private FileInputStream fis;
    private FileOutputStream fos;
    private filesListModel flm;
    private File archFile;
    Enumeration entries = null;
    ZipFile zip = null;

    public void LoadFilesFromFolder(String FolderPath) {
        File srcDir = new File(FolderPath);
        File[] files = srcDir.listFiles();

        for (File itemFile : files) {
            if (!itemFile.getName().equals("pass.prop") && !itemFile.isDirectory()) {
                prop.setProperty(itemFile.getName(), "");
                flm.addElement(new FileItem(itemFile.getName(), itemFile.getName()));
            }
            if (itemFile.getName().equals("pass.prop")) {
                prop.clear();
            }
        }
        try {
            fos = new FileOutputStream(new File(srcDir, "pass.prop"));

            prop.store(fos, "Saved");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        archFile = srcDir;

        /*srcDir = new File(FolderPath);
         files = srcDir.listFiles();
        
         File zipFile = new File(srcDir.getParent(), srcDir.getName() + "_j7.zip");

         try {
         fos = new FileOutputStream(zipFile);
         ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("CP866"));

         for (File itemFile : files) {
         ZipEntry entry = new ZipEntry(itemFile.getName());
         zos.putNextEntry(entry);

         try (FileInputStream fis = new FileInputStream(itemFile)) {
         byte[] buff = new byte[2];
         int length = -1;
         while (-1 != (length = fis.read(buff))) {
         zos.write(buff, 0, length);
         }
         }
         zos.closeEntry();
         }
         zos.close();
         } catch (Exception e) {
         System.out.println("Exception " + e.getMessage());
         }*/
    }

    public void CriptArch() {
        try {
            FileInputStream fis = new FileInputStream(archFile.getParent() + "\\" + archFile.getName() + "_archiv.jbdx");
            FileOutputStream fos = new FileOutputStream(archFile.getParent() + "\\"+archFile.getName()+".jbdx");
            File toHide = new File(archFile.getParent() + "\\" + archFile.getName() + "_archiv.jbdx");
            
            byte[] buffer = new byte[16];
            int n = 0;
            while (-1 != (n = fis.read(buffer))) {
                buffer[2] = (byte) ~buffer[2];
                fos.write(buffer, 0, n);
            }
            fos.close();
            fis.close();         
                       
            /*fis = new FileInputStream("newzip1.jbdx");
            fos = new FileOutputStream("temp.jbdx");

            buffer = new byte[16];
            n = 0;
            while (-1 != (n = fis.read(buffer))) {
                buffer[2] = (byte) ~buffer[2];
                fos.write(buffer, 0, n);
            }
            fos.close();
            fis.close();*/
            toHide.delete();
        } catch (IOException ex) {
            Logger.getLogger(Savetozip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SaveToArchiv() {
        File[] files = archFile.listFiles();

        File zipFile = new File(archFile.getParent(), archFile.getName() + "_archiv.jbdx");
        try {
            Runtime.getRuntime().exec("attrib +H " + zipFile);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("CP866"));

            for (File itemFile : files) {
                ZipEntry entry = new ZipEntry(itemFile.getName());
                zos.putNextEntry(entry);

                try (FileInputStream fis = new FileInputStream(itemFile)) {
                    byte[] buff = new byte[16];
                    int length = -1;
                    while (-1 != (length = fis.read(buff))) {
                        zos.write(buff, 0, length);
                    }
                }
                zos.closeEntry();
            }
            zos.close();
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
        CriptArch();
    }

    public void LoadFilesFromZip(String filename) {
        try {
            File fl;
            zip = new ZipFile(filename);
            entries = zip.entries();
//            System.out.println("Before while");
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                fl = new File(entry.getName());
                if (fl.getParent() == null) {
                    System.out.println("Entry: " + entry.getName());
                    System.out.println(zip.getName());
                    System.out.println("File: " + fl.getName());
                    System.out.println("Parent: " + fl.getParent());
                }
                /*fl = new File(entry.getName());
                 System.out.println("file: " + fl.getName());
                 System.out.println("Hidden: " + fl.isHidden());
                 System.out.println("Folder: " + entry.isDirectory());
                 if (!entry.isDirectory()&&!fl.isHidden()) {
                 System.out.println("Extracting:" + entry.getName());
                 write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(entry.getName())));
                 }*/
            }
            //System.out.println("After while");
            zip.close();
        } catch (IOException e) {
            System.out.println("Exception:");
            e.printStackTrace();
            return;
        }

    }

    public Server() {
        initComponents();
        flm = new filesListModel();
        filesList.setModel(flm);

        prop = new Properties();        
        //prop.propertyNames()
        /* for (int i = 0; i < 10; i++) {
         flm.addElement(new FileItem("newzip" + i, ""));
         prop.setProperty("newzip" + i, (i + 10020) + "");
         }
         try {
         fos = new FileOutputStream("pass.prop");

         } catch (FileNotFoundException ex) {
         Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prop.store(fos, "Saved");
         fos.close();
         } catch (IOException ex) {
         Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        filesList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passw = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        mainPass = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Шифрование данных");
        setResizable(false);

        filesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                filesListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(filesList);

        jLabel1.setText("Список хранимых файлов");

        jLabel2.setText("Пароль выбранному");

        jButton1.setText("№2 Задать пароль хранимому файлу");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("№1 Выбрать архив");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("№3 Запаковать архив");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        mainPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainPassActionPerformed(evt);
            }
        });

        jLabel3.setText("Основной пароль");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passw, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mainPass, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filesListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filesListMousePressed
        // TODO add your handling code here:
        int ind = filesList.getSelectedIndex();
        if (ind > -1) {
            String str = filesList.getModel().getElementAt(ind).toString();
            try {
                System.out.println("File: " + archFile.getPath());
                fis = new FileInputStream(archFile.getPath() + "\\pass.prop");
                prop.load(fis);
                String passwd = prop.getProperty(str);
                passw.setText(passwd);
                System.out.println("File: " + str + ", Passwd: " + passwd);
            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }
            passw.requestFocus();
        }
    }//GEN-LAST:event_filesListMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int ind = filesList.getSelectedIndex();
        if (ind > -1) {
            String str = filesList.getModel().getElementAt(ind).toString();
            prop.setProperty(str, passw.getText());

            try {
                fos = new FileOutputStream(archFile.getPath() + "\\pass.prop");                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                prop.store(fos, "Saved");
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JFileChooser fileopen = new JFileChooser();
        fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fileopen.setFileFilter(new FileNameExtensionFilter("Архивы *.zip", "zip"));
        int ret = fileopen.showDialog(null, "Выбрать архив");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());
            flm.clear();
            LoadFilesFromFolder(file.getPath());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (flm.size() > 0) {
            SaveToArchiv();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void mainPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mainPassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList filesList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mainPass;
    private javax.swing.JTextField passw;
    // End of variables declaration//GEN-END:variables
}
