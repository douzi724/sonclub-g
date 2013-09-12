package net.sonclub

import net.sonclub.command.ToMsg
import net.sonclub.common.WechatMsgType
import net.sonclub.exception.WechatAuthException
import net.sonclub.exception.WechatCommandException

class WechatApiController {
    def beforeInterceptor = [action: this.&authWechat, except: 'login']
    def ioApiService
    def userService

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
        if (fromMsg.msgType == WechatMsgType.text.toString()) {
            fromMsg.content = xmlMsg.Content as String
            fromMsg.msgKey = xmlMsg.MsgId as String
        } else if (fromMsg.msgType == WechatMsgType.event.toString()) {
            fromMsg.content = xmlMsg.Event as String
            fromMsg.msgKey = xmlMsg.EventKey as String
        }

        //处理消息
        String handleResult = ""
        try {
            userService.checkNewUser(fromMsg.fromUser)
            handleResult = ioApiService.handleMsg(fromMsg)
        } catch (WechatAuthException e) {
            fromMsg.action = "unAuth"
            handleResult = e.getMessage()
        } catch (WechatCommandException e) {
            fromMsg.action = "commandErr"
            handleResult = e.getMessage()
        } catch (Exception e) {
            fromMsg.action = "xErr"
            handleResult = new WechatCommandException().getMessage()
        } finally {
            fromMsg.save(flush:true)
        }

        //装载发出的消息
        def toMsg = new ToMsg(
                fromUser: xmlMsg.ToUserName as String,
                toUser: xmlMsg.FromUserName as String,
                msgType: "text",
                createTime: (new Date().time / 1000).intValue(),
                content: handleResult
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
}
