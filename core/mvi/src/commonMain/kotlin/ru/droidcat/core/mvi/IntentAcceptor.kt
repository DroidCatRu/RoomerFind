package ru.droidcat.core.mvi

interface IntentAcceptor<Intent : Any> {

    fun accept(intent: Intent)
}
