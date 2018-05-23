/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.*;
import modelo.vo.*;
import vista.*;

public class ContConsulta implements ActionListener{
    
    
    private VistaConsulta vista;
    private Reserva_dao modelo;
    private DefaultTableModel tablaconsulta;
    
    
    public ContConsulta(VistaConsulta vista, Reserva_dao modelo) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableReserva.getModel();
        
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
