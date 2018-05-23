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

    public Automovil_dao() {
        dbConection = null;
        preparedStmt = null;
        resultSet = null;

    }

    public int creat(Automovil automovil) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String insertSQL = "INSERT INTO automovil (id_automovil, placa, marca, modelo, preciodia, id_garaje)" + "VALUES(?,?,?,?,?,?)";
            preparedStmt = dbConection.prepareStatement(insertSQL);
            preparedStmt.setString(1, String.valueOf(automovil.getId_automovil()));
            preparedStmt.setString(2, automovil.getPlaca());
            preparedStmt.setString(3, automovil.getMarca());
            preparedStmt.setString(4, String.valueOf(automovil.getModelo()));
            preparedStmt.setString(5, String.valueOf(automovil.getPreciodia()));
            preparedStmt.setString(6, String.valueOf(automovil.getId_garaje()));
            resultado = preparedStmt.executeUpdate();
            dbConection.close();
            preparedStmt.close();
            JOptionPane.showMessageDialog(null, "Se ha creado una nuevo automovil extitosamente");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas con la creacion  de contacto comuniquese con el administrador");

        }

        return resultado;

    }

    public Automovil read(String placa) {

        Automovil AutomovilRead = new Automovil();
        Garaje Garaje =new Garaje();
        try {
            dbConection = ConexionDB.getConexion();
            String selectSQL = "SELECT * from automovil, garaje   where automovil.id_garaje = garaje.id_garaje and placa = ?";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            preparedStmt.setString(1, placa);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                AutomovilRead.setId_automovil(resultSet.getInt("id_automovil"));
                AutomovilRead.setPlaca(resultSet.getString("placa"));
                AutomovilRead.setMarca(resultSet.getString("marca"));
                AutomovilRead.setModelo(resultSet.getInt("modelo"));
                AutomovilRead.setPreciodia(resultSet.getInt("preciodia"));
                Garaje.setId_garaje(0);
                Garaje.setNombre("");
                Garaje.setDireccion("");
                AutomovilRead.setGaraje(Garaje);

            }

            dbConection.close();
            preparedStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");

        }
        return AutomovilRead;

    }

    public int update(Automovil automovil) {
        int resultado = 0;
        try {
            dbConection = ConexionDB.getConexion();
            String updateSQL = "UPDATE  automovil  SET marca  = ?,modelo  =  ?, preciodia = ?, id_garaje= ?" + "  WHERE  placa  =  ?";
            preparedStmt = dbConection.prepareStatement(updateSQL);
            preparedStmt.setString(1, automovil.getMarca());
            preparedStmt.setString(2, String.valueOf(automovil.getModelo()));
            preparedStmt.setString(3, String.valueOf(automovil.getPreciodia()));
            preparedStmt.setString(4, String.valueOf(automovil.getGaraje().getId_garaje()));
            preparedStmt.setString(5, automovil.getPlaca());

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
            String selectSQL = "SELECT  *  FROM  automovil";
            preparedStmt = dbConection.prepareStatement(selectSQL);
            resultSet = preparedStmt.executeQuery();
            Automovil AutomovilAll;
            while (resultSet.next()) {
                AutomovilAll = new Automovil();
                AutomovilAll.setId_automovil(resultSet.getInt("id_automovil"));
                AutomovilAll.setPlaca(resultSet.getString("placa"));
                AutomovilAll.setMarca(resultSet.getString("marca"));
                AutomovilAll.setModelo(resultSet.getInt("modelo"));
                AutomovilAll.setPreciodia(resultSet.getInt("preciodia"));
                AutomovilAll.setId_garaje(resultSet.getInt("id_garaje"));
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

    public List<Garaje> garajereadAll() {
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

    public  Garaje  garjeread(String  nombre){

		Garaje  GarajeRead  =  new  Garaje();
            try{
                dbConection  =  ConexionDB.getConexion();
                String  selectSQL  ="SELECT  *  FROM  garaje  WHERE  nombre  =  ?"  ;
                preparedStmt  =  dbConection.prepareStatement(selectSQL);
                preparedStmt.setString  (1,  nombre);
                resultSet  =  preparedStmt.executeQuery();
                while(resultSet.next()){
                GarajeRead .setId_garaje(resultSet.getInt("id_garaje"));   
                GarajeRead .setNombre(resultSet.getString("nombre"));
		GarajeRead .setDireccion(resultSet.getString("direccion"));
		               
                }
                
                dbConection.close();
                preparedStmt.close();

            }catch(SQLException  e){
                 System.out.println(e.getMessage());
                 JOptionPane.showMessageDialog(null,  "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
                 
				 }
                return  GarajeRead ;
       
	   }
}
