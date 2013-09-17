package net.sonclub

import net.sonclub.common.UserFlag
import net.sonclub.shiro.User

class Player {
    User user

    String number
    UserFlag userFlag

    static constraints = {
        number(nullable: true, blank: false)
    }
}
