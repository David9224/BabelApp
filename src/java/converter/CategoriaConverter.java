package converter;

import entity.Categoria;
import entity.Roles;
import facade.CategoriaFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import utility.JsfUtil;

@FacesConverter(value = "categoriaConverter")
public class CategoriaConverter implements Converter {

    private final CategoriaFacade categoriaFacade;
    
    public CategoriaConverter() {
        this.categoriaFacade= new CategoriaFacade();
    }
 
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      
        try {
            if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
                return null;
            }else{
                System.out.println(categoriaFacade.buscarCategoria(getKey(value)).toString());
                 return categoriaFacade.buscarCategoria(getKey(value));
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoriaConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Categoria) {
            Categoria o = (Categoria) object;
            System.out.println(o.getId_categoria());
            return getStringKey(o.getId_categoria());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Categoria.class.getName()});
            return null;
        }
    }

}
