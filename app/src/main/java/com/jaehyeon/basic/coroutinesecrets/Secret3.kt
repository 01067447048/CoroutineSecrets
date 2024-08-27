package com.jaehyeon.basic.coroutinesecrets

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds


/**
 * Created by jaehyeon.
 * Date: 2024. 8. 27.
 */
suspend fun fetchNetwork(client: HttpClient) {
    while (true) {
        try {
            println("Polling network resource ...")

            val method = client.get("https://jsonplaceholder.org/posts")
            val response = client.execute(method)

            println("Received posts!")
            delay(30.seconds)
        } catch (e: Exception) {

            /**
             * 긴 동작의 함수가 취소 될 경우 취소에 대한 throw 를 재정의 해주지 않으면
             * 호출단 클래스가 삭제되지 않는 한 무한반복 한다.
             */

            if (e is CancellationException) throw e
            coroutineContext.ensureActive()

            println("Oops, something went wrong, make sure you're connected" +
                    "to the internet")
        }
    }
}