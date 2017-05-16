package com.iusofts.blades.sys.enums;

public enum DeleteFlag {

	NORMAL("正常", "0"), DELETED("已删除", "1");

	private String name;
    private String code;

	private DeleteFlag(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static String getName(String code) {
		for (DeleteFlag c : DeleteFlag.values()) {
			if (c.getCode() == code) {
				return c.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
