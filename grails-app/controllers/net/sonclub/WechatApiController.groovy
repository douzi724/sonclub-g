package net.sonclub

class WechatApiController {

    def beforeInterceptor = [action: this.&authWechat, except: 'login']

    def ioApiService

    private authWechat() {
        def signature = [ grailsApplication.config.wechat.token, params.timestamp, params.nonce]
        signature.sort()
        //println(signature.join('').encodeAsSHA1())
        if (params.signature != signature.join('').encodeAsSHA1()) {
            response.status = 403
            return false
        }
    }

    def input() {
        render (params.echostr)
    }

    def output() {
        def inMsg = new XmlSlurper().parseText(request.reader.text)
        ioApiService.handleMsg(inMsg, params)
        render(contentType: "text/xml", view: "output.gsp")
    }
}
