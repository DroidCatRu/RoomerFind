package ru.droidcat.feature.finders.internal.usecase

import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.feature.finders.api.model.Reaction
import ru.droidcat.feature.finders.api.usecase.SendReactionUseCase
import ru.droidcat.feature.finders.internal.data.RemoteFindersRepository

class SendReactionUseCaseImpl(
    private val remoteFindersRepository: RemoteFindersRepository,
) : SendReactionUseCase, AbstractSuspendUseCase() {

    override suspend fun sendReaction(reaction: Reaction): Result<Unit> {
        return when (reaction) {
            is Reaction.Like -> remoteFindersRepository.like()
            is Reaction.Dislike -> remoteFindersRepository.dislike()
        }
    }
}
