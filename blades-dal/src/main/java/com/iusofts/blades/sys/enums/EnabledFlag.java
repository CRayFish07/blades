package com.iusofts.blades.sys.enums;

public enum EnabledFlag {

	ENABLE("可用", "1"), DISABLE("不可用", "0");

	private String name;
    private String code;

	private EnabledFlag(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static String getName(String code) {
		for (EnabledFlag c : EnabledFlag.values()) {
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
