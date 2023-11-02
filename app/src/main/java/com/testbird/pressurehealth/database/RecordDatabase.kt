package com.testbird.pressurehealth.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.testbird.pressurehealth.appContext
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class RecordDatabase :RoomDatabase(){
    abstract fun recordDao():RecordDao

    companion object{
        private var instance:RecordDatabase? = null
        fun getDatabase():RecordDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(appContext,RecordDatabase::class.java,"recordDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(object :Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            "database create success".log()
                        }
                    }).build()
            }

            return instance!!
        }
    }
}