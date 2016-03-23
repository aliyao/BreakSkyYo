package com.yao.breakskyyo.entity;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/3/23 15:57
 * 修改人：yoyo
 * 修改时间：2016/3/23 15:57
 * 修改备注：
 */
public class JsonHead<E>  {
    int code;
    E info;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
