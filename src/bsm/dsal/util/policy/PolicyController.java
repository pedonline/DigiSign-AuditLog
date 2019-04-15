package bsm.dsal.util.policy;

import java.io.IOException;
import java.util.PropertyResourceBundle;

import bsm.dsal.common.TYPE_FLAG;

public class PolicyController {
	public static int LogSizeLimit = 0;
	public static boolean VerifyData = false;
	public static boolean SystemCommonLog = false;
	public static boolean CommonLog = true;
	public static boolean DebugLog = true;
	public static boolean SQLCommandLog = true;

	static {
		
		PropertyResourceBundle propResBundle = null;
		try {
			propResBundle = new PropertyResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream("config_policy_digisign.properties"));
			
			try{
				TYPE_FLAG lo_VerifyData = TYPE_FLAG.getEnum(Integer.parseInt(propResBundle.getString("VerifyData")));
				if( lo_VerifyData.getValue() == TYPE_FLAG.TYPE_TRUE.getValue()){
					VerifyData = true;
				}else{
					VerifyData = false;
				}
			}catch(Exception ex){
				VerifyData = false;
			}
			try{
				TYPE_FLAG lo_SystemCommonLog = TYPE_FLAG.getEnum(Integer.parseInt(propResBundle.getString("SystemCommonLog")));
				if( lo_SystemCommonLog.getValue() == TYPE_FLAG.TYPE_TRUE.getValue()){
					SystemCommonLog = true;
				}else{
					SystemCommonLog = false;
				}
			}catch(Exception ex){
				SystemCommonLog = false;
			}
			
			try{
				TYPE_FLAG lo_CommonLog = TYPE_FLAG.getEnum(Integer.parseInt(propResBundle.getString("CommonLog")));
				if( lo_CommonLog.getValue() == TYPE_FLAG.TYPE_TRUE.getValue()){
					CommonLog = true;
				}else{
					CommonLog = false;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				CommonLog = false;
			}
			try {
				TYPE_FLAG lo_DebugLog = TYPE_FLAG.getEnum(Integer.parseInt(propResBundle.getString("DebugLog")));
				if (lo_DebugLog.getValue() == TYPE_FLAG.TYPE_TRUE.getValue()) {
					DebugLog = true;
				} else {
					DebugLog = false;
				}
			} catch (Exception ex) {
//				ex.printStackTrace();
				DebugLog = false;
			}
			
			try {
				TYPE_FLAG lo_SQLCommandLog = TYPE_FLAG.getEnum(Integer.parseInt(propResBundle.getString("SQLCommandLog")));
				if (lo_SQLCommandLog.getValue() == TYPE_FLAG.TYPE_TRUE.getValue()) {
					SQLCommandLog = true;
				} else {
					SQLCommandLog = false;
				}
			} catch (Exception ex) {
//				ex.printStackTrace();
				SQLCommandLog = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
	}
	
	public static void main(String[] args) {
		
	}
}
