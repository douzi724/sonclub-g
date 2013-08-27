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
        } else if (message.MsgType == "event") {
            handleService =  eventHandleService
            action = message.Event
        }

        if (handleService.getClass().getMethods().contains("${action}")) {
            handleService."${action}"()
        } else {
            println('nononon')
        }
    }
}
