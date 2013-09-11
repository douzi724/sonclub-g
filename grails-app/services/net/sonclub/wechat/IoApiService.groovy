package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.SysUser
import net.sonclub.command.ToMsg
import net.sonclub.common.UserStatus
import net.sonclub.exception.WechatAuthException
import net.sonclub.exception.WechatCommandException
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.Subject


class IoApiService {

    def shiroSecurityManager
    def messageSource

    def textHandleService
    def eventHandleService

    def textCommand = [
        "+": "joinMatch",
        "-": "exitMatch",
        "?": "commandHelp",
        "？": "commandHelp",
        "help": "commandHelp"
    ]

    //处理消息
    def String handleMsg(FromMsg fromMsg) {
        def handleService

        try {
            //指定处理方法
            if (fromMsg.msgType == "text") {
                handleService =  textHandleService
                fromMsg.action = textCommand.get(fromMsg.content) ?: "custom"
            } else if (fromMsg.msgType == "event") {
                handleService =  eventHandleService
                fromMsg.action = fromMsg.content
            } else {
                throw new WechatCommandException()
            }
            //处理并返回信息
            if (handleService.metaClass.respondsTo(handleService, fromMsg.action)) {
                if (buildSubject(fromMsg.fromUser).isPermitted("comm:${fromMsg.msgType}:${fromMsg.action}")) {
                    return handleService."${fromMsg.action}"(fromMsg)
                } else {
                    throw new WechatAuthException()
                }
            } else {
                throw new WechatCommandException()
            }
        } catch (WechatCommandException e) {
            fromMsg.action = "commandErr"
            return e.getMessage()
        } catch (WechatCommandException e) {
            fromMsg.action = "notAuth"
            return e.getMessage()
        } catch (Exception e) {
            fromMsg.action = "xErr"
            return new WechatCommandException().getMessage()
        } finally {
            fromMsg.save()
        }
    }

    private Subject buildSubject(String userIdentity) {
        PrincipalCollection principals = new SimplePrincipalCollection(userIdentity, "DbRealm");
        return new Subject.Builder(shiroSecurityManager).principals(principals).buildSubject();
    }
}
