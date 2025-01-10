package com.lany192.box.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * 搜索记录表  , indices = [Index(value = ["keyword"])] 不创建索引，数据少
 */
@Entity(tableName = "tb_search_history")
class SearchHistory : BaseTable() {
    /**
     * 搜索关键字
     */
    @ColumnInfo(name = "keyword")
    var keyword: String? = null
}