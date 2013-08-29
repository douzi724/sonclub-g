package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.command.ToMsg


class IoApiService {

    def textHandleService
    def eventHandleService
    def messageSource

    def textCommand = [
        "+": "joinMatch",
        "-": "exitMatch",
        "?": "commandHelp",
        "？": "commandHelp",
        "help": "commandHelp"
    ]

    def handleMsg(message, params) {
        def handleService

        def fromMsg = new FromMsg()
        fromMsg.fromUserName = message.FromUserName
        fromMsg.toUserName = message.ToUserName
        fromMsg.msgType = message.MsgType
        fromMsg.msgId = message.MsgId
        fromMsg.createTime = new Date((message.CreateTime.toLong()) * 1000)
        fromMsg.content = message.Content
        fromMsg.event = message.Event
        fromMsg.save()

        def toMsg = new ToMsg()
        toMsg.fromUserName = fromMsg.toUserName
        toMsg.toUserName = fromMsg.fromUserName
        toMsg.msgType = "text"
        toMsg.createTime = (new Date().time / 1000).intValue()
        params.put("toMsg", toMsg)

        if (message.MsgType == "text") {
            handleService =  textHandleService
            fromMsg.action = textCommand.get(message.Content.toString()) ?: message.Content
        } else if (message.MsgType == "event") {
            handleService =  eventHandleService
            fromMsg.action = fromMsg.event
        } else {
            toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
        }
        try {
            if (handleService.metaClass.respondsTo(handleService, fromMsg.action)) {
                handleService."${fromMsg.action}"(fromMsg, toMsg)
            } else {
                toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
            }
        } catch (Exception e) {
            toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
        }
    }
}
