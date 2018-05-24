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
import modelo.vo.Automovil;
import modelo.vo.Cliente;
import modelo.vo.Reserva;
import utilidades.ConexionDB;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

public class Reserva_dao {
    private Connection dbConection;
     private PreparedStatement preparedStmt;
     private  ResultSet  resultSet;

	public Reserva_dao(){
	dbConection=null;
	preparedStmt=null;
	resultSet=null;
	
	}

	public  int creat(Reserva reserva){
	int resultado=0;
	try{
	dbConection = ConexionDB.getConexion();
	String insertSQL = "INSERT INTO reserva (id_reserva, id_agencia, id_automovil, id_cliente, iva, costo_final, fecha_inicio, fecha_final, estado,costo)"+"VALUES(?,?,?,?,?,?,?,?,?,?)";
	preparedStmt= dbConection.prepareStatement(insertSQL);
	preparedStmt.setString (1,String.valueOf(reserva.getId_reserva()));
        preparedStmt.setString (2,String.valueOf(reserva.getAgencia().getId_agencia()));
        preparedStmt.setString (3,String.valueOf(reserva.getAutomovil().getId_automovil()));
        preparedStmt.setString (4,String.valueOf(reserva.getCliente().getId_cliente()));
        preparedStmt.setString (5,String.valueOf(reserva.getIva()));
        preparedStmt.setString (6,String.valueOf(reserva.getCosto_final()));
	preparedStmt.setString (7,String.valueOf(reserva.getFecha_inicio()));
	preparedStmt.setString (8,String.valueOf(reserva.getFecha_final()));
        preparedStmt.setString (9,reserva.getEstado());
        preparedStmt.setString (10,String.valueOf(reserva.getCosto()));
        
        resultado=preparedStmt.executeUpdate();
	dbConection.close();
	preparedStmt.close();
	JOptionPane.showMessageDialog(null,"Se ha creado extitosamente");
	
	
	}catch(SQLException e){
		System.out.println(e.getMessage());
		JOptionPane.showMessageDialog(null, "Problemas con la creacion comuniquese con el administrador");
	
	
	}
	
	return resultado;
	
	}
	
	public  Reserva  read(int  id_reserva){

		Reserva  reservaRead  =  new  Reserva();
            try{
                dbConection  =  ConexionDB.getConexion();
                String  selectSQL  ="SELECT  *  FROM  Reserva  WHERE  id_reserva  =  ?"  ;
                preparedStmt  =  dbConection.prepareStatement(selectSQL);
                preparedStmt.setString  (1,  String.valueOf(id_reserva));
                resultSet  =  preparedStmt.executeQuery();
                while(resultSet.next()){
                reservaRead .setId_reserva(resultSet.getInt("id_reserva"));   
          
                reservaRead .setIva(resultSet.getInt("iva"));
                reservaRead .setCosto_final(resultSet.getInt("Costo_final"));
		reservaRead .setFecha_inicio(resultSet.getString("fecha_inicio"));
		reservaRead .setFecha_final(resultSet.getString("Fecha_final"));
                reservaRead .setEstado(resultSet.getString("Estado"));
               
                }
                
                dbConection.close();
                preparedStmt.close();

            }catch(SQLException  e){
                 System.out.println(e.getMessage());
                 JOptionPane.showMessageDialog(null,  "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
                 
				 }
                return  reservaRead ;
       
	   }
		
	
	public  int  update(Reserva  reserva){
		int  resultado  =0;
                try{
                         dbConection  =  ConexionDB.getConexion();
                         String  updateSQL  ="UPDATE  reserva  SET iva  = ?,Estado  =  ?, direccion = ?, telefono= ?"  +  "  WHERE  identificacion  =  ?";
                         preparedStmt  =  dbConection.prepareStatement(updateSQL);
                         preparedStmt.setString  (1,  String.valueOf(reserva.getIva()));
                         preparedStmt.setString  (2,  reserva.getEstado());
                         preparedStmt.setString  (3,  String.valueOf(reserva.getFecha_final()));
                         preparedStmt.setString  (4,  String.valueOf(reserva.getFecha_inicio()));
                         preparedStmt.setString  (5,  String.valueOf(reserva.getCosto_final()));
                         preparedStmt.setString  (9,  String.valueOf(reserva.getId_reserva()));
                         
                         resultado  =  preparedStmt.executeUpdate();
                         dbConection.close();
                         preparedStmt.close();

                       }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  con  la Actualizacion Comuniquese  con  el  Administrador");
                  }

                return  resultado;
       }

        
	public  int  delete(int  id_reserva){
                int  resultado=0;
                try{
                         dbConection  =  ConexionDB.getConexion();
                         String  deleteSQL  =  "DELETE  FROM  reserva  WHERE  id_reserva  =  ?";
                         preparedStmt  =  dbConection.prepareStatement(deleteSQL);
                         preparedStmt.setString(1,String.valueOf(id_reserva));
                         resultado  =  preparedStmt.executeUpdate();
                }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  con  el  Borrado    Comuniquese  con  el  Administrador");
                  }

                return  resultado;
       }

       public  List<Reserva>  ReservareadAll(){
         List<Reserva>  listreserva  =new  ArrayList();

                     try{
                        dbConection  =  ConexionDB.getConexion();
			String  selectSQL  ="SELECT  *  FROM  reserva , cliente, agencia, automovil "
                                + "where reserva.id_cliente = cliente.id_cliente  "
                                + "reserva.id_agencia=agencia.id_agencia"
                                + "reserva.idautomovil=automovil.idautomovil"  ;
                        preparedStmt  =  dbConection.prepareStatement(selectSQL);
                        resultSet  =  preparedStmt.executeQuery();
                        Reserva  ReservaAll;
                        Agencia agencia =new Agencia();
                        Cliente cliente =new Cliente();
                        
                     while(resultSet.next()){
                        ReservaAll  =new  Reserva();
                        ReservaAll.setId_reserva(resultSet.getInt("id_reserva"));
   
                        ReservaAll.setIva(resultSet.getInt("iva"));
                        ReservaAll.setCosto_final(resultSet.getInt("costo_final"));
                        ReservaAll.setFecha_inicio(resultSet.getString("fecha_inicio"));
                        ReservaAll.setFecha_final(resultSet.getString("fecha_final"));
                        ReservaAll.setEstado(resultSet.getString("estado"));
                        listreserva.add(ReservaAll);
                             }
                        dbConection.close(); 
                        preparedStmt.close();

                  }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
                  }
                return  listreserva;
       }

       
    
        
    
     
    
     
}
