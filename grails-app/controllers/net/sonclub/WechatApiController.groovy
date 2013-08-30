package net.sonclub

import net.sonclub.command.ToMsg

class WechatApiController {

    def beforeInterceptor = [action: this.&authWechat, except: 'login']
    def ioApiService
    def messageSource

    //接入验证
    def input() {
        render (params.echostr)
    }

    //信息回复
    def output() {
        def inMsg = new XmlSlurper().parseText(request.reader.text)
        def toMsg = new ToMsg(
                fromUserName: inMsg.ToUserName.toString(),
                toUserName: inMsg.FromUserName.toString(),
                msgType: "text",
                createTime: (new Date().time / 1000).intValue()
        )
        params.put("toMsg", toMsg)

        //解析收到消息内容
        def fromMsg = new FromMsg(
                toUserName: inMsg.ToUserName.toString(),
                fromUserName: inMsg.FromUserName.toString(),
                msgType: inMsg.MsgType.toString(),
                createTime: new Date((inMsg.CreateTime.toLong()) * 1000)
        )
        if (inMsg.MsgType == "text") {
            fromMsg.content = inMsg.Content.toString()
            fromMsg.msgId = inMsg.MsgId.toString()
        } else if (inMsg.MsgType == "event") {
            fromMsg.content = inMsg.Event.toString()
            fromMsg.msgId = inMsg.EventKey.toString()
        }

        //检查用户权限
        if (ioApiService.checkUser(fromMsg, toMsg)) {
            ioApiService.handleMsg(fromMsg, toMsg)
        } else {
            fromMsg.action = "unAuth"
            toMsg.content =  messageSource.getMessage("wechat.text.unauth", null, new Locale("zh", "CN"))
        }
        fromMsg.save()
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
