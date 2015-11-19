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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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
        String passEnc = encripta.Encriptar(password);
        System.out.println("" + passEnc);
        System.out.println("" + encripta.Desencriptar(passEnc));
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (cedula != null) {
            acceso = accesoFacade.getAcceso(cedula);
            if (acceso != null) {
                if (password.equalsIgnoreCase(acceso.getAcContra())) {
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
            String rol = rolesFacade.getRoles(acceso.getAcRol()).getNombre();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoginBean", this);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", rol);
            context.addCallbackParam("view", rol + "/index.xhtml");
        }
    }

    public void logout() {
        logeado = false;
        System.out.println("logout");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Object session = externalContext.getSession(false);
        HttpSession httpSession = (HttpSession) session;
        httpSession.invalidate();
        externalContext.getSessionMap().clear();
    }

    public void redirect() {
        if (logeado) {
            try {
                String rol = rolesFacade.getRoles(acceso.getAcRol()).getNombre();
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(rol+"/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
