package com.example.droidpractice3.listwithdetails.data.repository

import androidx.datastore.core.DataStore
import com.example.droidpractice3.listwithdetails.data.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import org.threeten.bp.LocalTime

class ProfileRepository : IProfileRepository {

    private val dataStore: DataStore<ProfileEntity> by inject(
        DataStore::class.java,
        named("profile")
    )

    override suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    override suspend fun setProfile(
        avatarURL: String,
        name: String,
        documentURL: String,
        notificationTime: LocalTime
    ): ProfileEntity = dataStore.updateData {
        it.toBuilder().apply {
            this.avatarUri = avatarURL
            this.name = name
            this.documentUrl = documentURL
            this.notificationTime = notificationTime.toString()
        }.build()
    }

    override suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data
}
