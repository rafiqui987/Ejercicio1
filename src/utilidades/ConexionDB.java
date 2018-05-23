/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.derby.client.am.Connection;

public class ConexionDB {
    private static Connection dbConnection= null;
    private static Driver driver= new org.apache.derby.jdbc.ClientDriver();
    private static String dburl= "jdbc:derby://localhost:1527/Reserva";
    private static String usuario = "root";
    private static String contraseña= "toor";
    
    public static Connection getConexion() throws SQLException {
        
        DriverManager.registerDriver(driver);
        dbConnection = (Connection) DriverManager.getConnection(dburl, usuario, contraseña);
        return dbConnection;
        
        
    }
    
    public static void desconectar(){
        
        dbConnection=null;
    }
    
}
    
  

class TestConexciondb {
    
    private static Connection dbConnection;
    
public static void main (String[] args) throws SQLException{
    
    dbConnection = ConexionDB.getConexion();
    if (dbConnection != null){
        
        JOptionPane.showMessageDialog(null, "Conexion realizada corectamente");
        
        }
    }
}
