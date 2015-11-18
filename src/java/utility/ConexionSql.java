/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Fecha 15/11/2015
 * @author David
 */
public class ConexionSql implements Serializable{

    private Connection connection;

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/babel", "root", "admin1234");
            return connection;
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver no encontrado " + ex.toString());
            return null;
        } catch (SQLException ex) {
            System.out.println("Conexion Fallida " + ex.toString());
            return null;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("No se pudo cerrar conexion: " + e.toString());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
