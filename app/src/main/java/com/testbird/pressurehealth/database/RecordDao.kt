package com.testbird.pressurehealth.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.testbird.pressurehealth.model.RecordEntity

@Dao
abstract class RecordDao {
    @Insert
    abstract fun insert(recordEntity: RecordEntity)
    @Update
    abstract fun update(recordEntity: RecordEntity)

    @Query("select * from record order by time desc")
    abstract fun queryAll():MutableList<RecordEntity>
}