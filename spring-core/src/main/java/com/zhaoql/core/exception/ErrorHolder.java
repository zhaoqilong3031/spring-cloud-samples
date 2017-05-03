package com.zhaoql.core.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ErrorHolder {

	public static String getCode(CodeTemp templ) {
		return templ.getCode();
	}

	public static String getMessage(CodeTemp templ, String... params) {
		String regContent = templ.getTemp();
		int index = 0;
		while (regContent.indexOf("{}") >= 0) {
			if (ArrayUtils.isEmpty(params) || params.length <= index) {
				if (regContent.indexOf("{}") >= 0) {
					regContent = regContent.replaceAll("\\{\\}", "");
				}
				break;
			}
			regContent = StringUtils.replaceOnce(regContent, "{}", params[index]);
			index++;
		}

		return regContent;
	}

	public enum CodeTemp {
		SUCCESS("00000", "成功"), UNKNOW("10000", "未知错误,{}"),;
		String code;
		String temp;

		CodeTemp(String code, String temp) {
			this.temp = temp;
			this.code = code;
		}

		public static CodeTemp toEnum(String code) {
			for (CodeTemp enums : CodeTemp.values()) {
				if (enums.getCode().equals(code)) {
					return enums;
				}
			}
			return CodeTemp.UNKNOW;
		}

		public String getCode() {
			return code;
		}

		public String getTemp() {
			return temp;
		}

	}
}
