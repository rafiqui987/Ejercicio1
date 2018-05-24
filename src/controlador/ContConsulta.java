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
       
        
        if (ae.getSource().equals(this.vista.jButtonSelect)) {
            List<Reserva> ReservaTodos = modelo.ReservareadAll();
        Reserva Reservaall;
        int filas = tablaconsulta.getRowCount();

        for (int i = 0; filas > i; i++) {

            tablaconsulta.removeRow(0);

        }

        Iterator<Reserva> ReservaIterator = ReservaTodos.iterator();

        while (ReservaIterator.hasNext()) {

            Reservaall = ReservaIterator.next();

            Object rowData[] = {Reservaall.getId_reserva(),
                                Reservaall.getCliente().getIdentificacion(), 
                                Reservaall.getCliente().getNombre(),
                                Reservaall.getAutomovil().getPlaca(), 
                                Reservaall.getAutomovil().getPreciodia(),
                                Reservaall.getFecha_inicio(), 
                                Reservaall.getFecha_final(),
                                Reservaall.getCosto(), 
                                Reservaall.getIva(),
                                Reservaall.getCosto_final(),
                                Reservaall.getEstado(),};

           tablaconsulta.addRow(rowData);

        }
            
            
            
        }
        
        
        if (ae.getSource().equals(this.vista.jButtonExit)) {

            this.vista.dispose();
            ContPrincipal principal = new ContPrincipal(new VistaPrincipal());

        }
        
        
        
    }
    
    
    
    
}
