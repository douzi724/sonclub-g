package net.sonclub.exception

import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib

/**
 * Created with IntelliJ IDEA.
 * User: nemo
 * Date: 13-9-4
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
class BaseException extends Exception{
    private static ApplicationTagLib messageTag = new ApplicationTagLib()

    //消息代码，与国际化文件中对应
    protected String code
    //消息参数，多个参数默认逗号分隔
    protected Object[] args

    @Override
    public String getMessage() {
        return messageTag.message(code:code, args:args, locale: new Locale("zh", "CN"))
    }
}
