package cn.smallplants.client.model.entity

import java.lang.Deprecated

@Deprecated
class QQToken {
    /**
     * ret : 0
     * openid : 402E6B72068257D15B0A5E142AD1DB06
     * access_token : D44314F87AB1CE8F3F673EBF54B02AF1
     * pay_token : 341A24821FDCB2978E5FF3CE7C7A9692
     * expires_in : 7776000
     * pf : desktop_m_qq-10000144-android-2002-
     * pfkey : e50421ac7885b3e7c61879ea0d5d1045
     * msg :
     * login_cost : 97
     * query_authority_cost : 186
     * authority_cost : 0
     * expires_time : 1537702318985
     */
    var ret = 0
    var openid: String? = null
    var access_token: String? = null
    var pay_token: String? = null
    var expires_in = 0
    var pf: String? = null
    var pfkey: String? = null
    var msg: String? = null
    var login_cost = 0
    var query_authority_cost = 0
    var authority_cost = 0
    var expires_time: Long = 0
}