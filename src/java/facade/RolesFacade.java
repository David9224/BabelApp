/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.AccesosUsuarios;
import entity.Roles;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utility.ConexionSql;

/**
 *@Fecha 16/11/2015
 * @author David
 */
public class RolesFacade implements Serializable{
    
    private ConexionSql connection;
    /**
     * @param cedula
     * @return 
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Roles getRoles(int id){
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from roles "
                    + "     where id_rol = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            Roles rol = null;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                rol = new Roles();
                rol.setId(rs.getInt(1));
                rol.setNombre(rs.getString(2));
            }
            rs.close();
            stmt.close();
            conexion.close();
            
            return rol;
        } catch (Exception e) {
            System.out.println("Error getRoles "+e.toString());
            return null;
        }
    }
}
