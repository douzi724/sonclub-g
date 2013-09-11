package net.sonclub

import net.sonclub.command.ToMsg
import net.sonclub.common.UserFlag
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType
import net.sonclub.shiro.Role
import net.sonclub.shiro.User
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.Subject

class WechatApiController {

    //def beforeInterceptor = [action: this.&authWechat, except: 'login']
    def ioApiService
    def shiroSecurityService

    //接入验证
    def input() {
        render (params.echostr)
    }

    //信息回复
    def output() {
        def xmlMsg = new XmlSlurper().parseText(request.reader.text)
        //解析收到的消息
        def fromMsg = new FromMsg(
                toUser: xmlMsg.ToUserName as String,
                fromUser: xmlMsg.FromUserName as String,
                msgType: xmlMsg.MsgType as String,
                createTime: new Date((xmlMsg.CreateTime.toLong()) * 1000)
        )
        if (fromMsg.msgType == "text") {
            fromMsg.content = xmlMsg.Content as String
            fromMsg.msgKey = xmlMsg.MsgId as String
        } else if (fromMsg.msgType == "event") {
            fromMsg.content = xmlMsg.Event as String
            fromMsg.msgKey = xmlMsg.EventKey as String
            if (fromMsg.content == "subscribe") checkNewUser(fromMsg)
        }
        fromMsg.save()
        ioApiService.handleMsg(fromMsg)

        //装载发出的消息
        def toMsg = new ToMsg(
                fromUser: xmlMsg.ToUserName as String,
                toUser: xmlMsg.FromUserName as String,
                msgType: "text",
                createTime: (new Date().time / 1000).intValue()
        )
        params.put("toMsg", toMsg)

        render(contentType: "text/xml", view: "output.gsp")
    }

    //微信验证
    private authWechat() {
        def signature = [ grailsApplication.config.wechat.token, params.timestamp, params.nonce]
        signature.sort()
        //println(signature.join('').encodeAsSHA1())
        if (params.signature != signature.join('').encodeAsSHA1()) {
            response.status = 403
            return false
        }
    }

    private checkNewUser(FromMsg fromMsg) {
        if (User.findByUsername(fromMsg.fromUser)) {
            new User(
                    username: fromMsg.fromUser,
                    passwordHash: shiroSecurityService.encodePassword('sonclub'),
                    type: UserType.wechat,
                    status: UserStatus.unauth,
                    flagX: UserFlag.player,
                    roles: Role.findByName("sc_normal")
            ).save(flush: true, failOnError: true)
        }
    }
}
