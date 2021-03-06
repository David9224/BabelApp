/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Usuario;
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
public class UsuariosFacade implements Serializable {

    private final ConexionSql connection;

    public UsuariosFacade() {
        connection = new ConexionSql();
    }

    /**
     * @param cedula
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Usuario buscarUsuario(long cedula) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " select * from USUARIOS "
                    + "     where cedula = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            Usuario usuarios = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios = new Usuario();
                usuarios.setCedula(rs.getLong(1));
                usuarios.setNombres(rs.getString(2));
                usuarios.setApellidos(rs.getString(3));
                usuarios.setFecha_nacimiento(rs.getDate(4));
                usuarios.setDireccion(rs.getString(5));
                usuarios.setEmail(rs.getString(6));
                usuarios.setTelefono(rs.getLong(7));
            }
            rs.close();
            stmt.close();
            conexion.close();
            return usuarios;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error Buscar Usuario: " + e.toString());
        }
    }

    public Usuario crearUsuario(Usuario usuarios) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = "INSERT INTO Usuarios values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, usuarios.getCedula());
            stmt.setString(2, usuarios.getNombres().toUpperCase());
            stmt.setString(3, usuarios.getApellidos().toUpperCase());
            stmt.setDate(4, usuarios.getFecha_nacimiento());
            stmt.setString(5, usuarios.getDireccion().toUpperCase());
            stmt.setString(6, usuarios.getEmail());
            stmt.setLong(7, usuarios.getTelefono());
            stmt.execute();
            stmt.close();
            conexion.close();
            return usuarios;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error Crear Usuario: " + e.toString());
        }
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " update usuarios set  "
                    + "     nombres = ?, apellidos = ?,fecha_nacimiento = ?, direccion = ?,email = ?,telefono = ? "
                    + "     where cedula = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, usuario.getNombres().toUpperCase());
            stmt.setString(2, usuario.getApellidos().toUpperCase());
            stmt.setDate(3, usuario.getFecha_nacimiento());
            stmt.setString(4, usuario.getDireccion().toUpperCase());
            stmt.setString(5, usuario.getEmail());
            stmt.setLong(6, usuario.getTelefono());
            stmt.setLong(7, usuario.getCedula());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error update Usuario: " + e.toString());
        }
    }

    public void borrarUsuario(long cedula) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " delete from usuarios "
                    + "     where CEDULA =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error Delete Usuario: " + e.toString());
        }
    }

    public List<Usuario> getAllUsuarios() throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " select * from usuarios ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Usuario usuarios = null;
            List<Usuario> listaUsuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios = new Usuario();
                usuarios.setCedula(rs.getLong(1));
                usuarios.setNombres(rs.getString(2));
                usuarios.setApellidos(rs.getString(3));
                usuarios.setFecha_nacimiento(rs.getDate(4));
                usuarios.setDireccion(rs.getString(5));
                usuarios.setEmail(rs.getString(6));
                usuarios.setTelefono(rs.getLong(7));
                listaUsuarios.add(usuarios);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaUsuarios;
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error getAllUsuarios: " + e.toString());
        }
    }

}