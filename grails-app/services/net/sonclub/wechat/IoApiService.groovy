package net.sonclub.wechat

import net.sonclub.FromMsg
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

    //处理消息
    def String handleMsg(FromMsg fromMsg) throws WechatCommandException, WechatAuthException {
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
