/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;

/**
 *
 * @author anderson
 */
public class Detalle {
    private int num_detalle;
    private Factura factura;
    private Producto producto;
    private int cantidad;

    public Detalle() {
    }

    public int getNum_detalle() {
        return num_detalle;
    }

    public void setNum_detalle(int num_detalle) {
        this.num_detalle = num_detalle;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.num_detalle;
        hash = 37 * hash + Objects.hashCode(this.factura);
        hash = 37 * hash + Objects.hashCode(this.producto);
        hash = 37 * hash + this.cantidad;
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
        final Detalle other = (Detalle) obj;
        if (this.num_detalle != other.num_detalle) {
            return false;
        }
        if (!Objects.equals(this.factura, other.factura)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Detalle{" + "num_detalle=" + num_detalle + ", factura=" + factura + ", producto=" + producto + ", cantidad=" + cantidad + '}';
    }

    
}
