/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.AccesosUsuarios;
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
public class AccesosUsuariosFacade implements Serializable {

    private ConexionSql connection;
    private final UsuariosFacade usuariosFacade = new UsuariosFacade();
    private final RolesFacade rolesFacade = new RolesFacade();

    /**
     * @param cedula
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public AccesosUsuarios getAccesoCedula(Long cedula) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from ACCESOS_USUARIOS "
                    + "     where ac_cedu = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            AccesosUsuarios acceso = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                acceso = new AccesosUsuarios();
                acceso.setAcCodi(rs.getInt(1));
                acceso.setAcCedu(usuariosFacade.buscarUsuario(rs.getInt(2)));
                acceso.setAcContra(rs.getString(3));
                acceso.setAcRol(rolesFacade.getRoles(rs.getInt(4)));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return acceso;
        } catch (Exception e) {
            System.out.println("Error getAcceso " + e.toString());
            return null;
        }
    }

    public AccesosUsuarios getAccesoCodigo(Long Ac_Codi) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from ACCESOS_USUARIOS "
                    + "     where Ac_Codi = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, Ac_Codi);
            AccesosUsuarios acceso = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                acceso = new AccesosUsuarios();
                acceso.setAcCodi(rs.getInt(1));
                acceso.setAcCedu(usuariosFacade.buscarUsuario(rs.getInt(2)));
                acceso.setAcContra(rs.getString(3));
                acceso.setAcRol(rolesFacade.getRoles(rs.getInt(4)));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return acceso;
        } catch (Exception e) {
            System.out.println("Error getAcceso " + e.toString());
            return null;
        }
    }

    public void crearAccesoUsuario(AccesosUsuarios accesosUsuarios) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " insert into accesos_usuarios (Ac_Cedu,Ac_Contra,Ac_Rol) values (?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setDouble(1, accesosUsuarios.getAcCedu().getCedula());
            stmt.setString(2, accesosUsuarios.getAcContra());
            stmt.setInt(3, accesosUsuarios.getAcRol().getIdRol());

            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error al crearAccesoUsuario: " + e.toString());
        }
    }

    public void updateAcceso(AccesosUsuarios acceso) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update accesos_usuarios set  "
                    + "     Ac_Cedu = ?, Ac_Contra = ? ,Ac_Rol = ?"
                    + "     where Ac_Codi = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, acceso.getAcCedu().getCedula());
            stmt.setString(2, acceso.getAcContra());
            stmt.setInt(3, acceso.getAcRol().getIdRol());
            stmt.setInt(4, acceso.getAcCodi());
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error update AccesoUsuario: " + e.toString());
        }
    }

    public void borrarAcceso(int id) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from accesos_usuarios "
                    + "     where Ac_Codi =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete AccesoUsuario " + e.toString());
        }
    }

    public List<AccesosUsuarios> getAllAcceso() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from accesos_usuarios ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            AccesosUsuarios acceso = null;
            List<AccesosUsuarios> listaAccesosUsuarios = new ArrayList<>();
            while (rs.next()) {
                acceso = new AccesosUsuarios();
                acceso.setAcCodi(rs.getInt(1));
                acceso.setAcCedu(usuariosFacade.buscarUsuario(rs.getInt(2)));
                acceso.setAcContra(rs.getString(3));
                acceso.setAcRol(rolesFacade.getRoles(rs.getInt(4)));
                listaAccesosUsuarios.add(acceso);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaAccesosUsuarios;
        } catch (Exception e) {
            throw new Exception("Error getAllAccesos: " + e.toString());
        }
    }

}
