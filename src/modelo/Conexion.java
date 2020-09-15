package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
	public static final String URL = "jdbc:mysql://localhost/creperia?useSSL=false&serverTimezone=UTC";
    public static final String usuario = "root"; 
    public static final String contraseña = "admin";
    
    
    public Connection getConnection(){
        Connection conexion = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(URL, usuario, contraseña);
           // JOptionPane.showMessageDialog(null, "Conexion Exitosa con DB");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return conexion;
    }   
}