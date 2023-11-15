package io.astrum.mindsync.app.common.model

import io.astrum.mindsync.app.common.model.theme.DarkThemeConfig
import io.astrum.mindsync.app.common.model.theme.ThemeBrand

data class UserData(
    val bookmarkedNewsResources: Set<String>,
    val followedTopics: Set<String>,
    val followedAuthors: Set<String>,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig
)
