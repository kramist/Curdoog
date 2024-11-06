

package com.haverson.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haverson.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 响应信息主体
 *
 * @param <T>
 * @author jiangwei
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RespInfo<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String retMsg;


	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;


	public static <T> RespInfo<T> ok() {
		return restResult(null, CommonConstants.SUCCESS, null);
	}

	public static <T> RespInfo<T> ok(String msg) {
		return restResult(null, CommonConstants.SUCCESS, msg);
	}

	public static <T> RespInfo<T> ok(T data) {
		return restResult(data, CommonConstants.SUCCESS, null);
	}
	public static <T> RespInfo<T> ok(T data, String msg) {
		return restResult(data, CommonConstants.SUCCESS, msg);
	}

	public static <T> RespInfo<T> failed() {
		return restResult(null, CommonConstants.FAIL, null);
	}

	public static <T> RespInfo<T> failed(String msg) {
		return restResult(null, CommonConstants.FAIL, msg);
	}

	public static <T> RespInfo<T> failed(T data) {
		return restResult(data, CommonConstants.FAIL, null);
	}

	public static <T> RespInfo<T> failed(T data, String msg) {
		return restResult(data, CommonConstants.FAIL, msg);
	}

	public static <T> RespInfo<T> restResult(String code, String msg) {
		return restResult(null, code, msg);
	}

	public static <T> RespInfo<T> restResult(T data, String code, String msg) {
		RespInfo<T> apiResult = new RespInfo<T>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setRetMsg(msg);
		return apiResult;
	}
}

