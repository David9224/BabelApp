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

    private final ConexionSql connection;

    public EmpresaFacade() {
        connection = new ConexionSql();
    }

    /**
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca la empresa
     */
    public Empresa getEmpresa() throws Exception {
        Connection conexion = connection.conexion();
        try {
            String SQL = " SELECT * FROM EMPRESAS";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            Empresa empresa = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
            conexion.close();
            throw new Exception("Error getEmpresa " + e.getMessage());

        }
    }
}
