package bsm.common.factory;


import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import bsm.dsal.common.CommonLog;
import bsm.dsal.common.LOG_LEVEL;
import bsm.dsal.digisign.DigiSignInformation;
import bsm.dsal.digisign.DigisignInformationExpiredException;



public abstract class DigiSignManageBeanTemplate {
	private static String className = "DigiSignManageBeanTemplate";
	@ManagedProperty(value = "#{digiSignInformation}")
	private DigiSignInformation digiSignInformation = null;
	public abstract String saveAction();
	

	public void onSignAction(SelectEvent event) throws DigisignInformationExpiredException{ 
		String methodName = "onAttachFileAction";
		String redirectPage = null;
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[START]");
		if(event != null && event.getObject() != null){
			DigiSignInformation DigiSignInformationFormDialog = (DigiSignInformation) event.getObject();
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "DigiSignInformationFormDialog :: "+DigiSignInformationFormDialog);
			if(DigiSignInformationFormDialog != null && !DigiSignInformationFormDialog.isExpired()){
				this.digiSignInformation.setDigisignAlias(DigiSignInformationFormDialog.getDigisignAlias());	
				this.digiSignInformation.setDigisignCertificate(DigiSignInformationFormDialog.getDigisignCertificate());
				this.digiSignInformation.setDigisignBeginTime(DigiSignInformationFormDialog.getDigisignBeginTime());
				this.digiSignInformation.setDigisignExpiredTime(DigiSignInformationFormDialog.getDigisignExpiredTime());
				this.digiSignInformation.setDigisignOTK(DigiSignInformationFormDialog.getDigisignOTK());				
				this.digiSignInformation.setDigisignDigitalSignature(DigiSignInformationFormDialog.getDigisignDigitalSignature());
				CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "digiSignInformation :: "+this.digiSignInformation);
				redirectPage = saveAction();
				
			}else{
				CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "DATA Not Found Form Dialog");
			}
		}
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "redirectPage :: "+redirectPage);
		FacesContext fc = FacesContext.getCurrentInstance();
	    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, redirectPage);
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[END]");
	}


	public DigiSignInformation getDigiSignInformation() {
		return digiSignInformation;
	}


	public void setDigiSignInformation(DigiSignInformation digiSignInformation) {
		this.digiSignInformation = digiSignInformation;
	}



}
