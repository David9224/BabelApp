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
public class Roles implements Serializable {

    private int idRol;
    private String nombreRol;
    private String descripcion;

    public int getIdRol() {
        return idRol;
    }

    public Roles() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.idRol;
        hash = 37 * hash + Objects.hashCode(this.nombreRol);
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
        final Roles other = (Roles) obj;
        if (this.idRol != other.idRol) {
            return false;
        }
        if (!Objects.equals(this.nombreRol, other.nombreRol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Roles{" + "idRol=" + idRol + ", nombreRol=" + nombreRol + ", descripcion=" + descripcion + '}';
    }
    

}
