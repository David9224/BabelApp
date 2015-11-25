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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import utility.Encriptar;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private Long cedula;
    private String password;
    private boolean logeado = false;
    private AccesosUsuarios acceso;
    private AccesosUsuariosFacade accesoFacade;
    private RolesFacade rolesFacade;
    private Encriptar encripta;

    @PostConstruct
    public void init() {
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
                String passEnc = encripta.Encriptar(password);
                System.out.println("" + passEnc);
                System.out.println("" + encripta.Desencriptar(passEnc));
                System.out.println("bd_____ "+ acceso.getAcContra());
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
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", rol);
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

    public long getusuario() {
        return acceso.getAcCedu().getCedula();
    }

}
