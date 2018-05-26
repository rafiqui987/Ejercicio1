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

public class ContGaraje implements ActionListener {

    private VistaGaraje vista;
    private Garaje_dao modelo;
    private DefaultTableModel tablaconsulta;
    Garaje garaje = new Garaje();

    public ContGaraje(VistaGaraje vista, Garaje_dao modelo) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonUpdate.addActionListener(this);
        this.vista.jButtonCreate.addActionListener(this);
        this.vista.jButtonDelete.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableGaraje.getModel();
        tabla();
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.vista.jButtonSelect)) {

            if (vista.jTextFieldNombre.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Nombre no puede estar vacio");

            } else {

                String nombre = this.vista.jTextFieldNombre.getText();
                garaje = modelo.read(nombre);
                if (garaje.getNombre() == null) {
                    JOptionPane.showMessageDialog(null, "el garaje no existe");

                } else {
                    this.vista.jTextFieldDireccion.setText(garaje.getDireccion());

                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldNombre.getText().equals("") || vista.jTextFieldDireccion.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {

                garaje.setNombre(vista.jTextFieldNombre.getText());
                garaje.setDireccion(vista.jTextFieldDireccion.getText());

                modelo.creat(garaje);
                blancosCampos();
                tabla();

            }
        }

        if (ae.getSource().equals(this.vista.jButtonUpdate)) {

            garaje.setNombre(vista.jTextFieldNombre.getText());
            garaje.setDireccion(vista.jTextFieldDireccion.getText());

            modelo.update(garaje);
            blancosCampos();
            tabla();

        }

        if (ae.getSource().equals(this.vista.jButtonDelete)) {

            String nombre = this.vista.jTextFieldNombre.getText();
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar el garaje", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {

                modelo.delete(nombre);
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

        vista.jTextFieldNombre.setText("");
        vista.jTextFieldDireccion.setText("");

    }

    public void tabla() {
        List<Garaje> GarajeTodos = modelo.readAll();
        Garaje Garajeall;
        int filas = tablaconsulta.getRowCount();

        for (int i = 0; filas > i; i++) {

            tablaconsulta.removeRow(0);

        }

        Iterator<Garaje> contactoIterator = GarajeTodos.iterator();

        while (contactoIterator.hasNext()) {

            Garajeall = contactoIterator.next();

            Object rowData[] = {Garajeall.getId_garaje(), Garajeall.getNombre(), Garajeall.getDireccion()};

            tablaconsulta.addRow(rowData);

        }
    }

}
