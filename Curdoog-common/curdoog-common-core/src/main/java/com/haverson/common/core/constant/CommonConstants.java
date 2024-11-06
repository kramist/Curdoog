

package com.haverson.common.core.constant;

/**
 * @author jiangwei
 */
public interface CommonConstants {
	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单树根节点
	 */
	Integer MENU_TREE_ROOT_ID = -1;

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "xchd-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "xchd";

	/**
	 * 成功标记
	 */
	String SUCCESS = "200";
	/**
	 * 失败标记
	 */
	String FAIL = "999";
	/**
	 * Feign失败标记
	 */
	String FEIGN_FAIL = "705";
	/**
	 * 错误
	 */
	String TWO_ZERO_ONE= "201";

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

	/**
	 * 当前页
	 */
	String CURRENT = "current";

	/**
	 * size
	 */
	String SIZE = "size";

	String HTTP = "http://";

	String HTTPS = "https://";

	char PROVINCE = '省';
	char CITY = '市';
	char AREA = '区';
	char TOWN = '县';
	char ZHOU = '州';
}
