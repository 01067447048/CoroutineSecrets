package com.jaehyeon.basic.coroutinesecrets

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Created by jaehyeon.
 * Date: 2024. 8. 27.
 */
suspend fun createTempFile(context: Context) {
    val file = File(context.filesDir, "my-file.txt")
    try {
        writeLotsOfLines(file)
    } finally {
        println("finally block")
//        deleteFile(file)
        /**
         * cancel 함수로 finally 블록엔 들어오지만 같은 Scope 자체가 취소되어
         * deleteFile 함수가 실행 되지 않는다.
         * 그래서 NonCancellable 로 deleteFile 함수는 cancel 시키지 않고 반드시 실행.
         */
        withContext(NonCancellable) {
            deleteFile(file)
        }
    }
}

private suspend fun writeLotsOfLines(file: File) {
    withContext(Dispatchers.IO) {
        repeat(100_000) {
            file.appendText("Just a temp file.")
            ensureActive()
        }
    }
}

private suspend fun deleteFile(file: File) {
    withContext(Dispatchers.IO) {
        file.delete()
        println("deleteFile")
    }
}