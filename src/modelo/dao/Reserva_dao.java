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
    private Agencia AgenciaAll = new Agencia();
    private Cliente ClienteAll = new Cliente();
    private Automovil AutomovilAll = new Automovil();
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
                
                ClienteAll.setId_cliente(resultSet.getInt("id_cliente"));
                ClienteAll.setIdentificacion(resultSet.getInt("identificacion"));
                ClienteAll.setNombre(resultSet.getString("nombre"));
                ClienteAll.setApellido(resultSet.getString("apellido"));
                ClienteAll.setDireccion(resultSet.getString("direccion"));
                ClienteAll.setTelefono(resultSet.getInt("telefono"));

                AgenciaAll.setId_agencia(resultSet.getInt("id_agencia"));
                AgenciaAll.setNombre(resultSet.getString("nombre"));
                AgenciaAll.setDireccion(resultSet.getString("direccion"));
                AgenciaAll.setTelefono(resultSet.getInt("telefono"));

                AutomovilAll.setId_automovil(resultSet.getInt("id_automovil"));
                AutomovilAll.setPlaca(resultSet.getString("placa"));
                AutomovilAll.setMarca(resultSet.getString("marca"));
                AutomovilAll.setModelo(resultSet.getInt("modelo"));
                AutomovilAll.setPreciodia(resultSet.getInt("preciodia"));

                garaje.setId_garaje(resultSet.getInt("id_garaje"));
                garaje.setNombre(resultSet.getString("nombre"));
                garaje.setDireccion(resultSet.getString("direccion"));
                AutomovilAll.setGaraje(garaje);

                reserva.setAgencia(AgenciaAll);
                reserva.setAutomovil(AutomovilAll);
                reserva.setCliente(ClienteAll);

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
            Agencia AgenciaAll;
            Automovil AutomovilAll;
            Cliente ClienteAll;

            while (resultSet.next()) {
                ReservaAll = new Reserva();
                AgenciaAll = new Agencia();
                AutomovilAll = new Automovil();
                ClienteAll = new Cliente();
                
                ReservaAll.setId_reserva(resultSet.getInt("id_reserva"));
                AgenciaAll.setId_agencia(resultSet.getInt("id_agencia"));
                AgenciaAll.setNombre(resultSet.getString("nombre"));
                AgenciaAll.setDireccion(resultSet.getString("direccion"));
                AgenciaAll.setTelefono(resultSet.getInt("telefono"));
                AutomovilAll.setId_automovil(resultSet.getInt("id_automovil"));
                AutomovilAll.setPlaca(resultSet.getString("placa"));
                AutomovilAll.setMarca(resultSet.getString("marca"));
                AutomovilAll.setModelo(resultSet.getInt("modelo"));
                AutomovilAll.setPreciodia(resultSet.getInt("preciodia"));
                ClienteAll.setId_cliente(resultSet.getInt("id_cliente"));
                ClienteAll.setIdentificacion(resultSet.getInt("identificacion"));
                ClienteAll.setNombre(resultSet.getString("nombre"));
                ClienteAll.setApellido(resultSet.getString("apellido"));
                ClienteAll.setDireccion(resultSet.getString("direccion"));
                ClienteAll.setTelefono(resultSet.getInt("telefono"));
                ReservaAll.setIva(resultSet.getInt("iva"));
                ReservaAll.setCosto_final(resultSet.getInt("costo_final"));
                ReservaAll.setFecha_inicio(resultSet.getString("fecha_inicio"));
                ReservaAll.setFecha_final(resultSet.getString("fecha_final"));
                ReservaAll.setEstado(resultSet.getString("estado"));
                ReservaAll.setCosto(resultSet.getInt("costo"));
                ReservaAll.setAgencia(AgenciaAll);
                ReservaAll.setAutomovil(AutomovilAll);
                ReservaAll.setCliente(ClienteAll);

                listreserva.add(ReservaAll);
                System.out.println("modelo.dao.Reserva_dao.ReservareadAll()"+ReservaAll.getCliente().getNombre());
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
