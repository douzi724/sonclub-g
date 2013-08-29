class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
        "/wechat_io"{
            controller = "wechatApi"
            action = [GET:"input", POST:"output"]
        }
		"/"(view:"/index")
		"500"(view:'/error')
        "403"(view:'/error')
	}
}
