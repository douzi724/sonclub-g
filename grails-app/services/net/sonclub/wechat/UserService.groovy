package net.sonclub.wechat

import net.sonclub.common.UserFlag
import net.sonclub.common.UserStatus
import net.sonclub.common.UserType
import net.sonclub.shiro.Role
import net.sonclub.shiro.User
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.Subject

class UserService {
    def shiroSecurityService
    def shiroSecurityManager
    /**
     * 检查是否为新用户
     * @param fromMsg
     */
    def checkNewUser(String username) {
        def user = User.findByUsername(username) ?:
            new User(
                    username: username,
                    passwordHash: shiroSecurityService.encodePassword('sonclub'),
                    type: UserType.wechat,
                    status: UserStatus.unauth,
                    flagX: UserFlag.player,
                    roles: Role.findByName("wc_normal")
            ).save(flush:true, failOnError: true)
    }

    public Subject buildSubject(String userIdentity) {
        PrincipalCollection principals = new SimplePrincipalCollection(userIdentity, "DbRealm");
        Subject s =  new Subject.Builder(shiroSecurityManager).principals(principals).buildSubject();
    }
}
