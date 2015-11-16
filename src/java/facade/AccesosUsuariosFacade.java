/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.AccesosUsuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utility.ConexionSql;

/**
 *@Fecha 16/11/2015
 * @author David
 */
public class AccesosUsuariosFacade {
    
    private ConexionSql connection;
    /**
     * @param cedula
     * @return 
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public AccesosUsuarios getAcceso(Long cedula){
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from ACCESOS_USUARIOS "
                    + "     where ac_cedu = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            AccesosUsuarios acceso = null;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                acceso = new AccesosUsuarios();
                acceso.setAcCodi(rs.getLong(1));
                acceso.setAcCedu(rs.getLong(2));
                acceso.setAcContra(rs.getString(3));
            }
            rs.close();
            stmt.close();
            conexion.close();
            
            return acceso;
        } catch (Exception e) {
            System.out.println("Error getAcceso "+e.toString());
            return null;
        }
    }
}
