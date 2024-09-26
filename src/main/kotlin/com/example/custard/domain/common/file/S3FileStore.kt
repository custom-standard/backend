package com.example.custard.domain.common.file

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Component
@Profile("prod")
class S3FileStore (
    private val amazonS3: AmazonS3,
    private val fileRepository: FileRepository
) : FileStore {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    override fun uploadFiles(path: String, files: List<MultipartFile>): List<File> {
        return files.filter { !it.isEmpty }.map { file ->
            val fileUUID = UUID.randomUUID()
            val fileExtension = file.originalFilename!!.split(".").last()
            val fileOriginalName = file.originalFilename!!.split(".").first()
            val fileName = "${fileUUID}.${fileExtension}"

            val fileEntity = File(fileOriginalName, fileName, fileUUID, path, fileExtension)

            val metadata = ObjectMetadata().apply {
                contentLength = file.size
                contentType = file.contentType
            }

            try {
                amazonS3.putObject(bucket, "${path}/${fileName}", file.inputStream, metadata)
                fileRepository.save(fileEntity)
            } catch (e: IOException) {
                // TODO: Exception 처리
                throw RuntimeException("Failed to save file to S3: $fileOriginalName", e)
            }
        }
    }

    override fun deleteFiles(files: List<File>) {
        files.forEach { file ->
            val filePath = "${file.filePath}/${file.fileName}"
            try {
                amazonS3.deleteObject(bucket, filePath)
            } catch (e: Exception) {
                // TODO: Exception 처리
                throw RuntimeException("Failed to delete file from S3: $filePath", e)
            }
        }
    }
}