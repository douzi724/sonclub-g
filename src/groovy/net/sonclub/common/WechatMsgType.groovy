package net.sonclub.common

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-9-12
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public enum WechatMsgType {
    text("文本"), event("事件"), image("图片"), location("地理位置"), link("链接")
    WechatMsgType(String name) { this.name = name }
    private final String name
    public String getName() { return name }
}