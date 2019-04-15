package bsm.dsal.common;

public enum TYPE_FLAG {
	TYPE_TRUE(10, "แสดง"),
	TYPE_FALSE(20, "ไม่แสดง");
	private static final int[] StatusValue = { 10, 20};

	private static final int[][] StatusFlow = {};

	private int Value;
	private String Message;

	private TYPE_FLAG(int value, String message) {
		this.Value = value;
		this.Message = message;
	}

	public int getValue() {
		return this.Value;
	}

	public static int[] getStatusValue() {
		return StatusValue;
	}

	public static int[][] getStatusFlow() {
		return StatusFlow;
	}

	public void setValue(int value) {
		this.Value = value;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String message) {
		this.Message = message;
	}

	public static TYPE_FLAG getEnum(int Data) {
		TYPE_FLAG[] CheckList = TYPE_FLAG.values();
		for (int i = 0; i < CheckList.length; i++) {
			if (CheckList[i].getValue() == Data) {
				return CheckList[i];
			}
		}
		return TYPE_FLAG.TYPE_TRUE;
	}
	public static TYPE_FLAG getEnum(String message) {
		TYPE_FLAG[] CheckList = TYPE_FLAG.values();
		for (int i = 0; i < CheckList.length; i++) {
			if (CheckList[i].getMessage().equals(message)) {
				return CheckList[i];
			}
		}
		return TYPE_FLAG.TYPE_TRUE;
	}
}