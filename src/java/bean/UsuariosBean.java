/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Roles;
import entity.Usuarios;
import facade.AccesosUsuariosFacade;
import facade.RolesFacade;
import facade.UsuariosFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utility.Encriptar;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class UsuariosBean implements Serializable {

    private Usuarios usuarios;
    private Usuarios usuariosSelect;
    private UsuariosFacade usuariosFacade;
    private List<Usuarios> listaUsuarios;
    private List<Usuarios> listaUsuariosFiltrados;
    //Roles
    private String idRol;
    private String password;
    private RolesFacade rolesFacade;
    private List<Roles> lstRoles;
    //Accesos Usuarios
    private AccesosUsuariosFacade accesoFacade;

    public UsuariosBean() {
        usuarios = new Usuarios();
        usuariosSelect = new Usuarios();
        listaUsuariosFiltrados = new ArrayList<>();
        usuariosFacade = new UsuariosFacade();
        //Roles
        rolesFacade = new RolesFacade();
        lstRoles = new ArrayList<>();
        //Accesos Usuarios
        accesoFacade = new AccesosUsuariosFacade();
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

    public List<Usuarios> getListaUsuarios() throws Exception {
        listaUsuarios = usuariosFacade.getAllUsuarios();
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

    public Usuarios getUsuariosSelect() {
        return usuariosSelect;
    }

    public void setUsuariosSelect(Usuarios usuariosSelect) {
        this.usuariosSelect = usuariosSelect;
    }

    public List<Roles> getLstRoles() {
        lstRoles = rolesFacade.getAllRoles();
        return lstRoles;
    }

    public void setLstRoles(List<Roles> lstRoles) {
        this.lstRoles = lstRoles;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public void crearUsuario() throws Exception {
        String passEncriptado;
        FacesMessage msg = null;
        try {
            if (usuariosFacade.buscarUsuario(usuarios.getCedula()) == null) {
                usuariosFacade.crearUsuario(usuarios);
                password = "" + usuarios.getCedula();
                passEncriptado = new Encriptar().Encriptar(password.substring(password.length() - 4, password.length()));
                accesoFacade.crearAccesoUsuario(Integer.parseInt(idRol), usuarios.getCedula(), passEncriptado);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                usuariosFacade.updateUsuario(usuarios);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Editado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la operacion");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        usuarios = new Usuarios();
        RequestContext.getCurrentInstance().update("form1");
    }

    public void eliminarUsuario(Usuarios u) {
        FacesMessage msg = null;
        try {
            usuariosFacade.borrarUsuario(u.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("table");
        } catch (Exception ex) {
            System.out.println("No se pudo eliminar " + ex.toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect() {
        usuarios = usuariosSelect;
    }
}
