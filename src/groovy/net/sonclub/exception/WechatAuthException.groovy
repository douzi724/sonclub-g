package net.sonclub.exception

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-9-4
 * Time: 下午6:05
 * To change this template use File | Settings | File Templates.
 */
class WechatAuthException  extends BaseException{
    public WechatAuthException() {
        this.code = "wechat.exception.notauth"
    }
    public WechatAuthException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }
}