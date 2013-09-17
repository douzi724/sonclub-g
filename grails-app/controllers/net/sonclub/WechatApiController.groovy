package net.sonclub


class WechatApiController {
    def beforeInterceptor = [action: this.&authWechat, except: 'login']
    def ioApiService

    //接入验证
    def input() {
        render (params.echostr)
    }

    //信息回复
    def output() {
        def xmlMsg = new XmlSlurper().parseText(request.reader.text)
        params.put("toMsg", ioApiService.parseMsg(xmlMsg))
        render(contentType: "text/xml", view: "output.gsp")
    }

    //微信验证
    private authWechat() {
        def signature = [ grailsApplication.config.wechat.token, params.timestamp, params.nonce]
        signature.sort()
        //println(signature.join('').encodeAsSHA1())
        if (params.signature != signature.join('').encodeAsSHA1()) {
            response.status = 403
            return false
        }
    }
}
