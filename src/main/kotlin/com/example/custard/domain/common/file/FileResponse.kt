package com.example.custard.domain.common.file

class FileResponse (
    val fileUrl: String,
    val fileName: String,
    val fileType: String,
) {
    companion object {
        fun of(file: File): FileResponse {
            return FileResponse("${file.filePath}/${file.fileName}", file.fileOriginalName, file.fileType)
        }
    }
}