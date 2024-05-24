package ru.droidcat.feature.finders.api.usecase

import ru.droidcat.feature.finders.api.model.Reaction

interface SendReactionUseCase {

    suspend fun sendReaction(reaction: Reaction): Result<Unit>
}
