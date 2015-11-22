/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.Usuarios;
import facade.UsuariosFacade;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
    private String cancelar = "Limpiar";
    private String crearHeader = "Crear Usuario";
    private String crear = "Crear";
    private UploadedFile uploadedFile;

    public UsuariosBean() {
        usuarios = new Usuarios();
        usuariosSelect = new Usuarios();
        listaUsuariosFiltrados = new ArrayList<>();
        usuariosFacade = new UsuariosFacade();
    }

    public String getCancelar() {
        return cancelar;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void setCancelar(String cancelar) {
        this.cancelar = cancelar;
    }

    public String getCrearHeader() {
        return crearHeader;
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

    public void crearUsuario() throws Exception {
        FacesMessage msg = null;
        try {
            if (usuariosFacade.buscarUsuario(usuarios.getCedula()) == null) {
                usuariosFacade.crearUsuario(usuarios);
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
        listaUsuarios = getListaUsuarios();
        crear = "Crear";
        crearHeader = "Crear Usuario";
    }

    public void eliminarUsuario(Usuarios u) {
        FacesMessage msg = null;
        try {
            usuariosFacade.borrarUsuario(u.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Usuario Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            System.out.println("No se pudo eliminar " + ex.toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect() {
        usuarios = usuariosSelect;
        crear = "Editar";
        crearHeader = "Editar Usuario";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void cancelar() {
        usuarios = new Usuarios();
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
                usuarios = new Usuarios();
                usuarios.setCedula(Integer.parseInt(cvs.get(0)));
                usuarios.setNombres(cvs.get(1));
                usuarios.setApellidos(cvs.get(2));
                usuarios.setDireccion(cvs.get(3));
                usuarios.setTelefono(Integer.parseInt(cvs.get(4)));
                if (usuariosFacade.buscarUsuario(usuarios.getCedula()) == null) {
                    usuariosFacade.crearUsuario(usuarios);

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
}
