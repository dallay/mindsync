package io.astrum.mindsync.app.di

import androidx.compose.material3.SnackbarHostState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import io.astrum.mindsync.app.common.model.PetCategory
import io.astrum.mindsync.app.data.repositories.LastSearchAdsMockRepository
import io.astrum.mindsync.app.data.repositories.NearMeAdsMockRepository
import io.astrum.mindsync.app.data.repositories.ProfileRepository
import io.astrum.mindsync.app.data.repositories.SessionRepository
import io.astrum.mindsync.app.data.usecases.GetLastSearchUseCase
import io.astrum.mindsync.app.data.usecases.GetNearMeAdsUseCase
import io.astrum.mindsync.app.data.usecases.GetSearchUseCase
import io.astrum.mindsync.app.feature.debugmenu.repositories.GlobalAppSettingsRepository
import io.astrum.mindsync.app.feature.debugmenu.viewmodel.DebugMenuViewModel
import io.astrum.mindsync.app.feature.petupload.repositories.PetUploadPublishRepository
import io.astrum.mindsync.app.feature.petupload.usecases.PetUploadUseCase
import io.astrum.mindsync.app.feature.petupload.viewmodel.PetUploadViewModel
import io.astrum.mindsync.app.feature.profile.viewmodels.ProfileDetailViewModel
import io.astrum.mindsync.app.feature.search.repositories.LastSearchesRepository
import io.astrum.mindsync.app.feature.search.repositories.PetsFromSearchRepository
import io.astrum.mindsync.app.localization.getCurrentLocalization
import io.astrum.mindsync.app.platform.MindsyncEventTracker
import io.astrum.mindsync.app.platform.RootNavigatorRepository
import io.astrum.mindsync.app.platform.RootSnackbarHostStateRepository
import io.astrum.mindsync.app.platform.ServiceClient
import io.astrum.mindsync.app.ui.screens.viewmodel.HomeScreenViewModel
import io.astrum.mindsync.app.ui.screens.viewmodel.ProfileViewModel
import io.astrum.mindsync.app.ui.screens.viewmodel.SearchListingViewModel
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    factory { (navigator: Navigator) ->
        HomeScreenViewModel(navigator, get(), get(), get(), get(), get(), get(), get(), get(), get())
    }
    factory {
        PetUploadViewModel(get(), get(), get(), get())
    }
    factory { (navigator: Navigator) ->
        ProfileViewModel(navigator, get(), get(), get())
    }

    factory { (userId: Int, navigator: Navigator) ->
        ProfileDetailViewModel(userId, navigator, get(), get())
    }

    factory { (searchId: Int?, petCategory: PetCategory?, navigator: Navigator) ->
        SearchListingViewModel(searchId, petCategory, get(), navigator)
    }

    factory {
        DebugMenuViewModel(get())
    }

    single { (navigator: Navigator, tabNavigator: TabNavigator) ->
        RootNavigatorRepository(navigator, tabNavigator)
    }

    single { (snackbarHostState: SnackbarHostState) ->
        RootSnackbarHostStateRepository(snackbarHostState)
    }

    single {
        MindsyncEventTracker()
    }

    factoryOf(::GlobalAppSettingsRepository)
    factoryOf(::GetSearchUseCase)
    singleOf(::NearMeAdsMockRepository)
    factoryOf(::GetNearMeAdsUseCase)

    singleOf(::LastSearchesRepository)
    singleOf(::LastSearchAdsMockRepository)
    singleOf(::PetsFromSearchRepository)
    factoryOf(::GetLastSearchUseCase)

    singleOf(::SessionRepository)
    singleOf(::ProfileRepository)
    singleOf(::Settings)
    single { ServiceClient }
    single { getCurrentLocalization() }

    factoryOf(::PetUploadUseCase)
    singleOf(::PetUploadPublishRepository)
}
