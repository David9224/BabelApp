/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utility.ConexionSql;

/**
 *
 * @author David
 */
public class EmpresaFacade {
    
    
    public Empresa getEmpresa(){
        try {
            ConexionSql connection= new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " SELECT * FROM EMPRESAS";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            Empresa empresa = null;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                empresa = new Empresa();
                empresa.setNit(rs.getString(1));
                empresa.setNombreEmpresa(rs.getString(2));
                empresa.setDireccion(rs.getString(3));
                empresa.setTelefono(rs.getLong(4));
                empresa.setPaginaWeb(rs.getString(5));
                empresa.setCiudad(rs.getString(6));
            }
            rs.close();
            stmt.close();
            conexion.close();
            
            return empresa;
        } catch (Exception e) {
            System.out.println("Error getEmpresa "+ e.toString());
            return null;
        }
    }
}
