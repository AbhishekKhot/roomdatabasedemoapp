package com.example.roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentInformationDatabase : RoomDatabase() {
    abstract val subscriberDAO: StudentInfoDAO

    companion object {
        @Volatile
        private var INSTANCE: StudentInformationDatabase? = null
        fun getInstance(context: Context): StudentInformationDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentInformationDatabase::class.java,
                        "student_info_database"
                    ).build()
                }
                return instance
            }
        }
    }
}
