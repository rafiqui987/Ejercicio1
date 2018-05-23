/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.vo.Cliente;
import utilidades.ConexionDB;

public class Cliente_dao {
    
    private Connection dbConection;
     private PreparedStatement preparedStmt;
     private  ResultSet  resultSet;

	public Cliente_dao(){
	dbConection=null;
	preparedStmt=null;
	resultSet=null;
	
	}

	public  int creat(Cliente cliente){
	int resultado=0;
	try{
	dbConection = ConexionDB.getConexion();
	String insertSQL = "INSERT INTO cliente (id_cliente, identificacion, nombre, apellido, direccion, telefono)"+"VALUES(?,?,?,?,?,?)";
	preparedStmt= dbConection.prepareStatement(insertSQL);
	preparedStmt.setString (1,String.valueOf(cliente.getId_cliente()));
	preparedStmt.setString (2,String.valueOf(cliente.getIdentificacion()));
	preparedStmt.setString (3,cliente.getNombre());
	preparedStmt.setString (4,cliente.getApellido());
        preparedStmt.setString (5,cliente.getDireccion());
        preparedStmt.setString (6,String.valueOf(cliente.getTelefono()));
		resultado=preparedStmt.executeUpdate();
	dbConection.close();
	preparedStmt.close();
	JOptionPane.showMessageDialog(null,"Se ha creado una nuevo cliente extitosamente");
	
	
	}catch(SQLException e){
		System.out.println(e.getMessage());
		JOptionPane.showMessageDialog(null, "Problemas con la creacion comuniquese con el administrador");
	
	
	}
	
	return resultado;
	
	}
	
	public  Cliente  read(int  identificacion){

		Cliente  ClienteRead  =  new  Cliente();
            try{
                dbConection  =  ConexionDB.getConexion();
                String  selectSQL  ="SELECT  *  FROM  cliente  WHERE  identificacion  =  ?"  ;
                preparedStmt  =  dbConection.prepareStatement(selectSQL);
                preparedStmt.setString  (1,  String.valueOf(identificacion));
                resultSet  =  preparedStmt.executeQuery();
                while(resultSet.next()){
                ClienteRead .setId_cliente(resultSet.getInt("id_cliente"));   
                ClienteRead .setIdentificacion(resultSet.getInt("identificacion"));
		ClienteRead .setNombre(resultSet.getString("nombre"));
		ClienteRead .setApellido(resultSet.getString("apellido"));
                ClienteRead .setDireccion(resultSet.getString("direccion"));
                ClienteRead .setTelefono(resultSet.getInt("telefono"));
               
                }
                
                dbConection.close();
                preparedStmt.close();

            }catch(SQLException  e){
                 System.out.println(e.getMessage());
                 JOptionPane.showMessageDialog(null,  "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
                 
				 }
                return  ClienteRead ;
       
	   }
		
	
	public  int  update(Cliente  cliente){
		int  resultado  =0;
                try{
                         dbConection  =  ConexionDB.getConexion();
                         String  updateSQL  ="UPDATE  cliente  SET nombre  = ?,apellido  =  ?, direccion = ?, telefono= ?"  +  "  WHERE  identificacion  =  ?";
                         preparedStmt  =  dbConection.prepareStatement(updateSQL);
                         preparedStmt.setString  (1,  cliente.getNombre());
                         preparedStmt.setString  (2,  cliente.getApellido());
                         preparedStmt.setString  (3,  cliente.getDireccion());
                         preparedStmt.setString  (4,  String.valueOf(cliente.getTelefono()));
                         preparedStmt.setString  (5,  String.valueOf(cliente.getIdentificacion()));
                         
                         resultado  =  preparedStmt.executeUpdate();
                         dbConection.close();
                         preparedStmt.close();

                       }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  con  la Actualizacion Comuniquese  con  el  Administrador");
                  }

                return  resultado;
       }

        
	public  int  delete(String  identificacion){
                int  resultado=0;
                try{
                         dbConection  =  ConexionDB.getConexion();
                         String  deleteSQL  =  "DELETE  FROM  cliente  WHERE  identificacion  =  ?";
                         preparedStmt  =  dbConection.prepareStatement(deleteSQL);
                         preparedStmt.setString(1,identificacion);
                         resultado  =  preparedStmt.executeUpdate();
                }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  con  el  Borrado    Comuniquese  con  el  Administrador");
                  }

                return  resultado;
       }

       public  List<Cliente>  readAll(){
         List<Cliente>  listCliente  =new  ArrayList();

                     try{
                        dbConection  =  ConexionDB.getConexion();
						String  selectSQL  ="SELECT  *  FROM  cliente"  ;
                        preparedStmt  =  dbConection.prepareStatement(selectSQL);
                        resultSet  =  preparedStmt.executeQuery();
                        Cliente  ClienteAll;
                     while(resultSet.next()){
                        ClienteAll  =new  Cliente();
                        ClienteAll.setId_cliente(resultSet.getInt("id_cliente"));
                        ClienteAll.setIdentificacion(resultSet.getInt("identificacion")); 
                        ClienteAll.setNombre(resultSet.getString("nombre"));
			ClienteAll.setApellido(resultSet.getString("apellido"));
			ClienteAll.setDireccion(resultSet.getString("direccion")); 
                        ClienteAll.setTelefono(resultSet.getInt("telefono")); 
                        listCliente.add(ClienteAll);
                             }
                        dbConection.close(); 
                        preparedStmt.close();

                  }catch(SQLException  e){
                           System.out.println(e.getMessage());
                           JOptionPane.showMessageDialog(null,  "Problemas  en  la  Consulta Comuniquese  con  el  Administrador");
                  }
                return  listCliente;
       }

        
        
    
}
