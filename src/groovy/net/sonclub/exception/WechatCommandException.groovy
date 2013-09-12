package net.sonclub.exception

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-9-4
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
class WechatCommandException extends BaseException{
    public WechatCommandException() {
        this.code = "wechat.exception.error"
    }
    public WechatCommandException(Object[] args) {
        this.args = args;
    }
    public WechatCommandException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }
}
