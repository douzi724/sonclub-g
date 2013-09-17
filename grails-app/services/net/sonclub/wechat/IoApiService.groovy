package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.command.ToMsg
import net.sonclub.common.WechatMsgType
import net.sonclub.exception.WechatAuthException
import net.sonclub.exception.WechatCommandException


class IoApiService {
    def userService
    def textHandleService
    def eventHandleService

    def textCommand = [
        "+": "joinMatch",
        "-": "exitMatch",
        "?": "commandHelp",
        "？": "commandHelp",
        "help": "commandHelp"
    ]

    //解析信息，组织回复信息
    public ToMsg parseMsg(xmlMsg) {
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
            handleResult = handleMsg(fromMsg)
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

        //组织回复消息
        def toMsg = new ToMsg(
                fromUser: xmlMsg.ToUserName as String,
                toUser: xmlMsg.FromUserName as String,
                msgType: "text",
                createTime: (new Date().time / 1000).intValue(),
                content: handleResult
        )

        return toMsg
    }


    //处理消息
    private String handleMsg(FromMsg fromMsg) throws WechatCommandException, WechatAuthException {
        def handleService
        //指定处理方法
        if (fromMsg.msgType == WechatMsgType.text.toString()) {
            handleService =  textHandleService
            fromMsg.action = textCommand.get(fromMsg.content) ?: "custom"
        } else if (fromMsg.msgType == WechatMsgType.event.toString()) {
            handleService =  eventHandleService
            fromMsg.action = fromMsg.content
        } else {
            throw new WechatCommandException()
        }
        //处理并返回信息
        if (handleService.metaClass.respondsTo(handleService, fromMsg.action)) {
            if (userService.buildSubject(fromMsg.fromUser).isPermitted("comm:${fromMsg.msgType}:${fromMsg.action}")) {
                return handleService."${fromMsg.action}"(fromMsg)
            } else {
                throw new WechatAuthException()
            }
        } else {
            throw new WechatCommandException()
        }
    }
}
