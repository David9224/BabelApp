/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Factura;
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
public class FacturaFacade implements Serializable {

    private ConexionSql connection;
    private ClienteFacade clienteFacade = new ClienteFacade();
    private UsuariosFacade usuariosFacade = new UsuariosFacade();
    /**
     * @param num_factura
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Factura buscarFactura(int num_factura) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from factura "
                    + "     where num_factura = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, num_factura);
            Factura factura = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                factura = new Factura();
                factura.setNum_factura(rs.getInt(1));
                factura.setCliente(clienteFacade.buscarCliente(rs.getInt(2)));
                factura.setUsuario(usuariosFacade.buscarUsuario(rs.getInt(3)));
                factura.setFecha(rs.getDate(4));
                factura.setPendiente(rs.getBoolean(5));
                factura.setMesa(rs.getInt(6));
            }
            rs.close();
            stmt.close();
            conexion.close();
            return factura;
        } catch (Exception e) {
            throw new Exception("Error Buscar Cliente: " + e.getMessage());
        }
    }

    public Factura crearFactura(Factura factura) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO factura values (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);           
            stmt.setLong(1, factura.getCliente().getCedula());
            stmt.setLong(2, factura.getUsuario().getCedula());
            stmt.setDate(3,factura.getFecha());
            stmt.setBoolean(4, factura.isPendiente());
            stmt.setInt(5, factura.getMesa());
            stmt.execute();
            stmt.close();
            conexion.close();

            return factura;
        } catch (Exception e) {
            throw new Exception("Error Crear Factura: " + e.toString());
        }
    }

    public void updateFactura(Factura factura) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update Factura set  "
                    + "     id_cliente = ?, id_usuario = ?,fecha = ?, pendiente = ?,mesa = ?"
                    + "     where num_factura = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(6, factura.getNum_factura());
            stmt.setLong(1, factura.getCliente().getCedula());
            stmt.setLong(2, factura.getUsuario().getCedula());
            stmt.setDate(3,factura.getFecha());
            stmt.setBoolean(4, factura.isPendiente());
            stmt.setInt(5, factura.getMesa());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Factura: " + e.toString());
        }
    }

    public void Factura(int num_factura) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from Factura "
                    + "     where num_factura =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, num_factura);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete Factura " + e.toString());
        }
    }

    public List<Factura> getAllFacturas() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from Factura ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Factura factura = null;
            List<Factura> listaFacturas = new ArrayList<>();
            while (rs.next()) {
                factura = new Factura();
                factura.setNum_factura(rs.getInt(1));
                factura.setCliente(clienteFacade.buscarCliente(rs.getInt(2)));
                factura.setUsuario(usuariosFacade.buscarUsuario(rs.getInt(3)));
                factura.setFecha(rs.getDate(4));
                factura.setPendiente(rs.getBoolean(5));
                factura.setMesa(rs.getInt(6));
                listaFacturas.add(factura);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaFacturas;
        } catch (Exception e) {
            throw new Exception("Error getAllFacturas: " + e.toString());
        }
    }

}
