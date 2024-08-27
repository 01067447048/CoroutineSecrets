package com.jaehyeon.basic.coroutinesecrets.ui.theme

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.io.ByteArrayOutputStream

/**
 * Created by jaehyeon.
 * Date: 2024. 8. 27.
 */
class ImageLoader(
    private val context: Context,
    private val dispatchers: DispatcherProvider
) {

    suspend fun readImageFromUri(contentUri: Uri): ByteArray {
//        return withContext(Dispatchers.IO) {
//            context.contentResolver.openInputStream(contentUri)?.use {
//                it.readBytes()
//            } ?: byteArrayOf()
//        }
        return withContext(dispatchers.io) {
            context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    var byte = inputStream.read()
                    while (byte != -1) {
                        outputStream.write(byte)
                        byte = inputStream.read()

                        /**
                         * 동작 보장과, 용량이 클시 양보를 위한 yield
                         */
                        ensureActive()
                        yield()
                    }
                    outputStream.toByteArray()
                }
            } ?: byteArrayOf()
        }
    }

}

/**
 * 해당 방법을 통해
 * Coroutine Scope 제한
 */

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

object StandardDispatcherProvider: DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}
