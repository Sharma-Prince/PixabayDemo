package com.example.android.pixabaydemo.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ImageApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PxImageDatabase.getDatabase(this) }

    val repository by lazy { PxImageRepository(database.getImageDao()) }
}
