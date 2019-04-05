/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
package bsm.dsal.digisign;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import bsm.dsal.common.ui.TYPE_UI_COMMAND;
import bsm.dsal.utils.JSFUtils;
import dspo.util.CommonLog;
import dspo.util.LOG_LEVEL;




@ManagedBean(name = "digiSignDialog")
@SessionScoped
public class DigiSignDialog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8539569681890389036L;
	/**
	 * 
	 */
	private String className = "DigiSignDialog";
	@ManagedProperty(value = "#{digiSignInformation}")
	private DigiSignInformation digiSignInformation = null;
	
	public void DialogchooseOpen(ActionEvent event) {
    	System.out.println("DigiSignDialog :: Start");

    	Map<String, Object> options = new HashMap<String, Object>();
    	options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentWidth", 600);
		options.put("contentHeight", 600);
		if(digiSignInformation.isExpired()) {
			RequestContext.getCurrentInstance().openDialog("digital_signature_dialog", options, null);
		}else {
			RequestContext.getCurrentInstance().openDialog("digital_signature_dialog_confirm", options, null);
		}
		
    }
	public void DialogChooseCancel(ActionEvent event) {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogchooseCancel", "[Request]");
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void DialogChooseConfirm(ActionEvent event) {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[START]");	
		JSFUtils.flashScope().put("TYPE_UI_COMMAND", TYPE_UI_COMMAND.UI_COMMAND_ADD);
		Map<String, String> RequestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(this.digiSignInformation != null && !digiSignInformation.isExpired()) {
			if(!digiSignInformation.isExpired()) {
				CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[Not Null && Not Expired]");
				RequestContext.getCurrentInstance().closeDialog(this.getDigiSignInformation());
			}else {
				CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[Sign New]");
				Map<String, Object> options = new HashMap<String, Object>();
		    	options.put("modal", true);
				options.put("draggable", false);
				options.put("resizable", false);
				options.put("contentWidth", 600);
				options.put("contentHeight", 600);
				RequestContext.getCurrentInstance().closeDialog(null);
				RequestContext.getCurrentInstance().openDialog("digital_signature_dialog", options, null);	
			}
		
		}else {			
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[New DigiSignInformation]");
			String digisignOTK = RequestParameterMap.get("digiSignForm:digisignOTK");
			String digisignAlias = RequestParameterMap.get("digiSignForm:digisignAlias");
			String digisignCertificate = RequestParameterMap.get("digiSignForm:digisignCertificate");
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[digiSignForm:digisignOTK :: ]"+digisignOTK);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[digiSignForm:digisignAlias :: ]"+digisignAlias);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[digiSignForm:digisignCertificate :: ]"+digisignCertificate);
		
			this.digiSignInformation.setDigisignAlias(digisignAlias);
			this.digiSignInformation.setDigisignOTK(digisignOTK);
			this.digiSignInformation.setDigisignCertificate(digisignCertificate);
			if(digisignOTK != null && !digisignOTK.isEmpty()){
				RequestContext.getCurrentInstance().closeDialog(this.getDigiSignInformation());
			}else{
				FacesContext.getCurrentInstance().addMessage("notifyError", new FacesMessage(FacesMessage.SEVERITY_ERROR,"�?รุณาล�?�?าม", "�?รุณาล�?�?าม"));
				FacesMessage msg = new FacesMessage();
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				msg.setSummary("�?รุณาล�?�?าม");
				RequestContext.getCurrentInstance().showMessageInDialog(msg);			
			}
			
		}			
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, "DialogConfirm", "[END]");
	}
	public DigiSignInformation getDigiSignInformation() {
		return digiSignInformation;
	}
	public void setDigiSignInformation(DigiSignInformation digiSignInformation) {
		this.digiSignInformation = digiSignInformation;
	}

}
