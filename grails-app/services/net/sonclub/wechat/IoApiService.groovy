package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.SysUser
import net.sonclub.command.ToMsg
import net.sonclub.common.UserStatus


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

    def handleMsg(FromMsg fromMsg, ToMsg toMsg) {
        def handleService

        if (fromMsg.msgType == "text") {
            handleService =  textHandleService
            fromMsg.action = textCommand.get(fromMsg.content.toString()) ?: "custom"
        } else if (fromMsg.msgType == "event") {
            handleService =  eventHandleService
            fromMsg.action = fromMsg.content
        } else {
            toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
        }

        try {
            if (handleService.metaClass.respondsTo(handleService, fromMsg.action)) {
                handleService."${fromMsg.action}"(fromMsg, toMsg)
            } else {
                fromMsg.action = "commandErr"
                toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
            }
        } catch (Exception e) {
            fromMsg.action = "commandErr"
            toMsg.content = messageSource.getMessage("wechat.text.error", null, new Locale("zh", "CN"))
        } finally {

        }
    }

    def checkUser(FromMsg fromMsg, ToMsg toMsg) {
        if (inMsg.Event == "subscribe") {

        }
        def user = SysUser.findByWechatId(toMsg.toUserName)
        if (user != null && user.status == UserStatus.on) return true
        return false
    }
}
