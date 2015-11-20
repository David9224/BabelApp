/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;
import entity.Usuarios;
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

    private ConexionSql connection;

    /**
     * @param cedula
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Usuarios buscarUsuario(int cedula) throws Exception{
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from USUARIOS "
                    + "     where cedula = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            Usuarios usuarios = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios = new Usuarios();
                usuarios.setCedula(rs.getInt(1));
                usuarios.setNombres(rs.getString(2));
                usuarios.setApellidos(rs.getString(3));
                usuarios.setDireccion(rs.getString(4));
                usuarios.setTelefono(rs.getInt(5));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return usuarios;
        } catch (Exception e) {
            throw new Exception("Error Buscar Usuario: " +e.getMessage());
        }
    }

    public Usuarios crearUsuario(Usuarios usuarios) throws Exception {
        try {
            
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO Usuarios values (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, usuarios.getCedula());
            stmt.setString(2, usuarios.getNombres());
            stmt.setString(3, usuarios.getApellidos());
            stmt.setString(4, usuarios.getDireccion());
            stmt.setInt(5, usuarios.getTelefono());

            stmt.executeUpdate();
            stmt.close();
            conexion.close();

            return usuarios;
        } catch (Exception e) {
            throw new Exception("Error Crear Usuario: " + e.toString());
        }
    }

    public void updateUsuario(Usuarios usuario) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update usuarios set  "
                    + "     nombres=?, apellidos=?, direccion=?,telefono=? "
                    + "     where cedula = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, usuario.getNombres());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getDireccion());
            stmt.setInt(4, usuario.getTelefono());
             stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
         throw new Exception("Error update Usuario: " + e.toString());
        }
    }

    public void borrarUsuario(int cedula) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from usuarios "
                    + "     where CEDULA =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete usuario " + e.toString());
        }
    }

    public List<Usuarios> getAllUsuarios() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from usuarios ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Usuarios usuarios = null;
            List<Usuarios> listaUsuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios = new Usuarios();
                usuarios.setCedula(rs.getInt(1));
                usuarios.setNombres(rs.getString(2));
                usuarios.setApellidos(rs.getString(3));
                usuarios.setDireccion(rs.getString(4));
                usuarios.setTelefono(rs.getInt(5));
                listaUsuarios.add(usuarios);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaUsuarios;
        } catch (Exception e) {
             throw new Exception("Error getAllUsuarios: " + e.toString());
        }
    }

}
