package com.calyrsoft.ucbp1.features.profile.data.repository

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileEmail
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileName
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfilePhone
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileSummary
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository

class ProfileRepository : IProfileRepository {
    override fun fetchData(): Result<ProfileModel> {
        return Result.success(
            ProfileModel(
                pathUrl = "https://www.viaempresa.cat/uploads/s1/43/99/69/homer.jpg",
                name = ProfileName("Homero J. Simpson"),
                email = ProfileEmail("homero.simpson@springfieldmail.com"),
                cellphone = ProfilePhone("+19395557422"),
                summary = ProfileSummary(
                    "Ciudadano de Springfield y dedicado inspector de seguridad en la Planta Nuclear."
                )
            )
        )
    }
}