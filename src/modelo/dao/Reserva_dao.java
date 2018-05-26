/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.vo.*;
import utilidades.ConexionDB;

public class Reserva_dao {

    private Connection dbConection;
    private PreparedStatement preparedStmt;
    private ResultSet resultSet;
    private Agencia agencia = new Agencia();
    private Cliente cliente = new Cliente();
    private Automovil automovil = new Automovil();
    private Garaje garaje = new Garaje();
    private Reserva reserva = new Reserva();

    public Reserva_dao() {
        dbConection = null;
        preparedStmt = null;
        resultSet = null;

    }

    public int creat(Reserva reserva) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String insertSQL = "INSERT INTO reserva (id_agencia, id_automovil, id_cliente, iva, costo_final, fecha_inicio, fecha_final, estado,costo)" + "VALUES(?,?,?,?,?,?,?,?,?)";
            preparedStmt = dbConection.prepareStatement(insertSQL);
            preparedStmt.setString(1, String.valueOf(reserva.getAgencia().getId_agencia()));
            preparedStmt.setString(2, String.valueOf(reserva.getAutomovil().getId_automovil()));
            preparedStmt.setString(3, String.valueOf(reserva.getCliente().getId_cliente()));
            preparedStmt.setString(4, String.valueOf(reserva.getIva()));
            preparedStmt.setString(5, String.valueOf(reserva.getCosto_final()));
            preparedStmt.setString(6, String.valueOf(reserva.getFecha_inicio()));
            preparedStmt.setString(7, String.valueOf(reserva.getFecha_final()));
            preparedStmt.setString(8, reserva.getEstado());
            preparedStmt.setString(9, String.valueOf(reserva.getCosto()));

            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Se ha creado extitosamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas con la creacion comuniquese con el administrador");

        }

        return resultado;

    }

    public Reserva read(int id_reserva) {

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  reserva , cliente, agencia, automovil, garaje"
                    + " WHERE  id_reserva  =  ? and "
                    + " reserva.id_cliente = cliente.id_cliente  and "
                    + "reserva.id_agencia=agencia.id_agencia and "
                    + "reserva.id_automovil=automovil.id_automovil and "
                    + "automovil.id_garaje=garaje.id_garaje";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            preparedStmt.setString(1, String.valueOf(id_reserva));
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                reserva.setId_reserva(resultSet.getInt("id_reserva"));
                reserva.setIva(resultSet.getInt("iva"));
                reserva.setCosto_final(resultSet.getInt("Costo_final"));
                reserva.setFecha_inicio(resultSet.getString("fecha_inicio"));
                reserva.setFecha_final(resultSet.getString("Fecha_final"));
                reserva.setEstado(resultSet.getString("Estado"));
                
                cliente.setId_cliente(resultSet.getInt("id_cliente"));
                cliente.setIdentificacion(resultSet.getInt("identificacion"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setTelefono(resultSet.getInt("telefono"));

                agencia.setId_agencia(resultSet.getInt("id_agencia"));
                agencia.setNombre(resultSet.getString("nombre"));
                agencia.setDireccion(resultSet.getString("direccion"));
                agencia.setTelefono(resultSet.getInt("telefono"));

                automovil.setId_automovil(resultSet.getInt("id_automovil"));
                automovil.setPlaca(resultSet.getString("placa"));
                automovil.setMarca(resultSet.getString("marca"));
                automovil.setModelo(resultSet.getInt("modelo"));
                automovil.setPreciodia(resultSet.getInt("preciodia"));

                garaje.setId_garaje(resultSet.getInt("id_garaje"));
                garaje.setNombre(resultSet.getString("nombre"));
                garaje.setDireccion(resultSet.getString("direccion"));
                automovil.setGaraje(garaje);

                reserva.setAgencia(agencia);
                reserva.setAutomovil(automovil);
                reserva.setCliente(cliente);

            }

            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");

        }
        return reserva;

    }

    public int update(Reserva reserva) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String updateSQL = "UPDATE  reserva  SET iva  = ?,Estado  =  ?, direccion = ?, telefono= ?" + "  WHERE  identificacion  =  ?";
            preparedStmt = dbConection.prepareStatement(updateSQL);
            preparedStmt.setString(1, String.valueOf(reserva.getIva()));
            preparedStmt.setString(2, reserva.getEstado());
            preparedStmt.setString(3, String.valueOf(reserva.getFecha_final()));
            preparedStmt.setString(4, String.valueOf(reserva.getFecha_inicio()));
            preparedStmt.setString(5, String.valueOf(reserva.getCosto_final()));
            preparedStmt.setString(9, String.valueOf(reserva.getId_reserva()));

            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  la Actualizacion Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public int delete(int id_reserva) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String deleteSQL = "DELETE  FROM  reserva  WHERE  id_reserva  =  ?";
            preparedStmt = dbConection.prepareStatement(deleteSQL);
            preparedStmt.setString(1, String.valueOf(id_reserva));
            resultado = preparedStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  el  Borrado    Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public List<Reserva> ReservareadAll() {
        List<Reserva> listreserva = new ArrayList();

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  reserva , cliente, agencia, automovil "
                    + "where reserva.id_cliente = cliente.id_cliente  and "
                    + "reserva.id_agencia=agencia.id_agencia and "
                    + "reserva.id_automovil=automovil.id_automovil ";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            resultSet = preparedStmt.executeQuery();
            Reserva ReservaAll;

            while (resultSet.next()) {
                ReservaAll = new Reserva();
                ReservaAll.setId_reserva(resultSet.getInt("id_reserva"));
                agencia.setId_agencia(resultSet.getInt("id_agencia"));
                agencia.setNombre(resultSet.getString("nombre"));
                agencia.setDireccion(resultSet.getString("direccion"));
                agencia.setTelefono(resultSet.getInt("telefono"));
                automovil.setId_automovil(resultSet.getInt("id_automovil"));
                automovil.setPlaca(resultSet.getString("placa"));
                automovil.setMarca(resultSet.getString("marca"));
                automovil.setModelo(resultSet.getInt("modelo"));
                automovil.setPreciodia(resultSet.getInt("preciodia"));
                cliente.setId_cliente(resultSet.getInt("id_cliente"));
                cliente.setIdentificacion(resultSet.getInt("identificacion"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setTelefono(resultSet.getInt("telefono"));
                ReservaAll.setIva(resultSet.getInt("iva"));
                ReservaAll.setCosto_final(resultSet.getInt("costo_final"));
                ReservaAll.setFecha_inicio(resultSet.getString("fecha_inicio"));
                ReservaAll.setFecha_final(resultSet.getString("fecha_final"));
                ReservaAll.setEstado(resultSet.getString("estado"));
                ReservaAll.setCosto(resultSet.getInt("costo"));
                ReservaAll.setAgencia(agencia);
                ReservaAll.setAutomovil(automovil);
                ReservaAll.setCliente(cliente);

                listreserva.add(ReservaAll);
            }
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
        }
        return listreserva;
    }

}
