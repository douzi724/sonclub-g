package net.sonclub.shiro

import net.sonclub.Player
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType

class User {
    String username
    String passwordHash

    UserType type
    UserStatus status

    String nickName
    String remark

    //static hasOne = [ player: Player ]
    
    static hasMany = [ roles: Role, permissions: String ]

    static constraints = {
        username(blank: false, unique: true)
        nickName(nullable: true, unique: true)
        remark nullable: true
        //player unique: true
    }
}
