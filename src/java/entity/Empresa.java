/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author David
 */
public class Empresa {
    private String nit;
    private String nombreEmpresa;
    private String direccion;
    private Long telefono;
    private String paginaWeb;
    private String ciudad;

    public String getNit() {
        return nit;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
