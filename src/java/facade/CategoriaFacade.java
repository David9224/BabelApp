/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Categoria;
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
public class CategoriaFacade implements Serializable {

    private ConexionSql connection;

    /**
     * @param id
     * @return
     * @throws java.lang.Exception
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Categoria buscarCategoria(int id) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from categoria "
                    + "     where id_categoria = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            Categoria categoria = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categoria = new Categoria();
                categoria.setId_categoria(rs.getInt(1));
                categoria.setNombre(rs.getString(2));
                categoria.setDescripcion(rs.getString(3));

            }
            rs.close();
            stmt.close();
            conexion.close();

            return categoria;
        } catch (Exception e) {
            throw new Exception("Error Buscar categoria: " + e.getMessage());
        }
    }

    public Categoria crearCategoria(Categoria categoria) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO categoria (nombre,descripcion) values (?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.executeUpdate();
            stmt.close();
            conexion.close();

            return categoria;
        } catch (Exception e) {
            throw new Exception("Error Crear Categoria: " + e.toString());
        }
    }

    public void updateCategoria(Categoria categoria) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update categoria set  "
                    + "     nombre = ?, descripcion = ?"
                    + "     where id_categoria = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getId_categoria());

            stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Categoria: " + e.toString());
        }
    }

    public void borrarCategoria(int id) {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from categoria "
                    + "     where id_categoria =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            stmt.executeUpdate();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error delete categoria " + e.toString());
        }
    }

    public List<Categoria> getAllCategorias() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from categoria ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Categoria categoria=null;
            List<Categoria> listaCategorias = new ArrayList<>();
            while (rs.next()) {
                categoria = new Categoria();
                categoria.setId_categoria(rs.getInt(1));
                categoria.setNombre(rs.getString(2));
                categoria.setDescripcion(rs.getString(3));
                listaCategorias.add(categoria);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaCategorias;
        } catch (Exception e) {
            throw new Exception("Error getAllCategorias: " + e.toString());
        }
    }

}
