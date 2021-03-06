/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 *
 * @author anderson
 */
public class Factura implements Serializable{
   private int num_factura;
   private Usuario usuario;
   private Date fecha;
   private Long cedula;
   private String nombre;
   private boolean pendiente;
   private int mesa;
   private Long totalRecibido;
   private Long total;

    public Factura() {
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

 
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public Long getTotalRecibido() {
        return totalRecibido;
    }

    public void setTotalRecibido(Long totalRecibido) {
        this.totalRecibido = totalRecibido;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.num_factura;
        hash = 89 * hash + Objects.hashCode(this.usuario);
        hash = 89 * hash + Objects.hashCode(this.fecha);
        hash = 89 * hash + Objects.hashCode(this.cedula);
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + (this.pendiente ? 1 : 0);
        hash = 89 * hash + this.mesa;
        hash = 89 * hash + Objects.hashCode(this.totalRecibido);
        hash = 89 * hash + Objects.hashCode(this.total);
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
        final Factura other = (Factura) obj;
        if (this.num_factura != other.num_factura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Factura{" + "num_factura=" + num_factura + ", usuario=" + usuario + ", fecha=" + fecha + ", cedula=" + cedula + ", nombre=" + nombre + ", pendiente=" + pendiente + ", mesa=" + mesa + ", totalRecibido=" + totalRecibido + ", total=" + total + '}';
    }
}
