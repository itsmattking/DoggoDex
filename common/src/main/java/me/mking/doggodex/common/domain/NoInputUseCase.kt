package me.mking.doggodex.common.domain

import me.mking.doggodex.common.data.DataResult

interface NoInputUseCase<T> {
    suspend fun execute(): DataResult<T>
}