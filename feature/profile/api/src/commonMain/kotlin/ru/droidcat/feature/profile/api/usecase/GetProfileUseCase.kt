package ru.droidcat.feature.profile.api.usecase

import ru.droidcat.core.coroutines.GetOrLoadUseCase
import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface GetProfileUseCase : GetOrLoadUseCase<UserInfoDTO>
