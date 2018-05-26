/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.*;
import modelo.vo.*;
import vista.VistaReserva;
import vista.VistaPrincipal;

public class ContReserva implements ActionListener {

    private VistaReserva vista;
    private Reserva_dao modelo;
    private Automovil_dao modeloauto;
    private Agencia_dao modeloagencia;
    private Cliente_dao modelocliente;
    private DefaultTableModel tablaconsulta;
    private Cliente cliente = new Cliente();
    private Reserva reserva = new Reserva();
    private Automovil automovil = new Automovil();
    private Agencia agencia = new Agencia();

    public ContReserva(VistaReserva vista, Reserva_dao modelo, Agencia_dao modeloaagencia, Automovil_dao modeloauto, Cliente_dao modelocliente) {

        this.vista = vista;
        this.modelo = modelo;
        this.modeloagencia = modeloaagencia;
        this.modeloauto = modeloauto;
        this.modelocliente = modelocliente;
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

                int id_reserva = Integer.parseInt(this.vista.jTextFieldIdReserva.getText());
                reserva = modelo.read(id_reserva);
                if (reserva.getEstado() == null) {
                    JOptionPane.showMessageDialog(null, "La reserva no existe");

                } else {

                    this.vista.jTextFieldIdentificacion.setText(String.valueOf(reserva.getCliente().getIdentificacion()));
                    this.vista.jTextFieldNombre.setText(reserva.getCliente().getNombre());
                    this.vista.jTextFieldApellido.setText(reserva.getCliente().getApellido());
                    this.vista.jTextFieldDireccion.setText(reserva.getCliente().getDireccion());
                    this.vista.jTextFieldTelefono.setText(String.valueOf(reserva.getCliente().getTelefono()));
                    this.vista.jTextFieldPlaca.setText(reserva.getAutomovil().getPlaca());
                    this.vista.jTextFieldMarca.setText(reserva.getAutomovil().getMarca());
                    this.vista.jTextFieldModelo.setText(String.valueOf(reserva.getAutomovil().getModelo()));
                    this.vista.jTextFieldPreciodia.setText(String.valueOf(reserva.getAutomovil().getPreciodia()));
                    this.vista.jTextFieldGaraje.setText(reserva.getAutomovil().getGaraje().getNombre());

                }
            }

        }
        if (ae.getSource().equals(this.vista.jButtonCreate)) {

            if (vista.jTextFieldIdentificacion.getText().equals("")
                    || vista.jTextFieldPlaca.getText().equals("")
                    || vista.jFormatTFFechaInicio.getText().equals("")
                    || vista.jFormatTFFechaFinal.getText().equals("")
                    || vista.jComboBoxAgencia.getSelectedItem().equals("Selecionar")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son abligatorios ");
            } else {

                try {

                    automovil = modeloauto.read(vista.jTextFieldPlaca.getText());
                    agencia = modeloagencia.read(String.valueOf(vista.jComboBoxAgencia.getSelectedItem()));
                    cliente = modelocliente.read(Integer.parseInt(vista.jTextFieldIdentificacion.getText()));
                    reserva.setAutomovil(automovil);
                    reserva.setAgencia(agencia);
                    reserva.setCliente(cliente);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String Nombre = (String.valueOf(this.vista.jComboBoxAgencia.getSelectedItem()));
                    agencia = modeloagencia.read(Nombre);
                    reserva.setFecha_final(vista.jFormatTFFechaFinal.getText());
                    reserva.setFecha_inicio((vista.jFormatTFFechaInicio.getText()));
                    Date fechaFinal = dateFormat.parse(reserva.getFecha_final());
                    Date fechaInicial = dateFormat.parse(reserva.getFecha_inicio());
                    int dias = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 86400000);
                    System.out.println(dias);
                    int costo = (int) reserva.getAutomovil().getPreciodia() * dias;
                    System.out.println(costo);
                    vista.jTextFieldcosto.setText(String.valueOf(costo));
                    reserva.setCosto(costo);
                    reserva.setEstado(String.valueOf(vista.jComboBoxEstado.getSelectedItem()));
                    reserva.setIva(Integer.parseInt(vista.jTextFielIVA.getText()));
                    int costofinal = (int) ((costo * reserva.getIva()) / 100) + costo;
                    vista.jTextFieldCostofinal.setText(String.valueOf(costofinal));
                    reserva.setCosto_final(costofinal);
                    modelo.creat(reserva);
                    blancosCampos();

                    tabla();
                } catch (ParseException ex) {
                    Logger.getLogger(ContReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

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

            int id_reserva = Integer.parseInt(this.vista.jTextFieldIdReserva.getText());
            int rep = JOptionPane.showConfirmDialog(null, "Esta seguro desea borrar ", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

            if (rep == 0) {

                modelo.delete(id_reserva);
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

                int identificacion = Integer.parseInt(this.vista.jTextFieldIdentificacion.getText());
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

                String placa = this.vista.jTextFieldPlaca.getText();
                automovil = modeloauto.read(placa);
                if (automovil.getPlaca() == null) {
                    JOptionPane.showMessageDialog(null, "El Automovil no existe");

                } else {
                    this.vista.jTextFieldPlaca.setText(automovil.getPlaca());
                    this.vista.jTextFieldMarca.setText(automovil.getMarca());
                    this.vista.jTextFieldModelo.setText(String.valueOf(automovil.getModelo()));
                    this.vista.jTextFieldPreciodia.setText(String.valueOf(automovil.getPreciodia()));
                    this.vista.jTextFieldGaraje.setText(automovil.getGaraje().getNombre());
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
        vista.jTextFieldApellido.setText("");
        vista.jTextFieldNombre.setText("");
        vista.jTextFieldTelefono.setText("");
        vista.jTextFieldDireccion.setText("");
        vista.jFormatTFFechaFinal.setText("");

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

            Object rowData[] = {Automovilall.getId_automovil(), Automovilall.getPlaca(), Automovilall.getMarca(), Automovilall.getModelo(), Automovilall.getPreciodia(), Automovilall.getGaraje().getNombre()};

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

            this.vista.jComboBoxAgencia.addItem(Agenciaall.getNombre());

        }
    }

}
