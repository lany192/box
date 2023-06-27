package com.lany192.box.network.data.model

/**
 * 首页-日报列表，响应实体类。
 *
 *
 * @since  2020/5/9
 */
data class Daily(
    val itemList: List<Item>,
    val count: Int,
    val total: Int,
    val nextPageUrl: String?,
    val adExist: Boolean
) : Model() {

    data class Item(
        val `data`: Data,
        val type: String,
        val tag: Any?,
        val id: Int = 0,
        val adIndex: Int
    )

    data class Data(
        val actionUrl: String?,
        val adTrack: Any,
        val backgroundImage: String,
        val content: Content,
        val dataType: String,
        val follow: Author.Follow?,
        val header: Header,
        val id: Int,
        val rightText: String,
        val subTitle: Any,
        val text: String,
        val titleList: List<String>,
        val type: String,
        val image: String,
        val label: Label?
    )

    data class Header(
        val actionUrl: String?,
        val cover: Any,
        val description: String,
        val font: Any,
        val icon: String?,
        val iconType: String,
        val id: Int,
        val label: Any,
        val labelList: Any,
        val rightText: Any,
        val showHateVideo: Boolean,
        val subTitle: Any,
        val subTitleFont: Any,
        val textAlign: String,
        val time: Long,
        val title: String
    )
}
