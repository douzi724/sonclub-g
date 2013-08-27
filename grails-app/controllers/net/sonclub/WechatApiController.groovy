package net.sonclub

class WechatApiController {

    def beforeInterceptor = [action: this.&authWechat, except: 'login']

    def ioApiService

    private authWechat() {
        def signature = [ grailsApplication.config.wechat.token, params.timestamp, params.nonce]
        signature.sort()
        if (params.signature != signature.join('').encodeAsSHA1()) {
            response.status = 403
            return false
        }
    }

    def input() {
        render (params.echostr)
    }

    def output() {
        def message = new XmlSlurper().parseText(request.reader.text)
        ioApiService.handleMsg(message, params)
        render(contentType: "text/xml", view: "output.gsp")
    }
}
