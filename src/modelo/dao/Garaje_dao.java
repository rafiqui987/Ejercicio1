/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.vo.Garaje;
import utilidades.ConexionDB;

public class Garaje_dao {

    private Connection dbConection;
    private PreparedStatement preparedStmt;
    private ResultSet resultSet;

    public Garaje_dao() {
        dbConection = null;
        preparedStmt = null;
        resultSet = null;

    }

    public int creat(Garaje garaje) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String insertSQL = "INSERT INTO garaje (nombre, direccion)" + "VALUES(?,?)";
            preparedStmt = dbConection.prepareStatement(insertSQL);
            preparedStmt.setString(1, garaje.getNombre());
            preparedStmt.setString(2, garaje.getDireccion());
            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Se ha creado un nuevo garaje extitosamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas con la creacion comuniquese con el administrador");

        }

        return resultado;

    }

    public Garaje read(String nombre) {

        Garaje AgenciaRead = new Garaje();
        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  garaje  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            preparedStmt.setString(1, nombre);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                AgenciaRead.setId_garaje(resultSet.getInt("id_garaje"));
                AgenciaRead.setNombre(resultSet.getString("nombre"));
                AgenciaRead.setDireccion(resultSet.getString("direccion"));

            }

            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");

        }
        return AgenciaRead;

    }

    public int update(Garaje garaje) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String updateSQL = "UPDATE  garaje  SET  direccion  =  ?" + "  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(updateSQL);
            preparedStmt.setString(1, garaje.getDireccion());
            preparedStmt.setString(2, garaje.getNombre());
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
            String deleteSQL = "DELETE  FROM  garaje  WHERE  nombre  =  ?";
            preparedStmt = dbConection.prepareStatement(deleteSQL);
            preparedStmt.setString(1, nombre);
            resultado = preparedStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  con  el  Borrado Comuniquese  con  el  Administrador");
        }

        return resultado;
    }

    public List<Garaje> readAll() {
        List<Garaje> listGaraje = new ArrayList();

        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT  *  FROM  garaje";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            resultSet = preparedStmt.executeQuery();
            Garaje GarajeAll;
            while (resultSet.next()) {
                GarajeAll = new Garaje();
                GarajeAll.setId_garaje(resultSet.getInt("id_garaje"));
                GarajeAll.setNombre(resultSet.getString("Nombre"));
                GarajeAll.setDireccion(resultSet.getString("Direccion"));

                listGaraje.add(GarajeAll);
            }
            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
        }
        return listGaraje;
    }

}
