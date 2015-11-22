/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Producto;
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
public class ProductoFacade implements Serializable {

    private ConexionSql connection;

    /**
     * @param cedula
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Producto buscarProducto(int id) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from producto "
                    + "     where id_producto = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, id);
            Producto producto = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt(1));
                producto.setId_categoria(rs.getInt(2));
                producto.setNombre(rs.getString(3));
                producto.setPrecio(rs.getFloat(4));
                producto.setCantidadDisponible(rs.getInt(5));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return producto;
        } catch (Exception e) {
            throw new Exception("Error Buscar Producto: " + e.getMessage());
        }
    }

    public Producto crearProducto(Producto producto) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO producto values (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, producto.getId_producto());
            stmt.setInt(2, producto.getId_categoria());
            stmt.setString(3, producto.getNombre());
            stmt.setFloat(4, producto.getPrecio());
            stmt.setInt(5, producto.getCantidadDisponible());

            stmt.execute();
            stmt.close();
            conexion.close();

            return producto;
        } catch (Exception e) {
            throw new Exception("Error Crear Producto: " + e.toString());
        }
    }

    public void updateProducto(Producto producto) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update Producto set  "
                    + "     id_categoria = ?, nombre = ?, precio = ?,cantidad_disponible = ? "
                    + "     where id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, producto.getId_categoria());
            stmt.setString(2, producto.getNombre());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidadDisponible());
            stmt.setInt(5, producto.getId_producto());
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update producto: " + e.toString());
        }
    }

    public void borrarProducto(int id) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from producto "
                    + "     where id_producto =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, id);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete producto " + e.toString());
        }
    }

    public List<Producto> getAllProductos() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from producto ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Producto producto = null;
            List<Producto> listaProductos = new ArrayList<>();
            while (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt(1));
                producto.setId_categoria(rs.getInt(2));
                producto.setNombre(rs.getString(3));
                producto.setPrecio(rs.getFloat(4));
                producto.setCantidadDisponible(rs.getInt(5));
                listaProductos.add(producto);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaProductos;
        } catch (Exception e) {
            throw new Exception("Error getAllProductos: " + e.toString());
        }
    }

}
