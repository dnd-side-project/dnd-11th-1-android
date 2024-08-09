package com.materip.core_common

sealed interface ErrorState {
    data class NoAuthError(
        val generalError: Pair<Boolean, String?>
    ): ErrorState {
        fun inInvalid(): Boolean{
            return generalError.first
        }
    }
    data class AuthError(
        val invalidTokenError: Boolean,
        val notFoundTokenError: Boolean,
        val generalError: Pair<Boolean, String?>
    ): ErrorState {
        fun isInvalid(): Boolean{
            return invalidTokenError || notFoundTokenError || generalError.first
        }
    }
    data object Loading: ErrorState
}