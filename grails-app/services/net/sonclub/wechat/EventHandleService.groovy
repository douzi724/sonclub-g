package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.command.ToMsg

class EventHandleService {
    def messageSource

    def subscribe(FromMsg fromMsg, ToMsg toMsg) {
        toMsg.content =  messageSource.getMessage("wechat.subscribe", null, null)
    }

    def unsubscribe(FromMsg fromMsg, ToMsg toMsg) {

    }
}
