package net.sonclub

class FromMsg {

    String fromUser
    String toUser
    String msgType
    String msgKey
    Date createTime
    String content

    String action
    String remark

    static constraints = {
        msgKey nullable: true
        action nullable: true
        remark nullable: true
    }
}
