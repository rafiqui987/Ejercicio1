/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

//import com.sun.xml.internal.fastinfoset.EncodingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.Automovil_dao;
import modelo.dao.Garaje_dao;
import modelo.vo.Automovil;
import modelo.vo.Garaje;
import vista.VistaAutomovil;
import vista.VistaPrincipal;

public class ContAutomovil implements ActionListener {

    private VistaAutomovil vista;
    private Automovil_dao modelo;
    private Garaje_dao modelogaraje;
    private DefaultTableModel tablaconsulta;
    

    public ContAutomovil(VistaAutomovil vista, Automovil_dao modelo,Garaje_dao modelogaraje) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonUpdate.addActionListener(this);
        this.vista.jButtonCreate.addActionListener(this);
        this.vista.jButtonDelete.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableAutomovil.getModel();

        this.vista.setVisible(true);
        tabla();
        tabla2();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.vista.jButtonSelect)) {

            if (vista.jTextFieldPlaca.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Placa no puede estar vacio");

            } else {
                Automovil automovil = new Automovil();
                String placa = this.vista.jTextFieldPlaca.getText();
                automovil = modelo.read(placa);
                if (automovil.getPlaca() == null) {
                    JOptionPane.showMessageDialog(null, "El Automovil no existe");

                } else {
                    this.vista.jTextFieldPlaca.setText(automovil.getPlaca());
                    this.vista.jTextFieldMarca.setText(automovil.getMarca());
                    this.vista.jTextFieldModelo.setText(String.valueOf(automovil.getModelo()));
                    this.vista.jTextFieldPreciodia.setText(String.valueOf(automovil.getPreciodia()));
                    this.vista.jComboBoxGaraje.setVisible(false);
                    tabla();

                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldMarca.getText().equals("")
                    || vista.jTextFieldModelo.getText().equals("")
                    || vista.jTextFieldPlaca.getText().equals("")
                    || vista.jTextFieldPreciodia.getText().equals("")
                    || vista.jComboBoxGaraje.getSelectedItem().equals("Selecionar")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {
                Garaje garaje = new Garaje();
                String Nombre = (String.valueOf(this.vista.jComboBoxGaraje.getSelectedItem()));
                garaje = modelo.garjeread(Nombre);
                Automovil automovilCreate = new Automovil();
                automovilCreate.setPlaca(vista.jTextFieldPlaca.getText());
                automovilCreate.setMarca(vista.jTextFieldMarca.getText());
                automovilCreate.setModelo(Integer.parseInt(vista.jTextFieldModelo.getText()));
                automovilCreate.setPreciodia(Integer.parseInt(vista.jTextFieldPreciodia.getText()));
                automovilCreate.setGaraje(garaje);
                modelo.creat(automovilCreate);
                blancosCampos();
                tabla();

            }
        }

        if (ae.getSource().equals(this.vista.jButtonUpdate)) {

            Automovil automovil = new Automovil();

            automovil.setPlaca(vista.jTextFieldPlaca.getText());
            automovil.setMarca(vista.jTextFieldMarca.getText());
            automovil.setModelo(Integer.parseInt(vista.jTextFieldModelo.getText()));
            automovil.setPreciodia(Integer.parseInt(vista.jTextFieldPreciodia.getText()));
            modelo.update(automovil);
            blancosCampos();
            tabla();
            this.vista.jComboBoxGaraje.setVisible(true);

        }

        if (ae.getSource().equals(this.vista.jButtonDelete)) {

            String placa = this.vista.jTextFieldPlaca.getText();
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar el cliente", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {

                modelo.delete(placa);
                blancosCampos();
                tabla();
                this.vista.jComboBoxGaraje.setVisible(true);
            } else {

            }

        }

        if (ae.getSource().equals(this.vista.jButtonExit)) {

            this.vista.dispose();
            ContPrincipal principal = new ContPrincipal(new VistaPrincipal());

        }

    }

    public void blancosCampos() {

        vista.jTextFieldPlaca.setText("");
        vista.jTextFieldMarca.setText("");
        vista.jTextFieldModelo.setText("");
        vista.jTextFieldPreciodia.setText("");
        vista.jComboBoxGaraje.setSelectedIndex(0);

    }

    public void tabla() {
        List<Automovil> automovilTodos = modelo.readAll();
        Automovil Automovilall;
        int filas = tablaconsulta.getRowCount();

        for (int i = 0; filas > i; i++) {

            tablaconsulta.removeRow(0);

        }

        Iterator<Automovil> AutomovilIterator = automovilTodos.iterator();

        while (AutomovilIterator.hasNext()) {

            Automovilall = AutomovilIterator.next();

            Object rowData[] = {Automovilall.getId_automovil(), Automovilall.getPlaca(), Automovilall.getMarca(), Automovilall.getModelo(), Automovilall.getPreciodia(), Automovilall.getId_garaje()};

            tablaconsulta.addRow(rowData);

        }
    }

    /**
     *
     */
    public void tabla2() {
        List<Garaje> garajetodos = modelo.garajereadAll();
        Garaje Garajeall;

        Iterator<Garaje> GarajeIterator = garajetodos.iterator();
        this.vista.jComboBoxGaraje.addItem("Selecionar");
        while (GarajeIterator.hasNext()) {

            Garajeall = GarajeIterator.next();

            //Object rowData[] = {Garajeall.getId_garaje(),Garajeall.getNombre(), Garajeall.getDireccion()};
            //tablaconsulta.addRow(rowData);
            this.vista.jComboBoxGaraje.addItem(Garajeall.getNombre());

        }
    }

}
