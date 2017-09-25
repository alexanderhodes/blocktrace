package me.alexanderhodes.blocktrace.view.converter;

import me.alexanderhodes.blocktrace.model.Product;
import me.alexanderhodes.blocktrace.service.ProductService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 * Created by alexa on 24.09.2017.
 */
@ManagedBean(name = "productConverter")
@SessionScoped
public class ProductConverter implements Converter {

    @Inject
    private ProductService productService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        } else {
            // TODO: return Product
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof String) {
            return (String) o;
        }
        return String.valueOf(((Product) o).getId()).toString();
    }
}
