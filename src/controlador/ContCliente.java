/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.xml.internal.fastinfoset.EncodingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.*;
import modelo.vo.*;
import vista.*;

public class ContCliente implements ActionListener {

    private VistaCliente vista;
    private Cliente_dao modelo;
    private DefaultTableModel tablaconsulta;

    public ContCliente(VistaCliente vista, Cliente_dao modelo) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonUpdate.addActionListener(this);
        this.vista.jButtonCreate.addActionListener(this);
        this.vista.jButtonDelete.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableCliente.getModel();
        tabla();
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.vista.jButtonSelect)) {

            if (vista.jTextFieldIdentificacion.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Identificacion no puede estar vacio");

            } else {
                Cliente cliente = new Cliente();
                int identificacion = Integer.valueOf(this.vista.jTextFieldIdentificacion.getText());
                cliente = modelo.read(identificacion);
                if (cliente.getNombre() == null) {
                    JOptionPane.showMessageDialog(null, "El Cliente no existe");

                } else {
                    this.vista.jTextFieldNombre.setText(cliente.getNombre());
                    this.vista.jTextFieldApellido.setText(cliente.getApellido());
                    this.vista.jTextFieldDireccion.setText(cliente.getDireccion());
                    this.vista.jTextFieldTelefono.setText(String.valueOf(cliente.getTelefono()));
                    tabla();
                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldNombre.getText().equals("") || 
                vista.jTextFieldDireccion.getText().equals("") || 
                vista.jTextFieldTelefono.getText().equals("")   ||
                vista.jTextFieldIdentificacion.getText().equals("") ||
                vista.jTextFieldApellido.getText().equals("")) {
                    
          
                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {

                Cliente clienteCreate = new Cliente();
                clienteCreate.setIdentificacion(Integer.parseInt(vista.jTextFieldIdentificacion.getText()));
                clienteCreate.setNombre(vista.jTextFieldNombre.getText());
                clienteCreate.setApellido(vista.jTextFieldApellido.getText());
                clienteCreate.setDireccion(vista.jTextFieldDireccion.getText());
                clienteCreate.setTelefono(Integer.parseInt(vista.jTextFieldTelefono.getText()));
                modelo.creat(clienteCreate);
                blancosCampos();
                tabla();

            }
        }

        if (ae.getSource().equals(this.vista.jButtonUpdate)) {

            Cliente cliente = new Cliente();
            
            cliente.setNombre(vista.jTextFieldNombre.getText());
            cliente.setApellido(vista.jTextFieldApellido.getText());
            cliente.setDireccion(vista.jTextFieldDireccion.getText());
            cliente.setTelefono(Integer.parseInt(vista.jTextFieldTelefono.getText()));
            cliente.setIdentificacion(Integer.parseInt(vista.jTextFieldIdentificacion.getText()));

            modelo.update(cliente);
            blancosCampos();
            tabla();

        }

        if (ae.getSource().equals(this.vista.jButtonDelete)) {

            String identificacion = this.vista.jTextFieldIdentificacion.getText();
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar el cliente", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {

                modelo.delete(identificacion);
                blancosCampos();
                tabla();
            } else {

            }

        }

        if (ae.getSource().equals(this.vista.jButtonExit)) {

            this.vista.dispose();
            ContPrincipal principal = new ContPrincipal(new VistaPrincipal());

        }

    }

    public void blancosCampos() {
        
        vista.jTextFieldIdentificacion.setText("");
        vista.jTextFieldNombre.setText("");
        vista.jTextFieldApellido.setText("");
        vista.jTextFieldDireccion.setText("");
        vista.jTextFieldTelefono.setText("");
        

    }

    public void tabla() {
        List<Cliente> ClienteTodos = modelo.readAll();
        Cliente Clienteaall;
        int filas = tablaconsulta.getRowCount();

        for (int i = 0; filas > i; i++) {

            tablaconsulta.removeRow(0);

        }

        Iterator<Cliente> clienteIterator = ClienteTodos.iterator();

        while (clienteIterator.hasNext()) {

            Clienteaall = clienteIterator.next();

            Object rowData[] = {Clienteaall.getId_cliente(),Clienteaall.getIdentificacion() , Clienteaall.getNombre(),Clienteaall.getApellido(), Clienteaall.getDireccion(), String.valueOf(Clienteaall.getTelefono())};

            tablaconsulta.addRow(rowData);

        }
    }

}
