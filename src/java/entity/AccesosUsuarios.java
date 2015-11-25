/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author David
 */
public class AccesosUsuarios implements Serializable {

    private int acCodi;
    private Usuario acCedu;
    private String acContra;
    private Roles acRol;

    public AccesosUsuarios() {
    }

    
    public int getAcCodi() {
        return acCodi;
    }

    public void setAcCodi(int acCodi) {
        this.acCodi = acCodi;
    }

    public String getAcContra() {
        return acContra;
    }

    public void setAcContra(String acContra) {
        this.acContra = acContra;
    }

    public Usuario getAcCedu() {
        return acCedu;
    }

    public void setAcCedu(Usuario acCedu) {
        this.acCedu = acCedu;
    }

    public Roles getAcRol() {
        return acRol;
    }

    public void setAcRol(Roles acRol) {
        this.acRol = acRol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.acCodi;
        hash = 67 * hash + Objects.hashCode(this.acCedu);
        hash = 67 * hash + Objects.hashCode(this.acContra);
        hash = 67 * hash + Objects.hashCode(this.acRol);
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
        final AccesosUsuarios other = (AccesosUsuarios) obj;
        if (this.acCodi != other.acCodi) {
            return false;
        }
        if (!Objects.equals(this.acCedu, other.acCedu)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccesosUsuarios{" + "acCodi=" + acCodi + ", acCedu=" + acCedu + ", acContra=" + acContra + ", acRol=" + acRol + '}';
    }
    
    

}
