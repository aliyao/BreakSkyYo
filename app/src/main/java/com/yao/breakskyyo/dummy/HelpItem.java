package com.yao.breakskyyo.dummy;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/10/13 20:40
 * 修改人：yoyo
 * 修改时间：2015/10/13 20:40
 * 修改备注：
 */
public class HelpItem {
    int title;
    String content;
    int imageResourceId;
    int instruction;
    int playType;
    public HelpItem(){

    }

    public HelpItem( int title,int instruction, int playType){
        this.title=title;
        this.instruction=instruction;
        this.playType=playType;
    }
    public HelpItem(int imageResourceId,String content){
        this.imageResourceId=imageResourceId;
        this.content=content;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getInstruction() {
        return instruction;
    }

    public void setInstruction(int instruction) {
        this.instruction = instruction;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }
}
