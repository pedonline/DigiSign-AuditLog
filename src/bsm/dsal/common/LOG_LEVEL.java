package bsm.dsal.common;

public enum LOG_LEVEL {
	INFO_LEVEL(100, "INFO", "Information"),
	WARN_LEVEL(200, "WARN", "Warning"),
	ERROR_LEVEL(300, "ERROR", "Error"),
	DEBUG_LEVEL(400, "DEBUG", "DEBUG"),
	SQL_COMMAND_LEVEL(500, "SQLCOMMAND", "SQL Command");

	private int Value;
	private String Code;
	private String Message;

	private LOG_LEVEL(int value, String code, String message) {
		this.Value = value;
		this.Code = code;
		this.Message = message;
	}

	public int getValue() {
		return this.Value;
	}

	public void setValue(int value) {
		this.Value = value;
	}

	public String getCode() {
		return this.Code;
	}

	public void setCode(String code) {
		this.Code = code;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String message) {
		this.Message = message;
	}

	public static LOG_LEVEL getEnum(int Data) {
		LOG_LEVEL[] CheckList = LOG_LEVEL.values();
		for (int i = 0; i < CheckList.length; i++) {
			if (CheckList[i].getValue() == Data) {
				return CheckList[i];
			}
		}
		return LOG_LEVEL.INFO_LEVEL;
	}
}
