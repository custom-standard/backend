package com.example.custard.domain.common.file

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
@Profile("dev")
class LocalFileStore (
    private val fileRepository: FileRepository
) : FileStore {

    @Value("\${file.local_url}")
    private lateinit var localUrl: String

    override fun uploadFiles(path: String, files: List<MultipartFile>): List<File> {
        createDirectory(path)

        return files.filter { !it.isEmpty }.map { file ->
            val fileUUID = UUID.randomUUID()
            val fileExtension = file.originalFilename!!.split(".").last()
            val fileOriginalName = file.originalFilename!!.split(".").first()
            val fileName = "${fileUUID}.${fileExtension}"
            val fileUrl = "${localUrl}/${path}/${fileName}"

            val fileEntity = File(fileOriginalName, fileName, fileUUID, path, fileExtension)

            try {
                file.transferTo(java.io.File(fileUrl))
                fileRepository.save(fileEntity)
            } catch (e: Exception) {
                // TODO: Exception 처리
                throw RuntimeException("Failed to save file to local storage: $fileOriginalName", e)
            }
        }
    }

    override fun deleteFiles(file: List<File>) {
        file.forEach { file ->
            val fileUrl = "${localUrl}/${file.filePath}/${file.fileName}"
            java.io.File(fileUrl).delete()
        }
    }

    private fun createDirectory(path: String) {
        val directory = java.io.File("${localUrl}/${path}")
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}