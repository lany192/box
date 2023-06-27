package com.lany192.box.network.data.model

import com.google.gson.annotations.SerializedName

/**
 * 通知-推送消息列表，响应实体类。
 *
 *
 * @since  2020/5/2
 */
data class PushMessage(
    @SerializedName("messageList") val itemList: List<Message>,
    val nextPageUrl: String?,
    val updateTime: Long
) : Model() {

    data class Message(
        val actionUrl: String,
        val content: String,
        val date: Long,
        val icon: String?,
        val id: Int,
        val ifPush: Boolean,
        val pushStatus: Int,
        val title: String?,
        val uid: Any,
        val viewed: Boolean
    )
}

