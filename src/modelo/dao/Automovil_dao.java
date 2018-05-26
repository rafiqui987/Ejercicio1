/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.vo.Automovil;
import modelo.vo.Garaje;
import utilidades.ConexionDB;

public class Automovil_dao {

    private Connection dbConection;
    private PreparedStatement preparedStmt;
    private ResultSet resultSet;
    Garaje garaje = new Garaje();
    Automovil automovil = new Automovil();

    public Automovil_dao() {
        dbConection = null;
        preparedStmt = null;
        resultSet = null;

    }

    public int creat(Automovil automovil) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String insertSQL = "INSERT INTO automovil ( placa, marca, modelo, preciodia, id_garaje)" + "VALUES(?,?,?,?,?)";
            preparedStmt = dbConection.prepareStatement(insertSQL);
            preparedStmt.setString(1, automovil.getPlaca());
            preparedStmt.setString(2, automovil.getMarca());
            preparedStmt.setString(3, String.valueOf(automovil.getModelo()));
            preparedStmt.setString(4, String.valueOf(automovil.getPreciodia()));
            preparedStmt.setString(5, String.valueOf(automovil.getGaraje().getId_garaje()));
            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Se ha creado extitosamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas con la creacion  comuniquese con el administrador");

        }

        return resultado;

    }

    public Automovil read(String placa) {

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT * from automovil, garaje   where automovil.id_garaje = garaje.id_garaje and placa = ?";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            preparedStmt.setString(1, placa);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                automovil.setId_automovil(resultSet.getInt("id_automovil"));
                automovil.setPlaca(resultSet.getString("placa"));
                automovil.setMarca(resultSet.getString("marca"));
                automovil.setModelo(resultSet.getInt("modelo"));
                automovil.setPreciodia(resultSet.getInt("preciodia"));
                garaje.setId_garaje(resultSet.getInt("id_garaje"));
                garaje.setNombre(resultSet.getString("nombre"));
                garaje.setDireccion(resultSet.getString("direccion"));
                automovil.setGaraje(garaje);

            }

            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");

        }
        return automovil;

    }

    public int update(Automovil automovil) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String updateSQL = "UPDATE  automovil  SET marca  = ?,modelo  =  ?, preciodia = ?" + "  WHERE  placa  =  ?";
            preparedStmt = dbConection.prepareStatement(updateSQL);
            preparedStmt.setString(1, automovil.getMarca());
            preparedStmt.setString(2, String.valueOf(automovil.getModelo()));
            preparedStmt.setString(3, String.valueOf(automovil.getPreciodia()));
            //preparedStmt.setString(4, String.valueOf(automovil.getGaraje().getId_garaje()));
            preparedStmt.setString(4, automovil.getPlaca());

            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  la Actualizaciion Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public int delete(String placa) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String deleteSQL = "DELETE  FROM  automovil  WHERE  placa  =  ?";
            preparedStmt = dbConection.prepareStatement(deleteSQL);
            preparedStmt.setString(1, placa);
            resultado = preparedStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  el  Borrado Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public List<Automovil> readAll() {
        List<Automovil> listAutomovil = new ArrayList();

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  automovil, garaje where automovil.id_garaje = garaje.id_garaje ";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            resultSet = preparedStmt.executeQuery();
            Automovil AutomovilAll;
            Garaje Garajeall;

            while (resultSet.next()) {
                AutomovilAll = new Automovil();
                Garajeall = new Garaje();
                AutomovilAll.setId_automovil(resultSet.getInt("id_automovil"));
                AutomovilAll.setPlaca(resultSet.getString("placa"));
                AutomovilAll.setMarca(resultSet.getString("marca"));
                AutomovilAll.setModelo(resultSet.getInt("modelo"));
                AutomovilAll.setPreciodia(resultSet.getInt("preciodia"));
                Garajeall.setId_garaje(resultSet.getInt("id_garaje"));
                Garajeall.setNombre(resultSet.getString("nombre"));
                Garajeall.setDireccion(resultSet.getString("direccion"));
                AutomovilAll.setGaraje(Garajeall);
                listAutomovil.add(AutomovilAll);
            }
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
        }
        return listAutomovil;
    }

}
