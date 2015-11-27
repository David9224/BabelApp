/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.Cliente;
import facade.ClienteFacade;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class ClientesBean implements Serializable {

    private Cliente clientes;
    private Cliente clientesSelect;
    private ClienteFacade clientesFacade;
    private List<Cliente> listaClientes;
    private List<Cliente> listaClientesFiltrados;
    private String cancelar = "Limpiar";
    private String crearHeader = "Crear Cliente";
    private String crear = "Crear";
    private UploadedFile uploadedFile;
    private java.util.Date date;

    public ClientesBean() {
        clientes = new Cliente();
        clientesSelect = new Cliente();
        listaClientesFiltrados = new ArrayList<>();
        clientesFacade = new ClienteFacade();
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

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    public Cliente getClientesSelect() {
        return clientesSelect;
    }

    public void setClientesSelect(Cliente clientesSelect) {
        this.clientesSelect = clientesSelect;
    }

    public List<Cliente> getListaClientes() {
        try {
            listaClientes= clientesFacade.getAllClientes();
            return listaClientes;
        } catch (Exception ex) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
            return  listaClientes;
        }
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cliente> getListaClientesFiltrados() {
        return listaClientesFiltrados;
    }

    public void setListaClientesFiltrados(List<Cliente> listaClientesFiltrados) {
        this.listaClientesFiltrados = listaClientesFiltrados;
    }


    public void crearCliente() throws Exception {
      
        FacesMessage msg = null;
        try {
            if (clientesFacade.buscarCliente(clientes.getCedula()) == null) {
                clientesFacade.crearCliente(clientes);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                clientesFacade.updateCliente(clientes);
              
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Editado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la operacion: "+ ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        date=null;
        clientes = new Cliente();
        listaClientes = getListaClientes();
        crear = "Crear";
        crearHeader = "Crear Cliente";
        cancelar="Limpiar";
    }

    public void eliminarCliente(Cliente u) {
        FacesMessage msg = null;
        try {
            clientesFacade.borrarCliente(u.getCedula());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect() {
        clientes = clientesSelect;
        date= clientes.getFecha_nacimiento();
        crear = "Editar";
        crearHeader = "Editar Cliente";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void cancelar() {
        clientes = new Cliente();
        date= null;
        crear = "Crear";
        crearHeader = "Crear Cliente";
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
                clientes = new Cliente();
                clientes.setCedula(Integer.parseInt(cvs.get(0)));
                clientes.setNombres(cvs.get(1));
                clientes.setApellidos(cvs.get(2));
                clientes.setFecha_nacimiento(new java.sql.Date(date.getYear(), date.getMonth(), date.getDay()));
                clientes.setDireccion(cvs.get(4));
                clientes.setEmail(cvs.get(5));
                clientes.setTelefono(Integer.parseInt(cvs.get(6)));
                if (clientesFacade.buscarCliente(clientes.getCedula()) == null) {
                    clientesFacade.crearCliente(clientes);
                   
                } else {
                    clientesFacade.updateCliente(clientes);
                }
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se han importado todos los datos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        clientes.setFecha_nacimiento(new Date(date.getTime()));
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected",clientes.getFecha_nacimiento().toString()));
    }
     
}
