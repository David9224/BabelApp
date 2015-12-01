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
    private final CategoriaFacade categoriaFacade = new CategoriaFacade();
    private final ImagenFacade imagenFacade= new ImagenFacade();

    /**
     * @param id
     * @return
     * @throws java.lang.Exception
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
                producto.setId_categoria(categoriaFacade.buscarCategoria(rs.getInt(2)));
                producto.setNombre(rs.getString(3));
                producto.setPrecio(rs.getFloat(4));
                producto.setImagen(imagenFacade.buscarImagen(rs.getInt(5)));
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
            String SQL = "INSERT INTO producto (id_categoria,nombre,precio,imagen) values (?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, producto.getId_categoria().getId_categoria());
            stmt.setString(2, producto.getNombre().toUpperCase());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setInt(4, producto.getImagen().getId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                producto.setId_producto(generatedKeys.getInt(1));
            }
            }
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
                    + "     id_categoria = ?, nombre = ?, precio = ?,imagen = ?"
                    + "     where id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, producto.getId_categoria().getId_categoria());
            stmt.setString(2, producto.getNombre().toUpperCase());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setInt(4, producto.getImagen().getId());
            stmt.setInt(5, producto.getId_producto());
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update producto: " + e.toString());
        }
    }

    public void borrarProducto(int id) throws Exception{
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
            throw new Exception("Error delete producto " + e.toString());
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
                producto.setId_categoria(categoriaFacade.buscarCategoria(rs.getInt(2)));
                producto.setNombre(rs.getString(3));
                producto.setPrecio(rs.getFloat(4));
                producto.setImagen(imagenFacade.buscarImagen(rs.getInt(5)));
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
