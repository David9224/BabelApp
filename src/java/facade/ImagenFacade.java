/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Cliente;
import entity.Imagen;
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
public class ImagenFacade implements Serializable {

    private ConexionSql connection;

    /**
     * @param cedula
     * @return
     * @Fecha 16/11/2015
     * @Observacion busca el usuario por cedula
     */
    public Imagen buscarImagen(int id) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from imagen "
                    + "     where id = ? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setInt(1, id);
            Imagen imagen = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                imagen = new Imagen();
                imagen.setId(rs.getInt(1));
                imagen.setArchivo(rs.getBytes(2));
                imagen.setExt(rs.getString(3));
                imagen.setNombre(rs.getString(4));
            }
            rs.close();
            stmt.close();
            conexion.close();

            return imagen;
        } catch (Exception e) {
            throw new Exception("Error Buscar Imagen: " + e.getMessage());
        }
    }

    public Imagen crearImagen(Imagen imagen) throws Exception {
        try {

            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = "INSERT INTO Imagen (archivo,ext,nombre) values (?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setBytes(1, imagen.getArchivo());
            stmt.setString(2, imagen.getExt());
            stmt.setString(3, imagen.getNombre());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               imagen.setId(generatedKeys.getInt(1));
            }
            }
            stmt.close();
            conexion.close();

            return imagen;
        } catch (Exception e) {
            throw new Exception("Error Crear Imagen: " + e.toString());
        }
    }

    public void updateImagen(Imagen imagen) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " update Imagen set  "
                    + "     archivo = ?, ext = ?,nombre = ?"
                    + "     where id = ?";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setBytes(1, imagen.getArchivo());
            stmt.setString(2, imagen.getExt());
            stmt.setString(3,  imagen.getNombre());
            stmt.setInt(4, imagen.getId());
            stmt.execute();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error update Imagen: " + e.toString());
        }
    }

    public void borrarImagen(int id) throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " delete from Imagen "
                    + "     where id =? ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            stmt.setLong(1, id);
            stmt.execute();

            stmt.close();
            conexion.close();
        } catch (Exception e) {
            throw new Exception("Error delete Imagen " + e.toString());
        }
    }

    public List<Imagen> getAllImagenes() throws Exception {
        try {
            connection = new ConexionSql();
            Connection conexion = connection.conexion();
            String SQL = " select * from Imagen ";
            PreparedStatement stmt = conexion.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            Imagen imagen = null;
            List<Imagen> listaImagenes = new ArrayList<>();
            while (rs.next()) {
               imagen = new Imagen();
                imagen.setId(rs.getInt(1));
                imagen.setArchivo(rs.getBytes(2));
                imagen.setExt(rs.getString(3));
                imagen.setNombre(rs.getString(4));
                listaImagenes.add(imagen);
            }
            rs.close();
            stmt.close();
            conexion.close();
            return listaImagenes;
        } catch (Exception e) {
            throw new Exception("Error getAllImagenes: " + e.toString());
        }
    }

}
