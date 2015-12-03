/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Roles;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utility.ConexionSql;

/**
 * @Fecha 16/11/2015
 * @author David
 */
public class RolesFacade implements Serializable {

    private final ConexionSql connection;

    public RolesFacade() {
        connection = new ConexionSql();
    }

    /**
     * @param id
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca el Rol por id
     */
    public Roles getRoles(int id) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " select * from roles "
                    + "     where id_rol = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            Roles rol = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rol = new Roles();
                rol.setIdRol(rs.getInt(1));
                rol.setNombreRol(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
            }
            rs.close();
            stmt.close();
            conexion.close();
            return rol;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error getRoles: " + e.toString());
        }
    }

    public Roles getRoles(String nombre_rol) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " select * from roles "
                    + "     where nombre_rol = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, nombre_rol);
            Roles rol = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rol = new Roles();
                rol.setIdRol(rs.getInt(1));
                rol.setNombreRol(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
            }
            rs.close();
            stmt.close();
            conexion.close();
            return rol;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error getRoles: " + e.toString());
        }
    }

    public List<Roles> getAllRoles() throws Exception {
        Connection conexion = connection.conexion();
        try {String SQL = " select * from roles ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            Roles rol = null;
            List<Roles> lstRoles = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rol = new Roles();
                rol.setIdRol(rs.getInt(1));
                rol.setNombreRol(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                lstRoles.add(rol);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return lstRoles;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error getAllRoles: " + e.toString());
        }
    }
}
