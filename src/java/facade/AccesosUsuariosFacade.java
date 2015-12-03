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

    private final ConexionSql connection;
    private final UsuariosFacade usuariosFacade;
    private final RolesFacade rolesFacade;

    public AccesosUsuariosFacade() {
        usuariosFacade = new UsuariosFacade();
        rolesFacade = new RolesFacade();
        connection = new ConexionSql();
    }

    /**
     * @param cedula
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca el acceso por cedula
     */
    public AccesosUsuarios getAccesoCedula(Long cedula) throws Exception {
        Connection conexion = connection.conexion();
        try {
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
            conexion.close();
            throw new Exception("Error getAcceso " + e.toString());
        }
    }

    public AccesosUsuarios getAccesoCodigo(Long Ac_Codi) throws Exception {
        Connection conexion = connection.conexion();
        try {
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
            conexion.close();
            throw new Exception("Error getAcceso " + e.toString());
        }
    }

    public void crearAccesoUsuario(AccesosUsuarios accesosUsuarios) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " insert into accesos_usuarios (Ac_Cedu,Ac_Contra,Ac_Rol) values (?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setDouble(1, accesosUsuarios.getAcCedu().getCedula());
            stmt.setString(2, accesosUsuarios.getAcContra());
            stmt.setInt(3, accesosUsuarios.getAcRol().getIdRol());

            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error al crearAccesoUsuario: " + e.toString());
        }
    }

    public void updateAcceso(AccesosUsuarios acceso) throws Exception {
        Connection conexion = connection.conexion();
        try {
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
            conexion.close();
            throw new Exception("Error update AccesoUsuario: " + e.toString());
        }
    }

    public void borrarAcceso(int id) throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " delete from accesos_usuarios "
                    + "     where Ac_Codi =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            conexion.close();
            throw new Exception("Error delete AccesoUsuario " + e.toString());
        }
    }

    public List<AccesosUsuarios> getAllAcceso() throws Exception {
        Connection conexion = connection.conexion();
        try {
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
            conexion.close();
            throw new Exception("Error getAllAccesos: " + e.toString());
        }
    }

}
