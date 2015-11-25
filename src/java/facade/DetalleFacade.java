/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Detalle;
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
public class DetalleFacade implements Serializable {

    private ConexionSql connection;
    private final FacturaFacade facturaFacade = new FacturaFacade();
    private final ProductoFacade productoFacade = new ProductoFacade();

    /**
     * @param num_detalle
     * @param id_producto
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Detalle buscarDetalle(int num_detalle, int id_producto) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from detalle "
                    + "     where num_detalle = ? and id_producto =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, num_detalle);
            stmt.setInt(1, id_producto);
            Detalle detalle = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                detalle = new Detalle();
                detalle.setNum_detalle(rs.getInt(1));
                detalle.setFactura(facturaFacade.buscarFactura(rs.getInt(2)));
                detalle.setProducto(productoFacade.buscarProducto(rs.getInt(3)));
                detalle.setCantidad(rs.getInt(4));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return detalle;
        } catch (Exception e) {
            throw new Exception("Error Buscar Detalle: " + e.getMessage());
        }
    }

    public Detalle crearDetalle(Detalle detalle) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO Detalle values (?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, detalle.getFactura().getNum_factura());
            stmt.setInt(2, detalle.getProducto().getId_producto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setFloat(4, detalle.getPrecio());
            stmt.execute();
            stmt.close();
            conexion.close();

            return detalle;
        } catch (Exception e) {
            throw new Exception("Error Crear Detalle: " + e.toString());
        }
    }

    public void updateDetalle(Detalle detalle) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update Detalle set  "
                    + "     id_factura = ?, id_producto = ?,cantidad = ?, precio = ?"
                    + "     where num_detalle = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, detalle.getFactura().getNum_factura());
            stmt.setInt(2, detalle.getProducto().getId_producto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setFloat(4, detalle.getPrecio());
            stmt.setInt(5, detalle.getNum_detalle());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Detalle: " + e.toString());
        }
    }

    public void borrarDetalle(int num_detalle) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from Detalle "
                    + "     where num_detalle =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, num_detalle);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete Detalle " + e.toString());
        }
    }

    public List<Detalle> getAllDetalles() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from Detalle ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Detalle detalle = null;
            List<Detalle> listaDetalles = new ArrayList<>();
            while (rs.next()) {
                detalle = new Detalle();
                detalle.setNum_detalle(rs.getInt(1));
                detalle.setFactura(facturaFacade.buscarFactura(rs.getInt(2)));
                detalle.setProducto(productoFacade.buscarProducto(rs.getInt(3)));
                detalle.setCantidad(rs.getInt(4));
                listaDetalles.add(detalle);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaDetalles;
        } catch (Exception e) {
            throw new Exception("Error getAllDetalles: " + e.toString());
        }
    }

}
