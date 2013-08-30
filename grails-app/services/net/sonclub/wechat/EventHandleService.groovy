package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.SysUser
import net.sonclub.command.ToMsg
import net.sonclub.common.UserFlag
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType

class EventHandleService {
    def messageSource

    //订阅
    def subscribe(FromMsg fromMsg, ToMsg toMsg) {
        toMsg.content = messageSource.getMessage("wechat.event.subscribe", null, new Locale("zh", "CN")) + messageSource.getMessage("wechat.text.unauth", null, new Locale("zh", "CN"))
    }

    //退订
    def unsubscribe(FromMsg fromMsg, ToMsg toMsg) {
        def user = SysUser.findByWechatId(toMsg.toUserName)
        if (user != null) {
            user.status = UserStatus.off
            user.save()
        }
    }
}
