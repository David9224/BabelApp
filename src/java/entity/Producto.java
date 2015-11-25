/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Fecha 15/11/2015
 * @author David
 */
public class Producto implements Serializable {

    private int id_producto;
    private Categoria id_categoria;
    private String nombre;
    private float precio;

    public Producto() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public Categoria getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categoria id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id_producto;
        hash = 83 * hash + Objects.hashCode(this.id_categoria);
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Float.floatToIntBits(this.precio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (this.id_producto != other.id_producto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "id_producto=" + id_producto + ", id_categoria=" + id_categoria + ", nombre=" + nombre + ", precio=" + precio + '}';
    }

}
