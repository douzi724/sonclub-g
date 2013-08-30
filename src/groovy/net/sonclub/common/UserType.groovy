package net.sonclub.common

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-8-30
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public enum UserType {
    wechat("微信"), web("网站"), admin("管理员")
    UserType(String name) { this.name = name }
    private final String name
    public String getName() { return name }
}