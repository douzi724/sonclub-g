package net.sonclub.shiro

import net.sonclub.common.UserFlag
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType

class User {
    String username
    String passwordHash

    UserType type
    UserStatus status
    UserFlag flagX

    String nickName
    String number

    String remark
    
    static hasMany = [ roles: Role, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
        nickName(nullable: true, unique: true)
        number(nullable: true, blank: false)
        remark nullable: true
    }
}
