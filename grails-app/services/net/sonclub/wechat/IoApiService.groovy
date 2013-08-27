package net.sonclub.wechat


class IoApiService {

    def textHandleService
    def eventHandleService

    def handleMsg(message, params) {
        def handleService
        def action

        if (message.MsgType == "text") {
            handleService =  textHandleService
            action = message.Content
            if (action == "+") action = "joinMatch"
            if (action == "-") action = "exitMatch"
            if (action == "?") action = "commandHelp"
        } else if (message.MsgType == "event") {
            handleService =  eventHandleService
            action = message.Event
        } else {
            println('bubububu')
        }
        try {
            if (handleService.metaClass.respondsTo(handleService, action)) {
                handleService."${action}"()
            } else {
                println('nononon')
            }
        } catch (Exception e) {
            println('nononon')
        }
    }
}
