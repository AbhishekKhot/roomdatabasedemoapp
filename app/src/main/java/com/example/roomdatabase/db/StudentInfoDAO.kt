package com.example.roomdatabase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentInfoDAO {

    @Insert
    suspend fun insertStudentInfo(subscriber: Student) : Long

    @Update
    suspend fun updateStudentInfo(subscriber: Student) : Int

    @Delete
    suspend fun deleteStudentInfo(subscriber: Student) : Int

    @Query("DELETE FROM student_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM student_data_table")
    fun getAllStudentInformation():LiveData<List<Student>>
}