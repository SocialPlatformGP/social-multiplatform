package com.gp.socialapp.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val DispatcherIO: CoroutineContext
    get() = Dispatchers.IO