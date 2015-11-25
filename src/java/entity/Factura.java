/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Objects;


/**
 *
 * @author anderson
 */
public class Factura {
   private int num_factura;
   private Cliente cliente;
   private Usuario usuario;
   private Date fecha;
   private boolean pendiente;
   private int mesa;

    public Factura() {
    }

    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.num_factura;
        hash = 79 * hash + Objects.hashCode(this.cliente);
        hash = 79 * hash + Objects.hashCode(this.usuario);
        hash = 79 * hash + Objects.hashCode(this.fecha);
        hash = 79 * hash + (this.pendiente ? 1 : 0);
        hash = 79 * hash + this.mesa;
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
        return "Factura{" + "num_factura=" + num_factura + ", cliente=" + cliente + ", usuario=" + usuario + ", fecha=" + fecha + ", pendiente=" + pendiente + ", mesa=" + mesa + '}';
    }
   
   
}
