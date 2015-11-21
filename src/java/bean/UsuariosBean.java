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
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class UsuariosBean implements Serializable {

    private Usuarios usuarios;
    private UsuariosFacade usuariosFacade;
    private List<Usuarios> listaUsuarios;
    private List<Usuarios> listaUsuariosFiltrados;

    @PostConstruct
    public void init() {
        try {
            usuarios = new Usuarios();
            listaUsuariosFiltrados = new ArrayList<>();
            usuariosFacade = new UsuariosFacade();
            listaUsuarios = usuariosFacade.getAllUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Usuarios> getListaUsuariosFiltrados() {
        return listaUsuariosFiltrados;
    }

    public void setListaUsuariosFiltrados(List<Usuarios> listaUsuariosFiltrados) {
        this.listaUsuariosFiltrados = listaUsuariosFiltrados;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public List<Usuarios> getListaUsuarios() {
        System.out.println(listaUsuarios.size());
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public void eliminarUsuario(Usuarios u) {
        FacesMessage msg = null;
        try {
            System.out.println("aaaaa");
            usuariosFacade.borrarUsuario(u.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            buscarAllUsuarios();
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void buscarAllUsuarios() {
        FacesMessage msg = null;
        try {
            listaUsuarios = usuariosFacade.getAllUsuarios();

        } catch (Exception ex) {

            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            usuariosFacade.updateUsuario((Usuarios) event.getObject());
            FacesMessage msg = new FacesMessage("Usuario editado", Integer.toString(((Usuarios) event.getObject()).getCedula()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage("Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editacion Cancelada", Integer.toString(((Usuarios) event.getObject()).getCedula()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
