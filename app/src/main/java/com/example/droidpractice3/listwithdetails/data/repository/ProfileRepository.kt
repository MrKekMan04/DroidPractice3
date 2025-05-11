package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.data.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalTime


interface IProfileRepository {

    suspend fun getProfile(): ProfileEntity?

    suspend fun setProfile(
        avatarURL: String,
        name: String,
        documentURL: String,
        notificationTime: LocalTime
    ): ProfileEntity

    suspend fun observeProfile(): Flow<ProfileEntity>
}
