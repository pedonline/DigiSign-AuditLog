/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
package bsm.dsal.utils;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class JSFUtils {
	public static Flash flashScope (){
		return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
	}
	public static Map<String, String> parameterMap (){
		return (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap());
	}
	public static Map<Object, Object> attributes (){
		return (FacesContext.getCurrentInstance().getAttributes());
	}
}
