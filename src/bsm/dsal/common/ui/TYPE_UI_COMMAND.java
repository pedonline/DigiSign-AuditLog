/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
package bsm.dsal.common.ui;

public enum TYPE_UI_COMMAND {
	UI_COMMAND_VIEW(100, "ดู", "View"),
	UI_COMMAND_ADD(200, "เพิ่ม", "Add"),
	UI_COMMAND_EDIT(300, "แก้ไข", "Edit"),
	UI_COMMAND_DELETE(400, "ลบ", "Delete"),
	UI_COMMAND_SEARCH(500, "ค้นหา", "Search"),
	UI_COMMAND_DISABLE(600, "ยกเลิก", "Disable"),
	UI_COMMAND_SELECT(700, "เลือก", "Select");

	private static final int[] TypeValue = { 100, 200, 300, 400, 500, 600, 700 };

	private int Value;
	private String TMessage;
	private String EMessage;

	private TYPE_UI_COMMAND(int value, String tmessage, String emessage) {
		this.Value = value;
		this.TMessage = tmessage;
		this.EMessage = emessage;
	}
	
	public static int[] getTypeValue() {
		return TypeValue;
	}

	public int getValue() {
		return this.Value;
	}

	public void setValue(int value) {
		this.Value = value;
	}

	public String getTMessage() {
		return this.TMessage;
	}

	public void setTMessage(String message) {
		this.TMessage = message;
	}

	public String getEMessage() {
		return this.EMessage;
	}

	public void setEMessage(String message) {
		this.EMessage = message;
	}

	public static TYPE_UI_COMMAND getEnum(int Data) {
		TYPE_UI_COMMAND[] CheckList = TYPE_UI_COMMAND.values();
		for (int i = 0; i < CheckList.length; i++) {
			if (CheckList[i].getValue() == Data) {
				return CheckList[i];
			}
		}
		return TYPE_UI_COMMAND.UI_COMMAND_SELECT;
	}

	public TYPE_UI_COMMAND[] getValues(int ai_Mode) {
		return null;
	}

}