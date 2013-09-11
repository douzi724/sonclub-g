package net.sonclub.shiro

import org.apache.shiro.mgt.SessionStorageEvaluator
import org.apache.shiro.subject.Subject
import org.apache.shiro.web.util.WebUtils

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-9-2
 * Time: 下午5:38
 * To change this template use File | Settings | File Templates.
 */
class HybridSessionStorageEvaluator implements SessionStorageEvaluator{

    @Override
    boolean isSessionStorageEnabled(Subject subject) {
        boolean enabled = false;

        if(WebUtils.isWeb(Subject)) {

            HttpServletRequest request = WebUtils.getHttpRequest(subject);

            //set 'enabled' based on the current request.

        } else {

            //not a web request - maybe a RMI or daemon invocation?

            //set 'enabled' another way …

        }

        return enabled;
    }
}
