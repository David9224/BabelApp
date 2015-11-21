/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Categoria;
import facade.CategoriaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
    /**
     *
     */
    
    public CategoriaBean() {
         try {
             categoriaSelect= new Categoria();
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

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getListaCategorias() {
        try {
            listaCategorias = categoriaFacade.getAllCategorias();
            System.out.println(listaCategorias.size());
            return listaCategorias;
        } catch (Exception ex) {
             listaCategorias = new ArrayList<>();
             System.out.println(listaCategorias.size());
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

            categoriaFacade.crearCategoria(categoria);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Categoria Creada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        buscarAllCategorias();
        categoria = new Categoria();
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

            categoria = categoriaFacade.buscarCategoria(categoria.getId());
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
            System.out.println(u);
            categoriaFacade.borrarCategoria(u.getId());
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
    }

}
