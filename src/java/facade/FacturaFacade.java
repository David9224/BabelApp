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
    private final ClienteFacade clienteFacade = new ClienteFacade();
    private final UsuariosFacade usuariosFacade = new UsuariosFacade();

    /**
     * @param num_factura
     * @return
     * @throws java.lang.Exception
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
                factura.setCedula(rs.getLong(2));
                factura.setNombre(rs.getString(3));
                factura.setUsuario(usuariosFacade.buscarUsuario(rs.getInt(4)));
                factura.setFecha(rs.getDate(5));
                factura.setPendiente(rs.getBoolean(6));
                factura.setMesa(rs.getInt(7));
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
            String SQL = "INSERT INTO factura (id_cliente,nom_cliente,id_usuario,fecha,"
                    + "                         pendiente,mesa,totalRecibido, total) "
                    + "     values (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            if (factura.getCedula() != null) {
                stmt.setLong(1, factura.getCedula());
            } else {
                stmt.setString(1, null);
            }
            if (!factura.getNombre().equals("")) {
                stmt.setString(2, factura.getNombre().toUpperCase());
            } else {
                stmt.setString(2, null);
            }
            stmt.setLong(3, factura.getUsuario().getCedula());
            stmt.setBoolean(4, factura.isPendiente());
            stmt.setInt(5, factura.getMesa());
            stmt.setLong(6, factura.getTotalRecibido());
            stmt.setLong(7, factura.getTotal());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    factura.setNum_factura(generatedKeys.getInt(1));
                }
            }
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
                    + "     id_cliente = ?,nom_cliente = ?, id_usuario = ?, pendiente = ?,mesa = ?, "
                    + "     totalRecibido = ?, total = ?"
                    + "     where num_factura = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(8, factura.getNum_factura());
            if (factura.getCedula() != 0L) {
                stmt.setLong(1, factura.getCedula());
            } else {
                stmt.setString(1, null);
            }
            if (!factura.getNombre().equals("")) {
                stmt.setString(2, factura.getNombre().toUpperCase());
            } else {
                stmt.setString(2, null);
            }
            stmt.setLong(3, factura.getUsuario().getCedula());
            stmt.setBoolean(4, factura.isPendiente());
            stmt.setInt(5, factura.getMesa());
            stmt.setLong(6, factura.getTotalRecibido());
            stmt.setLong(7, factura.getTotal());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Factura: " + e.toString());
        }
    }

    public void deleteFactura(int num_factura) {
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
                factura.setCedula(rs.getLong(2));
                factura.setNombre(rs.getString(3));
                factura.setUsuario(usuariosFacade.buscarUsuario(rs.getInt(4)));
                factura.setFecha(new java.util.Date(rs.getDate(5).getTime()));
                factura.setPendiente(rs.getBoolean(6));
                factura.setMesa(rs.getInt(7));
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

    public List<Factura> getAllFacturasPendientes() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from Factura "
                    + "     where pendiente = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setBoolean(1, true);
            ResultSet rs = stmt.executeQuery();
            Factura factura = null;
            List<Factura> listaFacturas = new ArrayList<>();
            while (rs.next()) {
                factura = new Factura();
                factura.setNum_factura(rs.getInt(1));
                factura.setCedula(rs.getLong(2));
                factura.setNombre(rs.getString(3));
                factura.setUsuario(usuariosFacade.buscarUsuario(rs.getInt(4)));
                factura.setFecha(rs.getDate(5));
                factura.setPendiente(rs.getBoolean(6));
                factura.setMesa(rs.getInt(7));
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
