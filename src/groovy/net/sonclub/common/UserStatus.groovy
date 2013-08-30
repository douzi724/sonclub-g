package net.sonclub.common

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-8-30
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public enum UserStatus {
    unauth("未验证"), on("启用"), off("停用"), over("注销")
    UserStatus(String name) { this.name = name }
    private final String name
    public String getName() { return name }
}