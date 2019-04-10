package bsm.factory.person;


import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bsm.common.factory.DigiSignManageBeanTemplate;

@ManagedBean(name = "personEdit")
@ViewScoped
public class PersonEdit extends DigiSignManageBeanTemplate implements Serializable {
	
	public PersonEdit() {
		// Constructor
	}
	
	@Override
	public String saveAction() {
		//processSave
		String methodName = "processSave";
	
		return null;
	}
}
