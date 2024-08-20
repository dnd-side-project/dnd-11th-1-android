package com.materip.core_repository.useCase

import android.util.Log
import com.materip.core_common.ResultResponse
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.ui_model.Gender
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import java.time.LocalDate
import javax.inject.Inject

class GetProfileDetails @Inject constructor(
    private val profileRepository: ProfileRepository
){
    suspend operator fun invoke(): ResultResponse<GetProfileDetailsResponseDto> {
        val newResult = ResultResponse<GetProfileDetailsResponseDto>()
        val result = profileRepository.getProfileDetails()
        if (result.data != null){
            val newAge = LocalDate.now().year - result.data!!.birthYear
            val newGender = if(result.data!!.gender == Gender.MALE.name) "남성" else "여성"
            newResult.data = result.data!!.copy(
                birthYear = newAge,
                gender = newGender
            )
        }
        return if(result.data != null) newResult else result
    }
}