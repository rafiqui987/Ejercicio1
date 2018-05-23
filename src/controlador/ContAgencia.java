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
import modelo.dao.Agencia_dao;
import modelo.vo.*;
import vista.VistaAgencia;
import vista.VistaPrincipal;

public class ContAgencia implements ActionListener {

    private VistaAgencia vista;
    private Agencia_dao modelo;
    private DefaultTableModel tablaconsulta;

    public ContAgencia(VistaAgencia vista, Agencia_dao modelo) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonUpdate.addActionListener(this);
        this.vista.jButtonCreate.addActionListener(this);
        this.vista.jButtonDelete.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableAgencia.getModel();
        tabla();
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.vista.jButtonSelect)) {

            if (vista.jTextFieldNombre.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Nombre no puede estar vacio");

            } else {
                Agencia agencia = new Agencia();
                String nombre = this.vista.jTextFieldNombre.getText();
                agencia = modelo.read(nombre);
                if (agencia.getNombre() == null) {
                    JOptionPane.showMessageDialog(null, "la agencia no existe");

                } else {
                    this.vista.jTextFieldDireccion.setText(agencia.getDireccion());
                    this.vista.jTextFieldTelefono.setText(String.valueOf(agencia.getTelefono()));
                 
                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldNombre.getText().equals("")||vista.jTextFieldDireccion.getText().equals("")||vista.jTextFieldTelefono.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {

                int rep = JOptionPane.showConfirmDialog(null, "Esta seguro de crear el contacto", "Aviso", JOptionPane.YES_NO_OPTION);

                if (rep == 0) {

                    Agencia agenciaCreate = new Agencia();
                    agenciaCreate.setNombre(vista.jTextFieldNombre.getText());
                    agenciaCreate.setDireccion(vista.jTextFieldDireccion.getText());
                    agenciaCreate.setTelefono(Integer.parseInt(vista.jTextFieldTelefono.getText()));

                    modelo.creat(agenciaCreate);
                    blancosCampos();
                    tabla();
                } else {

                }

            }
        }

        if (ae.getSource().equals(this.vista.jButtonUpdate)) {
            
            Agencia agencia = new Agencia();
            agencia.setNombre(vista.jTextFieldNombre.getText());
            agencia.setDireccion(vista.jTextFieldDireccion.getText());
            agencia.setTelefono(Integer.parseInt(vista.jTextFieldTelefono.getText()));
            
            modelo.update(agencia);
            blancosCampos();
            tabla();

        }

        if (ae.getSource().equals(this.vista.jButtonDelete)) {
            
            
            String nombre = this.vista.jTextFieldNombre.getText();
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar la agencia", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {
                
                modelo.delete(nombre);
                blancosCampos();
                tabla();
            } else {
                

            }

        }

        if (ae.getSource().equals(this.vista.jButtonExit)) {
            
            this.vista.dispose();
           ContPrincipal principal=new ContPrincipal(new VistaPrincipal());

        }

    }
    
    
    public void blancosCampos() {


        vista.jTextFieldNombre.setText("");
        vista.jTextFieldDireccion.setText("");
        vista.jTextFieldTelefono.setText("");
     
        
    }
    public void tabla() {
        List<Agencia> agenciaTodos = modelo.readAll();
           Agencia agenciaall;
           int filas = tablaconsulta.getRowCount();
           
           for (int i =0 ; filas >i; i++){
                              
               tablaconsulta.removeRow(0);
               
           }
           
           Iterator<Agencia> contactoIterator = agenciaTodos.iterator();
           
           while (contactoIterator.hasNext()){
           
           agenciaall = contactoIterator.next();
           
               Object rowData[] = {agenciaall.getId_agencia(),agenciaall.getNombre(),agenciaall.getDireccion(),agenciaall.getTelefono()};
               
               tablaconsulta.addRow(rowData);
        
        
           }
    }
    

}
