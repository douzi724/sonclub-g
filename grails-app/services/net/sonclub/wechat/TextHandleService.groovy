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

    //查看帮助
    def commandHelp(FromMsg fromMsg, ToMsg toMsg) {
        toMsg.content =  messageSource.getMessage("wechat.text.help", null, new Locale("zh", "CN"))
    }

    //自定义方法
    def custom(FromMsg fromMsg, ToMsg toMsg) {
        if (fromMsg.action ==~ /@*@*@*/) {
           println("dsadasd")
        }
    }
}
