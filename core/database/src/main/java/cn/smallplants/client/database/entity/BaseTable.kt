package cn.smallplants.client.database.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * 基类表，每个表都要有id主键字段，自动增长
 */
abstract class BaseTable : Serializable {
    /**
     * 主键，自动增长
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null
}