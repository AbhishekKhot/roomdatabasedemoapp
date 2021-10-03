package com.example.roomdatabase.repository

import com.example.roomdatabase.db.Student
import com.example.roomdatabase.db.StudentInfoDAO

class StudentInformationRepository(private val dao: StudentInfoDAO) {

    val studentInfo = dao.getAllStudentInformation()

    suspend fun insert(student: Student): Long {
        return dao.insertStudentInfo(student)
    }

    suspend fun update(student: Student): Int {
        return dao.updateStudentInfo(student)
    }

    suspend fun delete(student: Student): Int {
        return dao.deleteStudentInfo(student)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}