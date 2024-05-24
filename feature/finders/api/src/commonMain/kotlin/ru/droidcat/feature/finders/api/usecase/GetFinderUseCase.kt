package ru.droidcat.feature.finders.api.usecase

import ru.droidcat.core.coroutines.GetOrLoadUseCase
import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface GetFinderUseCase : GetOrLoadUseCase<UserInfoDTO>
