package ru.roomerfind.backend.utils

import io.ktor.server.config.ApplicationConfig
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoTool(
    config: ApplicationConfig,
) {

    private val iv = config.property("encrypt.iv").getString().toByteArray()
    private val key = config.property("encrypt.key").getString().toByteArray()

    fun encryptCBC(password: String): String {
        val iv = IvParameterSpec(iv)
        val keySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        val crypt = cipher.doFinal(password.toByteArray())
        return String(Base64.getEncoder().encode(crypt))
    }

    fun decryptCBC(password: String): String {
        val decodedByte: ByteArray = Base64.getDecoder().decode(password)
        val iv = IvParameterSpec(iv)
        val keySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        return String(cipher.doFinal(decodedByte))
    }
}
