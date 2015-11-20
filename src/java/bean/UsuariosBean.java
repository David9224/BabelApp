/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Usuarios;
import facade.UsuariosFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@SessionScoped
public class UsuariosBean implements Serializable {

    private Usuarios usuarios;
    private UsuariosFacade usuariosFacade;
    private List<Usuarios> listaUsuarios;

    @PostConstruct
    public void init() {
        usuarios = new Usuarios();
        listaUsuarios= new ArrayList<>();
        usuariosFacade = new UsuariosFacade();
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public UsuariosFacade getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(UsuariosFacade usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    public void crearUsuario() {
        FacesMessage msg = null;
        try {

            usuariosFacade.crearUsuario(usuarios);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Creado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        usuarios = new Usuarios();
    }

    public void editarUsuario() {
         FacesMessage msg = null;
        try {
            usuariosFacade.updateUsuario(usuarios);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Editado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        usuarios = new Usuarios();
    }

    public void buscarUsuario() {
        FacesMessage msg = null;
        try {
            
            usuarios = usuariosFacade.buscarUsuario(usuarios.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void eliminarUsuario() {
         FacesMessage msg = null;
        try {
            usuariosFacade.borrarUsuario(usuarios.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        usuarios=new Usuarios();
    }
    
     public void buscarAllUsuarios() {
         FacesMessage msg = null;
        try {
            listaUsuarios = usuariosFacade.getAllUsuarios();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Busqueda Exitosa");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
