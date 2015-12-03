/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Roles;
import facade.RolesFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class RolesBean implements Serializable {
    private Roles roles;
    private List<Roles> listaRoles;
    private RolesFacade rolesFacade;
    /**
     *
     */
    public RolesBean() {
        try {
         roles= new Roles();
         listaRoles= new ArrayList<>();
         rolesFacade= new RolesFacade();
        } catch (Exception ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<Roles> getListaRoles() {
        try {
            listaRoles= rolesFacade.getAllRoles();
        } catch (Exception ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaRoles;
    }

    public void setListaRoles(List<Roles> listaRoles) {
        this.listaRoles = listaRoles;
    }


}
