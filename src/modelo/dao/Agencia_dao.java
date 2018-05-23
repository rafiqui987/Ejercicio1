/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.vo.Agencia;
import utilidades.ConexionDB;

public class Agencia_dao {

    private Connection dbConection;
    private PreparedStatement preparedStmt;
    private ResultSet resultSet;

    public Agencia_dao() {
        dbConection = null;
        preparedStmt = null;
        resultSet = null;

    }

    public int creat(Agencia agencia) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String insertSQL = "INSERT INTO agencia ( nombre, direccion, telefono)" + "VALUES(?,?,?)";
            preparedStmt = dbConection.prepareStatement(insertSQL);
            preparedStmt.setString(1, agencia.getNombre());
            preparedStmt.setString(2, agencia.getDireccion());
            preparedStmt.setString(3, String.valueOf(agencia.getTelefono()));
            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Se ha creado una nueva agencia extitosamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas con la creacion comuniquese con el administrador");

        }

        return resultado;

    }

    public Agencia read(String nombre) {

        Agencia AgenciaRead = new Agencia();
        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  agencia  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            preparedStmt.setString(1, nombre);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                AgenciaRead.setId_agencia(resultSet.getInt("id_agencia"));
                AgenciaRead.setNombre(resultSet.getString("nombre"));
                AgenciaRead.setDireccion(resultSet.getString("direccion"));
                AgenciaRead.setTelefono(resultSet.getInt("telefono"));

            }

            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");

        }
        return AgenciaRead;

    }

    public int update(Agencia agencia) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String updateSQL = "UPDATE  agencia  SET  telefono  = ?,direccion  =  ?" + "  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(updateSQL);
            preparedStmt.setString(1, String.valueOf(agencia.getTelefono()));
            preparedStmt.setString(2, agencia.getDireccion());
            preparedStmt.setString(3, agencia.getNombre());
            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  la Actualizaciion Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public int delete(String nombre) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String deleteSQL = "DELETE  FROM  agencia  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(deleteSQL);
            preparedStmt.setString(1, nombre);
            resultado = preparedStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  el  Borrado  de una agencia  Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public List<Agencia> readAll() {
        List<Agencia> listAgencia = new ArrayList();

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  agencia";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            resultSet = preparedStmt.executeQuery();
            Agencia AgenciaAll;
            while (resultSet.next()) {
                AgenciaAll = new Agencia();
                AgenciaAll.setId_agencia(resultSet.getInt("id_agencia"));
                AgenciaAll.setNombre(resultSet.getString("Nombre"));
                AgenciaAll.setDireccion(resultSet.getString("Direccion"));
                AgenciaAll.setTelefono(resultSet.getInt("telefono"));

                listAgencia.add(AgenciaAll);
            }
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
        }
        return listAgencia;
    }

}
