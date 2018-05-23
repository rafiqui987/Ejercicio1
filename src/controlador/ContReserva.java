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
import vista.VistaReserva;
import vista.VistaPrincipal;


public class ContReserva implements ActionListener{
    
  
    private VistaReserva vista;
    private Reserva_dao modelo;
    private Automovil_dao modeloauto;
    private Agencia_dao modeloagencia;
    private Cliente_dao modelocliente;
    private DefaultTableModel tablaconsulta;
    
    

    public ContReserva(VistaReserva vista, Reserva_dao modelo, Agencia_dao modeloaaencia, Automovil_dao modeloauto, Cliente_dao modelocliente) {

        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButtonSelect.addActionListener(this);
        this.vista.jButtonUpdate.addActionListener(this);
        this.vista.jButtonCreate.addActionListener(this);
        this.vista.jButtonDelete.addActionListener(this);
        this.vista.jButtonExit.addActionListener(this);
        this.vista.jTextFieldIdentificacion.addActionListener(this);
        this.vista.jTextFieldPlaca.addActionListener(this);
        this.tablaconsulta = (DefaultTableModel) this.vista.jTableAutomovil.getModel();
        tabla();
        this.vista.setVisible(true);
        tabla2();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(this.vista.jButtonSelect)) {

            if (vista.jTextFieldIdReserva.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Id Reserva no puede estar vacio");

            } else {
                Reserva reserva = new Reserva();
                int idreserva = Integer.parseInt(this.vista.jTextFieldIdReserva.getText());
                reserva = modelo.read(idreserva);
                if (reserva.getEstado()== null) {
                    JOptionPane.showMessageDialog(null, "La reserva no existe");

                } else {
                   /* this.vista.jTextFieldPlaca.setText(reserva.getPlaca());
                    this.vista.jTextFieldMarca.setText(reserva.getMarca());
                    this.vista.jTextFieldModelo.setText(String.valueOf(reserva.getModelo()));
                    this.vista.jTextFieldPreciodia.setText(String.valueOf(reserva.getPreciodia()));
                    */
                    
                    
                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldIdentificacion.getText().equals("") || 
                vista.jComboBoxAgencia.getSelectedItem().equals("Selecionar")
                  ) {
                    
          
                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {
               
                tabla();

            }
        }

        if (ae.getSource().equals(this.vista.jButtonUpdate)) {

           /* Automovil automovil = new Automovil();
            
            automovil.setPlaca(vista.jTextFieldPlaca.getText());
            automovil.setMarca(vista.jTextFieldMarca.getText());
            automovil.setModelo(Integer.parseInt(vista.jTextFieldModelo.getText()));
            automovil.setPreciodia(Integer.parseInt(vista.jTextFieldPreciodia.getText()));

            modelo.update(automovil);
            blancosCampos();
            tabla();
            this.vista.jComboBoxAgencia.setEditable(false);
*/
        }

        if (ae.getSource().equals(this.vista.jButtonDelete)) {

            int idreserva = Integer.parseInt(this.vista.jTextFieldIdReserva.getText());
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar ", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {

                modelo.delete(idreserva);
                blancosCampos();
                tabla();
                this.vista.jComboBoxAgencia.setEditable(false);
            } else {

            }

        }

        if (ae.getSource().equals(this.vista.jButtonExit)) {

            this.vista.dispose();
            ContPrincipal principal = new ContPrincipal(new VistaPrincipal());

        }

        if (ae.getSource().equals(this.vista.jTextFieldIdentificacion)) {
                 if (vista.jTextFieldIdentificacion.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Identificacion no puede estar vacio");

            } else {
                Cliente cliente = new Cliente();
                int identificacion = Integer.valueOf(this.vista.jTextFieldIdentificacion.getText());
                cliente = modelocliente.read(identificacion);
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
        
        
        if (ae.getSource().equals(this.vista.jTextFieldPlaca)) {
                 if (vista.jTextFieldPlaca.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "el campo Placa no puede estar vacio");

            } else {
                Automovil automovil = new Automovil();
                String placa = this.vista.jTextFieldPlaca.getText();
                automovil = modeloauto.read(placa);
                if (automovil.getPlaca()== null) {
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
    }

    public void blancosCampos() {
        
        vista.jTextFieldPlaca.setText("");
        vista.jTextFieldMarca.setText("");
        vista.jTextFieldModelo.setText("");
        vista.jTextFieldPreciodia.setText("");
        vista.jComboBoxGaraje.setSelectedIndex(0);
        

    }

    public void tabla() {
        List<Automovil> automovilTodos = modeloauto.readAll();
        Automovil Automovilall;
        int filas = tablaconsulta.getRowCount();

        for (int i = 0; filas > i; i++) {

            tablaconsulta.removeRow(0);

        }

        Iterator<Automovil> AutomovilIterator = automovilTodos.iterator();

        while (AutomovilIterator.hasNext()) {

            Automovilall = AutomovilIterator.next();

            Object rowData[] = {Automovilall.getId_automovil(),Automovilall.getPlaca(), Automovilall.getMarca(),Automovilall.getModelo(), Automovilall.getPreciodia(),Automovilall.getGaraje().getId_garaje()};

            tablaconsulta.addRow(rowData);

        }
    }
    
    
     
        public void tabla2() {
        List<Agencia> agenciatodos = modeloagencia.readAll();
        Agencia Agenciaall;
        
        Iterator<Agencia> AgenciaIterator = agenciatodos.iterator();
        this.vista.jComboBoxAgencia.addItem("Selecionar");
        while (AgenciaIterator.hasNext()) {

            Agenciaall = AgenciaIterator.next();

            //Object rowData[] = {Garajeall.getId_garaje(),Garajeall.getNombre(), Garajeall.getDireccion()};

            //tablaconsulta.addRow(rowData);
            this.vista.jComboBoxAgencia.addItem(Agenciaall.getNombre());

        }
    }
    
    

    
}
