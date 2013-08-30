package net.sonclub.common

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-8-30
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public enum UserFlag {
    player("队员"), aptain("队长")
    UserFlag(String name) { this.name = name }
    private final String name
    public String getName() { return name }
}