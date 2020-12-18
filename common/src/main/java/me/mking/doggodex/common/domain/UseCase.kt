package me.mking.doggodex.common.domain

import me.mking.doggodex.common.data.DataResult

interface UseCase<S, T> {
    suspend fun execute(input: S): DataResult<T>
}