package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.command.ToMsg

class EventHandleService {
    def messageSource

    def subscribe(FromMsg fromMsg, ToMsg toMsg) {
        toMsg.content =  messageSource.getMessage("wechat.event.subscribe", null, new Locale("zh", "CN"))
    }

    def unsubscribe(FromMsg fromMsg, ToMsg toMsg) {

    }
}
