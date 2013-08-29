package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.command.ToMsg

class TextHandleService {

    def messageSource

    def joinMatch(FromMsg fromMsg, ToMsg toMsg) {
        println("fffffffffffff")
    }

    def exitMatch(FromMsg fromMsg, ToMsg toMsg) {
        println("fffffffffffff")
    }

    def commandHelp(FromMsg fromMsg, ToMsg toMsg) {
        toMsg.content =  messageSource.getMessage("wechat.text.help", null, new Locale("zh", "CN"))
    }
}
