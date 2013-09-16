import net.sonclub.shiro.Role

class BootStrap {
    //def shiroSecurityService
    def init = { servletContext ->
        //def sessionStorageEvaluator = new HybridSessionStorageEvaluator()
        //shiroSecurityManager.subjectDAO.sessionStorageEvaluator = sessionStorageEvaluator

        // Create the normal role
        def normalRole = Role.findByName('wc_normal') ?:
            new Role(
                    name: 'wc_normal',
                    remark: "微信普通用户",
                    permissions: ["comm:event:subscribe",
                            "comm:event:unsubscribe",
                            "comm:text:commandHelp"]
            ).save(flush: true, failOnError: true)

        // Create the admin role
        def adminRole = Role.findByName('su_admin') ?:
            new Role(name: 'su_admin', remark: "管理员").save(flush: true, failOnError: true)

        // Create an admin user
        /*def adminUser = User.findByUsername('admin') ?:
            new User(username: "admin",
                    passwordHash: shiroSecurityService.encodePassword('password'))
                    .save(flush: true, failOnError: true)*/

        // Add roles to the admin user
        /*assert normalRole.addToPermissions(adminRole)
                .addToRoles(userRole)
                .save(flush: true, failOnError: true)*/

    }
    def destroy = {
    }
}
