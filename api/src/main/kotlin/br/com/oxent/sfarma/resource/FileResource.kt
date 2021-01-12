package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/file")
class FileResource @Autowired
constructor(private val fileService: FileService) {

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    fun handleFileUpload(file: MultipartFile) = fileService.storeFile(file)

    @GetMapping("/{f}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun foto(@PathVariable f: String): FileSystemResource? {
        return fileService.foto(f)
    }

    @PostMapping("/uploadFiles")
    fun uploadMultipleFiles(files: List<MultipartFile>) = fileService.storeFiles(files)


}