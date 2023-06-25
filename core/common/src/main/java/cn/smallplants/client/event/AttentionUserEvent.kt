package cn.smallplants.client.event


class AttentionUserEvent(
    private val uid: Long,
    private val authorId: Long,
    private val status: Int
) {
    val isAttention: Boolean
        get() = status == 0 || status == 2
}