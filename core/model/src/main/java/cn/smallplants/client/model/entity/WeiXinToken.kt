package cn.smallplants.client.model.entity

import java.lang.Deprecated

@Deprecated
class WeiXinToken {
    var access_token: String? = null
    var expires_in: String? = null
    var refresh_token: String? = null
    var openid: String? = null
    var scope: String? = null
}