package net.sonclub.shiro

class Role {
    String name
    String remark

    static hasMany = [ users: User, permissions: String ]
    static belongsTo = User

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        remark nullable: true
    }
}
