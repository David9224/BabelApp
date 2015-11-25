/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.Categoria;
import facade.CategoriaFacade;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {

    private Categoria categoria;
    private CategoriaFacade categoriaFacade;
    private List<Categoria> listaCategorias;
    private List<Categoria> listaCategoriaFiltrados;
    private Categoria categoriaSelect;
    private String crearHeader = "Crear Categoria";
    private String crear = "Crear";
    private String cancelar = "Limpiar";
    private UploadedFile uploadedFile;

    /**
     *
     */
    public CategoriaBean() {
        try {
            categoriaSelect = new Categoria();
            categoria = new Categoria();
            listaCategoriaFiltrados = new ArrayList<>();
            categoriaFacade = new CategoriaFacade();
        } catch (Exception ex) {
            Logger.getLogger(CategoriaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Categoria getCategoria() {
        return categoria;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getCancelar() {
        return cancelar;
    }

    public void setCancelar(String cancelar) {
        this.cancelar = cancelar;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoriaSelect() {
        return categoriaSelect;
    }

    public void setCategoriaSelect(Categoria categoriaSelect) {
        this.categoriaSelect = categoriaSelect;
    }

    public String getCrearHeader() {
        return crearHeader;
    }

    public String getCrear() {
        return crear;
    }

    public List<Categoria> getListaCategorias() {
        try {
            listaCategorias = categoriaFacade.getAllCategorias();
            return listaCategorias;
        } catch (Exception ex) {
            listaCategorias = new ArrayList<>();
            return listaCategorias;
        }
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Categoria> getListaCategoriaFiltrados() {
        return listaCategoriaFiltrados;
    }

    public void setListaCategoriaFiltrados(List<Categoria> listaCategoriaFiltrados) {
        this.listaCategoriaFiltrados = listaCategoriaFiltrados;
    }

    public void crearCategoria() {
        FacesMessage msg = null;
        try {
            if (categoriaFacade.buscarCategoria(categoria.getId_categoria()) == null) {
                categoriaFacade.crearCategoria(categoria);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Creada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                categoriaFacade.updateCategoria(categoria);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Editada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la operacion");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        buscarAllCategorias();
        categoria = new Categoria();
        crear = "Crear";
        crearHeader = "Crear Categoria";
        cancelar="Limpiar";
    }

    public void editarCategoria() {
        FacesMessage msg = null;
        try {
            categoriaFacade.updateCategoria(categoria);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Editada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        categoria = new Categoria();
    }

    public void buscarCategoria() {
        FacesMessage msg = null;
        try {

            categoria = categoriaFacade.buscarCategoria(categoria.getId_categoria());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Encontrada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void eliminarCategoria(Categoria u) {
        FacesMessage msg = null;
        try {
            categoriaFacade.borrarCategoria(u.getId_categoria());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Eliminada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            buscarAllCategorias();
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void buscarAllCategorias() {
        FacesMessage msg = null;
        try {
            listaCategorias = categoriaFacade.getAllCategorias();

        } catch (Exception ex) {

            Logger.getLogger(CategoriaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect() {
        categoria = categoriaSelect;
        crear = "Editar";
        crearHeader = "Editar Categoria";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void cancelar() {
        categoria = new Categoria();
        crear = "Crear";
        crearHeader = "Crear Categoria";
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
                categoria = new Categoria();      
                categoria.setNombre(cvs.get(1));
                categoria.setDescripcion(cvs.get(2));
                if (categoriaFacade.buscarCategoria(categoria.getId_categoria()) == null) {
                    categoriaFacade.crearCategoria(categoria);
                } else {
                    categoriaFacade.updateCategoria(categoria);
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
