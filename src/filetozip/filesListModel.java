/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filetozip;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Antonenko Andrey
 */
public class filesListModel extends DefaultListModel<FileItem>{
    
    private List<FileItem> files;
    private ListDataListener listener;  
      
}
