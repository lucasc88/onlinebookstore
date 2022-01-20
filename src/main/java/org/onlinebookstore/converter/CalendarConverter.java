package org.onlinebookstore.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Class responsible to converter in the correct patter and time-zone from
 * screen to controller and vice-versa
 * 
 * @author Lucas
 *
 */
@FacesConverter(forClass = Calendar.class)
public class CalendarConverter implements Converter {

	private DateTimeConverter converter = new DateTimeConverter();

	public CalendarConverter() {
		converter.setPattern("dd/MM/yyyy");
		converter.setTimeZone(TimeZone.getTimeZone("Europe/Dublin"));
	}

	// from screen to back end
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String textDate) {
		if (textDate == null || textDate.isEmpty()) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			textDate = formatter.format(new Date());
		}
		Date data = (Date) converter.getAsObject(context, component, textDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar;
	}

	// from back end to screen
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object objectDate) {
		if (objectDate == null)
			return null;

		Calendar calendar = (Calendar) objectDate;
		return converter.getAsString(context, component, calendar.getTime());
	}

}
