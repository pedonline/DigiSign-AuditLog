package bsm.dsal.common;
import bsm.dsal.util.policy.PolicyController;

public class CommonLog {
	private static boolean cb_LogMode = PolicyController.CommonLog;
	private static boolean cb_DebugMode = PolicyController.DebugLog;
	private static boolean cb_SQLCommandLog = PolicyController.SQLCommandLog;
	
	public static void Print(LOG_LEVEL LL, String AppName, String ModuleName, String Message) {
		if (cb_LogMode) {
			if (LL == LOG_LEVEL.INFO_LEVEL) {
				System.out.println("[INFO]-[" + AppName + ":" + ModuleName + "] -> " + Message);
			} else if (LL == LOG_LEVEL.WARN_LEVEL) {
				System.out.println("[WARN]-[" + AppName + ":" + ModuleName + "] -> " + Message);
			} else if (LL == LOG_LEVEL.ERROR_LEVEL) {
				System.out.println("[ERROR]-[" + AppName + ":" + ModuleName + "] -> " + Message);
			} 
//			else {
//				System.out.println("[INFO]-[" + AppName + ":" + ModuleName + "] -> " + Message);
//			}

		}
		if(cb_DebugMode){
			if (LL == LOG_LEVEL.DEBUG_LEVEL) {
				System.out.println("[DEBUG]-[" + AppName + ":" + ModuleName + "] -> " + Message);
			}
		}
		if(cb_SQLCommandLog){
			if (LL == LOG_LEVEL.SQL_COMMAND_LEVEL) {
				System.out.println("[SQLCOMMAND]-[" + AppName + ":" + ModuleName + "] -> " + Message);
			}
		}
	}
}
