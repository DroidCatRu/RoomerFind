package ru.droidcat.core.mvi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher

expect val uiDispatcher: MainCoroutineDispatcher
val ioDispatcher = Dispatchers.IO