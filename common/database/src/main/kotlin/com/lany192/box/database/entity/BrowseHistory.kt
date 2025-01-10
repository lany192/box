package com.lany192.box.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * 搜索记录表  , indices = [Index(value = ["keyword"])] 不创建索引，数据少
 */
@Entity(tableName = "tb_browse_history")
class BrowseHistory : BaseTable() {
    /**
     * 流量类型
     */
    @ColumnInfo(name = "type")
    var type: Int = 0

    /**
     * 目标记录id
     */
    @ColumnInfo(name = "record_id")
    var recordId: Long = 0
}