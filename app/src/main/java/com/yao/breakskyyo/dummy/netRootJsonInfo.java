package com.yao.breakskyyo.dummy;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/25 19:56
 * 修改人：yoyo
 * 修改时间：2015/9/25 19:56
 * 修改备注：
 */
public class netRootJsonInfo<I> {
    int code;
    I info;
    String tip;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public I getInfo() {
        return info;
    }

    public void setInfo(I info) {
        this.info = info;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
