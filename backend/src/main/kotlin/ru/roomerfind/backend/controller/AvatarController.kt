package ru.roomerfind.backend.controller

import io.ktor.http.ContentType
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import ru.roomerfind.backend.plugins.BadParametersException
import ru.roomerfind.backend.plugins.ResourceNotFoundException
import ru.roomerfind.backend.plugins.WrongFormatException
import ru.roomerfind.backend.repository.UserRepository
import java.io.File
import java.util.UUID

class AvatarController(
    private val userRepo: UserRepository,
) {

    fun getAvatar(filename: String?): File = runCatching {
        filename ?: throw BadParametersException()
        File("$AVATARS_PATH/$filename")
    }.onSuccess {
        if (!it.exists()) throw ResourceNotFoundException()
    }.getOrThrow()

    fun updateAvatarFromRaw(userId: Long, bytes: ByteArray, contentType: ContentType) {
        val extension = when (contentType) {
            ContentType.Image.PNG -> ".png"
            ContentType.Image.JPEG -> ".jpeg"
            else -> throw WrongFormatException()
        }
        val filename = UUID.randomUUID().toString() + extension
        userRepo.getAvatar(userId).takeIf { it != null }?.also { previous ->
            File("$AVATARS_PATH/$previous").takeIf { it.exists() }?.delete()
        }
        File("$AVATARS_PATH/$filename").apply {
            if (!parentFile.exists()) parentFile.mkdirs()
            if (!exists()) createNewFile()
            writeBytes(bytes)
            if (!userRepo.updateAvatar(userId, filename)) {
                delete()
                throw BadParametersException()
            }
        }
    }

    suspend fun updateAvatarFromMultiPart(userId: Long, multiPartData: MultiPartData) {
        var bytes: ByteArray? = null
        var extension: ContentType = ContentType.Any
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    bytes = part.streamProvider().readBytes()
                    extension = part.originalFileName?.fileExtension.asContentType
                }
                else -> {}
            }
            part.dispose()
        }
        bytes?.let {
            if (extension.match(ContentType.Image.Any)) {
                updateAvatarFromRaw(userId, it, extension)
            } else {
                throw WrongFormatException()
            }
        } ?: throw BadParametersException()
    }

    private val String.fileExtension: String? get() = split(".").lastOrNull()

    private val String?.asContentType: ContentType
        get() = when (this?.lowercase()) {
            "png" -> ContentType.Image.PNG
            "jpg", "jpeg" -> ContentType.Image.JPEG
            else -> ContentType.Any
        }
}

private const val AVATARS_PATH = "/avatars"
