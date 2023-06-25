package cn.smallplants.client.model.entity

import java.io.Serializable
import java.lang.Deprecated

@Deprecated
class Place : Serializable {
    var provinceId: Long = 0
    var provinceName: String? = null
    var cityId: Long = 0
    var cityName: String? = null
    var areaId: Long = 0
    var areaName: String? = null
}