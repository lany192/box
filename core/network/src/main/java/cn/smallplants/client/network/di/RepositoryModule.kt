package cn.smallplants.client.network.di

import cn.smallplants.client.network.api.ApiService
import cn.smallplants.client.network.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideArticleRepository(apiService: ApiService): ArticleRepository {
        return ArticleRepository(apiService)
    }

    @Provides
    fun provideAttentionRepository(apiService: ApiService): AttentionRepository {
        return AttentionRepository(apiService)
    }

    @Provides
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    fun provideBlackRepository(apiService: ApiService): BlackRepository {
        return BlackRepository(apiService)
    }

    @Provides
    fun provideCommentRepository(apiService: ApiService): CommentRepository {
        return CommentRepository(apiService)
    }

    @Provides
    fun provideDeviceRepository(apiService: ApiService): DeviceRepository {
        return DeviceRepository(apiService)
    }

    @Provides
    fun provideFeedbackRepository(apiService: ApiService): FeedbackRepository {
        return FeedbackRepository(apiService)
    }

    @Provides
    fun provideHomeRepository(apiService: ApiService): HomeRepository {
        return HomeRepository(apiService)
    }

    @Provides
    fun provideIndexRepository(apiService: ApiService): IndexRepository {
        return IndexRepository(apiService)
    }

    @Provides
    fun provideMessageRepository(apiService: ApiService): MessageRepository {
        return MessageRepository(apiService)
    }

    @Provides
    fun provideMettleRepository(apiService: ApiService): MettleRepository {
        return MettleRepository(apiService)
    }

    @Provides
    fun provideOtherRepository(apiService: ApiService): OtherRepository {
        return OtherRepository(apiService)
    }

    @Provides
    fun providePictureRepository(apiService: ApiService): PictureRepository {
        return PictureRepository(apiService)
    }

    @Provides
    fun providePlantRepository(apiService: ApiService): PlantRepository {
        return PlantRepository(apiService)
    }

    @Provides
    fun provideReportRepository(apiService: ApiService): ReportRepository {
        return ReportRepository(apiService)
    }

    @Provides
    fun provideSearchRepository(apiService: ApiService): SearchRepository {
        return SearchRepository(apiService)
    }

    @Provides
    fun provideUpvoteRepository(apiService: ApiService): UpvoteRepository {
        return UpvoteRepository(apiService)
    }

    @Provides
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }
}