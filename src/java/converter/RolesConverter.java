package converter;

import entity.Roles;
import facade.RolesFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import utility.JsfUtil;

@FacesConverter(value = "rolesConverter")
public class RolesConverter implements Converter {
 
    private RolesFacade rolesFacade;
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        rolesFacade= new RolesFacade();
        try {
            if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
                return null;
            }else{
                 return rolesFacade.getRoles(getKey(value));
            }
        } catch (Exception ex) {
            Logger.getLogger(RolesConverter.class.getName()).log(Level.SEVERE, null, ex);
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
        if (object instanceof Roles) {
            Roles o = (Roles) object;
            System.out.println(o.toString());
            return getStringKey(o.getIdRol());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Roles.class.getName()});
            return null;
        }
    }

}
