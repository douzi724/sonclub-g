package net.sonclub

import net.sonclub.common.UserFlag
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType

class SysUser {
    String wechatId
    UserType type
    String name
    String number
    UserStatus status
    UserFlag flagX
    String remark

    static constraints = {
        wechatId unique: true
        name nullable: true
        number nullable: true
        remark nullable: true
    }
}
