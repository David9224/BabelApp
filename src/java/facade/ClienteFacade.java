/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Cliente;
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
public class ClienteFacade implements Serializable {

    private ConexionSql connection;

    /**
     * @param cedula
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Cliente buscarCliente(long cedula) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from cliente "
                    + "     where cedula = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            Cliente cliente = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCedula(rs.getInt(1));
                cliente.setNombres(rs.getString(2));
                cliente.setApellidos(rs.getString(3));
                cliente.setFecha_nacimiento(rs.getDate(4));
                cliente.setDireccion(rs.getString(5));
                cliente.setEmail(rs.getString(6));
                cliente.setTelefono(rs.getInt(7));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return cliente;
        } catch (Exception e) {
            throw new Exception("Error Buscar Cliente: " + e.getMessage());
        }
    }

    public Cliente crearCliente(Cliente cliente) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO Cliente values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cliente.getCedula());
            stmt.setString(2, cliente.getNombres().toUpperCase());
            stmt.setString(3, cliente.getApellidos().toUpperCase());
            stmt.setDate(4, cliente.getFecha_nacimiento());
            stmt.setString(5, cliente.getDireccion().toUpperCase());
            stmt.setString(6, cliente.getEmail());
            stmt.setLong(7, cliente.getTelefono());
            stmt.execute();
            stmt.close();
            conexion.close();

            return cliente;
        } catch (Exception e) {
            throw new Exception("Error Crear Cliente: " + e.toString());
        }
    }

    public void updateCliente(Cliente cliente) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update Cliente set  "
                    + "     nombres = ?, apellidos = ?,fecha_nacimiento = ?, direccion = ?,email = ?,telefono = ? "
                    + "     where cedula = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, cliente.getNombres().toUpperCase());
            stmt.setString(2, cliente.getApellidos().toUpperCase());
            stmt.setDate(3,  cliente.getFecha_nacimiento());
            stmt.setString(4, cliente.getDireccion().toUpperCase());
            stmt.setString(5, cliente.getEmail());
            stmt.setLong(6, cliente.getTelefono());
            stmt.setLong(7, cliente.getCedula());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Cliente: " + e.toString());
        }
    }

    public void borrarCliente(long cedula) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from Cliente "
                    + "     where CEDULA =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, cedula);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete Cliente " + e.toString());
        }
    }

    public List<Cliente> getAllClientes() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from Cliente ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Cliente cliente = null;
            List<Cliente> listaCliente = new ArrayList<>();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCedula(rs.getInt(1));
                cliente.setNombres(rs.getString(2));
                cliente.setApellidos(rs.getString(3));
                cliente.setFecha_nacimiento(rs.getDate(4));
                cliente.setDireccion(rs.getString(5));
                cliente.setEmail(rs.getString(6));
                cliente.setTelefono(rs.getInt(7));
                listaCliente.add(cliente);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaCliente;
        } catch (Exception e) {
            throw new Exception("Error getAllClientes: " + e.toString());
        }
    }

}
