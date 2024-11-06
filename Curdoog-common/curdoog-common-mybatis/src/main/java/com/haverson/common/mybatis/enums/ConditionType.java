package com.haverson.common.mybatis.enums;

/**
 * 查询条件类型枚举
 * @author
 */
public enum ConditionType {
    EQUAL,//相等
    NOTEQUAL,//不相等
    GREATEREQUAL,//大于等于
    GREATERTHAN,//大于
    LESSEQUAL,//小于等于
    LESSTHAN,//小于
    NOTIN,//不在集合
    IN,//在集合
    LEFTLIKE,//左相似
    ALLLIKE//相似

}
