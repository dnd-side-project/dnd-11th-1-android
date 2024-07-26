package com.materip.core_common

sealed interface ErrorState {
    data class NoAuthError(
        val generalError: Pair<Boolean, String?>
    ): ErrorState
    data class AuthError(
        val invalidTokenError: Boolean,
        val notFoundTokenError: Boolean,
        val generalError: Boolean
    ): ErrorState
    data object Loading: ErrorState
}