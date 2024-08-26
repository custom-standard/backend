package com.example.custard.domain.common.file

import org.springframework.web.multipart.MultipartFile

interface FileStore {
    fun uploadFiles(path: String, files: List<MultipartFile>): List<File>
    fun deleteFiles(files: List<File>)
}