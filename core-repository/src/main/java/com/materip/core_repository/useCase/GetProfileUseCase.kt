package com.materip.core_repository.useCase

import com.materip.core_common.ResultResponse
import com.materip.core_model.response.GetProfileResponseDto
import com.materip.core_model.ui_model.Gender
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import java.time.LocalDate
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
){
    suspend operator fun invoke(): ResultResponse<GetProfileResponseDto> {
        val result = profileRepository.getProfile()
        val newResult = ResultResponse<GetProfileResponseDto>()
        if(result.data != null){
            val newBirth = LocalDate.now().year - result.data!!.birthYear
            val newGender = if(result.data!!.gender == Gender.MALE.name) "남성" else "여성"
            newResult.data = result.data!!.copy(
                birthYear = newBirth,
                gender = newGender
            )
        }
        return if(result.data != null) newResult else result
    }
}