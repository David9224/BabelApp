/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author David
 */
public class AccesosUsuarios implements Serializable{
    private Long acCodi;
    private Long acCedu;
    private String acContra;
    private int acRol;


    public Long getAcCodi() {
        return acCodi;
    }

    public void setAcCodi(Long acCodi) {
        this.acCodi = acCodi;
    }

    public Long getAcCedu() {
        return acCedu;
    }

    public void setAcCedu(Long acCedu) {
        this.acCedu = acCedu;
    }

    public String getAcContra() {
        return acContra;
    }

    public void setAcContra(String acContra) {
        this.acContra = acContra;
    }
     public int getAcRol() {
        return acRol;
    }

    public void setAcRol(int acRol) {
        this.acRol=acRol;
    }
    
}
