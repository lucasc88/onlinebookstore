package org.onlinebookstore.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.onlinebookstore.model.Author;

/**
 * Class responsible to converter authorId in object Author from screen to
 * controller and vice-versa
 * 
 * @author Lucas
 *
 */
@FacesConverter("authorConverter")
public class AuthorConverter implements Converter {

	// from screen to back end
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.trim().isEmpty())
			return null;

		Author autor = new Author();
		autor.setId(Integer.valueOf(id));

		return autor;
	}

	// from back end to screen
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object autorObject) {
		if (autorObject == null)
			return null;

		Author autor = (Author) autorObject;
		return autor.getId().toString();
	}

}