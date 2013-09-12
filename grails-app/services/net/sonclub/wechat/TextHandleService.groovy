package net.sonclub.wechat

import net.sonclub.FromMsg

class TextHandleService {

    def messageSource

    def joinMatch(FromMsg fromMsg) {
        println("fffffffffffff")
    }

    def exitMatch(FromMsg fromMsg) {
        println("fffffffffffff")
    }

    //查看帮助
    def commandHelp(FromMsg fromMsg) {
        return  messageSource.getMessage("wechat.text.help", null, new Locale("zh", "CN"))
    }

    //自定义方法
    /*def custom(FromMsg fromMsg) {
        if (fromMsg.action ==~ /@*@*@*//*) {
           println("dsadasd")
        }
    }*/
}
