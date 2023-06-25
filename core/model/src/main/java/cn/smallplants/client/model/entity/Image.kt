package cn.smallplants.client.model.entity

import com.github.lany192.utils.JsonUtils
import java.io.Serializable
import java.lang.Deprecated

@Deprecated
class Image : Serializable {
    var id: Long
    var name: String? = null
    var url: String
    var width: Int? = null
    var height: Int? = null
    var type: Int

    constructor(path: String) {
        type = 2
        id = 0L
        url = path
    }

    constructor(name: String?, url: String, width: Int, height: Int) {
        id = 0L
        type = 1
        this.name = name
        this.url = url
        this.width = width
        this.height = height
    }

    override fun toString(): String {
        return JsonUtils.object2json(this)
    }

    companion object {
        @JvmStatic
        val addImage: Image
            get() {
                val image = Image("add")
                image.type = Int.MAX_VALUE
                image.id = Long.MAX_VALUE
                image.name = "添加"
                image.height = 0
                image.width = 0
                return image
            }
    }
}