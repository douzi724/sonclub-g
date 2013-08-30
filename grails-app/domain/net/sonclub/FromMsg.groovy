package net.sonclub

class FromMsg {

    String toUserName
    String fromUserName
    String msgType
    String msgId
    Date createTime
    String content
    String action

    static constraints = {
        action nullable: true
        msgId nullable: true
    }
}
