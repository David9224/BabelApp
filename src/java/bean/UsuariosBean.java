/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.AccesosUsuarios;
import entity.Roles;
import entity.Usuario;
import facade.AccesosUsuariosFacade;
import facade.RolesFacade;
import facade.UsuariosFacade;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import utility.Encriptar;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class UsuariosBean implements Serializable {

    private Usuario usuarios;
    private Usuario usuariosSelect;
    private UsuariosFacade usuariosFacade;
    private List<Usuario> listaUsuarios;
    private List<Usuario> listaUsuariosFiltrados;
    private Roles roles = new Roles();
    private String cancelar = "Limpiar";
    private String crearHeader = "Crear Usuario";
    private String crear = "Crear";
    private UploadedFile uploadedFile;
    private final RolesFacade rolesFacade;
    private final AccesosUsuariosFacade accesosUsuariosFacade;
    private java.util.Date date;

    public UsuariosBean() {
        usuarios = new Usuario();
        usuariosSelect = new Usuario();
        listaUsuariosFiltrados = new ArrayList<>();
        usuariosFacade = new UsuariosFacade();
        rolesFacade = new RolesFacade();
        accesosUsuariosFacade = new AccesosUsuariosFacade();
    }

    public java.util.Date getDate() {
        return  date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    
    public String getCancelar() {
        return cancelar;
    }

    public void setCancelar(String cancelar) {
        this.cancelar = cancelar;
    }

    public String getCrearHeader() {
        return crearHeader;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public void setCrearHeader(String crearHeader) {
        this.crearHeader = crearHeader;
    }

    public String getCrear() {
        return crear;
    }

    public void setCrear(String crear) {
        this.crear = crear;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<Usuario> getListaUsuariosFiltrados() {
        return listaUsuariosFiltrados;
    }

    public void setListaUsuariosFiltrados(List<Usuario> listaUsuariosFiltrados) {
        this.listaUsuariosFiltrados = listaUsuariosFiltrados;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public List<Usuario> getListaUsuarios() throws Exception {
        listaUsuarios = usuariosFacade.getAllUsuarios();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public UsuariosFacade getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(UsuariosFacade usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    public Usuario getUsuariosSelect() {
        return usuariosSelect;
    }

    public void setUsuariosSelect(Usuario usuariosSelect) {
        this.usuariosSelect = usuariosSelect;
    }

    public void crearUsuario() throws Exception {
        String passEncriptado;
        String pass;
        FacesMessage msg = null;
        try {
            if (usuariosFacade.buscarUsuario(usuarios.getCedula()) == null) {
                usuariosFacade.crearUsuario(usuarios);
                AccesosUsuarios accesosUsuarios;
                accesosUsuarios = new AccesosUsuarios();
                Encriptar encriptar = new Encriptar();
                pass = Long.toString(usuarios.getCedula());
                System.out.println(pass);
                passEncriptado = encriptar.Encriptar(pass.substring(pass.length() - 5));
                System.out.println(pass.substring(pass.length() - 4));
                accesosUsuarios.setAcCedu(usuarios);
                accesosUsuarios.setAcContra(passEncriptado);
                accesosUsuarios.setAcRol(roles);
                accesosUsuariosFacade.crearAccesoUsuario(accesosUsuarios);
                
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                usuariosFacade.updateUsuario(usuarios);
                AccesosUsuarios a = accesosUsuariosFacade.getAccesoCedula(usuarios.getCedula());
                a.setAcRol(roles);
                accesosUsuariosFacade.updateAcceso(a);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Editado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la operacion: "+ ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        date=null;
        usuarios = new Usuario();
        listaUsuarios = getListaUsuarios();
        crear = "Crear";
        crearHeader = "Crear Usuario";
        cancelar="Limpiar";
    }

    public void eliminarUsuario(Usuario u) {
        FacesMessage msg = null;
        try {
            usuariosFacade.borrarUsuario(u.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect() {
        usuarios = usuariosSelect;
        date= usuarios.getFecha_nacimiento();
        crear = "Editar";
        crearHeader = "Editar Usuario";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void cancelar() {
        usuarios = new Usuario();
        date= null;
        crear = "Crear";
        crearHeader = "Crear Usuario";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = null;
        try {
            CsvReader cvs;
            uploadedFile = event.getFile();
            cvs = new CsvReader(new InputStreamReader(uploadedFile.getInputstream()));
            cvs.readHeaders();
            while (cvs.readRecord()) {
                usuarios = new Usuario();
                usuarios.setCedula(Integer.parseInt(cvs.get(0)));
                usuarios.setNombres(cvs.get(1));
                usuarios.setApellidos(cvs.get(2));
                usuarios.setFecha_nacimiento(new java.sql.Date(date.getYear(), date.getMonth(), date.getDay()));
                usuarios.setDireccion(cvs.get(4));
                usuarios.setEmail(cvs.get(5));
                usuarios.setTelefono(Integer.parseInt(cvs.get(6)));
                roles = rolesFacade.getRoles(cvs.get(7));
                if (usuariosFacade.buscarUsuario(usuarios.getCedula()) == null) {
                    usuariosFacade.crearUsuario(usuarios);
                    AccesosUsuarios accesosUsuarios;
                    accesosUsuarios = new AccesosUsuarios();
                    Encriptar encriptar = new Encriptar();
                    String passEncriptado;
                    String pass;
                    pass = Long.toString(usuarios.getCedula());
                    passEncriptado = encriptar.Encriptar(pass.substring(pass.length() - 5));
                    accesosUsuarios.setAcCedu(usuarios);
                    accesosUsuarios.setAcContra(passEncriptado);
                    accesosUsuarios.setAcRol(roles);
                    accesosUsuariosFacade.crearAccesoUsuario(accesosUsuarios);
                } else {
                    usuariosFacade.updateUsuario(usuarios);
                }
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se han importado todos los datos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void resetPassword(Usuario usuario) {
        FacesMessage msg = null;
        try {
            AccesosUsuarios accesosUsuarios;
            accesosUsuarios = accesosUsuariosFacade.getAccesoCedula(usuario.getCedula());
            Encriptar encriptar = new Encriptar();
            String passEncriptado;
            String pass;
            pass = Long.toString(usuarios.getCedula());
            passEncriptado = encriptar.Encriptar(pass.substring(pass.length() - 5));
            accesosUsuarios.setAcContra(passEncriptado);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se han reseteado la contraseña");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        usuarios.setFecha_nacimiento(new Date(date.getTime()));
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected",usuarios.getFecha_nacimiento().toString()));
    }
     
}
