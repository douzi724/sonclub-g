package net.sonclub.wechat

import net.sonclub.FromMsg
import net.sonclub.common.UserStatus
import net.sonclub.shiro.User

class EventHandleService {
    def messageSource

    //订阅
    def subscribe(FromMsg fromMsg) {
        return messageSource.getMessage("wechat.event.subscribe", null, new Locale("zh", "CN")) +
                messageSource.getMessage("wechat.exception.unauth", null, new Locale("zh", "CN"))
    }

    //退订
    def unsubscribe(FromMsg fromMsg) {
        def user = User.findByUsername(fromMsg.fromUser)
        if (user != null) {
            user.status = UserStatus.off
            user.save()
        }
    }
}
