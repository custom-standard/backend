package com.example.custard.domain.common.file

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
class File (
    fileOriginalName: String,
    fileName: String,
    fileUUID: UUID,
    filePath: String,
    fileType: String,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val fileOriginalName: String = fileOriginalName
    val fileName: String = fileName
    val fileUUID: UUID = fileUUID
    val filePath: String = filePath
    val fileType: String = fileType
}