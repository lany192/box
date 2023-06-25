package cn.smallplants.client.event

import cn.smallplants.client.model.types.TargetType


class LikeEvent(var type: TargetType, var targetId: Long, var isLike: Boolean)