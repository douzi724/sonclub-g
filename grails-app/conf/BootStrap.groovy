import net.sonclub.shiro.Role
import net.sonclub.shiro.User

class BootStrap {
    def shiroSecurityService
    def init = { servletContext ->
        //def sessionStorageEvaluator = new HybridSessionStorageEvaluator()
        //shiroSecurityManager.subjectDAO.sessionStorageEvaluator = sessionStorageEvaluator

        // Create the normal role
        def normalRole = Role.findByName('sc_normal') ?:
            new Role(
                    name: 'sc_normal',
                    remark: "普通用户",
                    permissions:
            ).save(flush: true, failOnError: true)

        // Create the admin role
        def adminRole = Role.findByName('sc_admin') ?:
            new Role(name: 'sc_admin', remark: "管理员").save(flush: true, failOnError: true)

        // Create an admin user
        /*def adminUser = User.findByUsername('admin') ?:
            new User(username: "admin",
                    passwordHash: shiroSecurityService.encodePassword('password'))
                    .save(flush: true, failOnError: true)*/

        // Add roles to the admin user
        assert normalRole.addToPermissions(adminRole)
                .addToRoles(userRole)
                .save(flush: true, failOnError: true)

    }
    def destroy = {
    }
}
