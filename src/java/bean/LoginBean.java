/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.AccesosUsuarios;
import facade.AccesosUsuariosFacade;
import facade.RolesFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import utility.Encriptar;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private Long cedula;
    private String password;
    private boolean logeado = false;
    private AccesosUsuarios acceso;
    private final AccesosUsuariosFacade accesoFacade;
    private final RolesFacade rolesFacade;
    private Encriptar encripta;

    public LoginBean() {
        accesoFacade = new AccesosUsuariosFacade();
        rolesFacade = new RolesFacade();
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public void login() throws Exception {
        encripta = new Encriptar();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (cedula != null) {
            acceso = accesoFacade.getAccesoCedula(new Long(cedula.toString()));
            if (acceso != null) {
                if (password.equals(encripta.Desencriptar(acceso.getAcContra()))) {
                    logeado = true;
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", "" + cedula);
                } else {
                    logeado = false;
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contraseña invalida");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no existe");
            }

        } else {
            logeado = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", logeado);
        if (logeado) {
            String rol = rolesFacade.getRoles(acceso.getAcRol().getIdRol()).getNombreRol();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoginBean", this);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", acceso);
            context.addCallbackParam("view", rol + "/index.xhtml");
        }
    }

    public void logout() {
        logeado = false;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        try {
            ec.redirect(ec.getRequestContextPath());
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirect() {
        if (logeado) {
            try {
                String rol = rolesFacade.getRoles(acceso.getAcRol().getIdRol()).getNombreRol();
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(rol + "/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getusuario() {
        AccesosUsuarios a = null;
        a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (a != null) {
            return Long.toString(a.getAcCedu().getCedula());
        } else {
            return "Usuario no autentificado";
        }
    }

    public boolean isLogueado() {
        AccesosUsuarios a = null;
        a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return a != null;
    }

    public boolean isAdmin() {
        AccesosUsuarios a = null;
        a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return a.getAcRol().getNombreRol().equals("admin");
    }

    public boolean isCajero() {
        AccesosUsuarios a = null;
        a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return a.getAcRol().getNombreRol().equals("cajero");
    }
}
