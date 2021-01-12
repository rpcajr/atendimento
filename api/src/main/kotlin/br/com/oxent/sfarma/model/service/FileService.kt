package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.JFFile
import getURLImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*


@Service
@PropertySource("classpath:oxente.properties")
class FileService @Autowired constructor(environment: Environment) {
    val env: Environment = environment

    @Throws(IOException::class)
    fun storeFile(file: MultipartFile): JFFile {
        val s = file.originalFilename?.let { com.google.common.io.Files.getFileExtension(it) }
        val fileName = "${UUID.randomUUID()}.$s"
        val filePath = Paths.get("${env.getProperty("file_directory")}/$fileName")
        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        return JFFile(
                nome = fileName,
                url = getURLImage(fileName),
                descricao = file.originalFilename
        )
    }

    @Throws(IOException::class)
    fun storeFiles(file: List<MultipartFile>): List<JFFile> {
        val temp: MutableList<JFFile> = mutableListOf()
        for (f in file) {
            temp.add(storeFile(f))
        }
        return temp
    }

    fun foto(foto: String): FileSystemResource? {
        val file = File("${env.getProperty("file_directory")}/$foto")
        if (file.exists()) {
            return FileSystemResource(file)
        }
        return null
    }


}