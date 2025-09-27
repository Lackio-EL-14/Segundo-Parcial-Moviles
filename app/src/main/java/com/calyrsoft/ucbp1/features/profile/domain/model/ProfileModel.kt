package com.calyrsoft.ucbp1.features.profile.domain.model



@JvmInline
value class ProfileName(val value: String) {
    init {
        require(value.isNotBlank()) { "El nombre no puede estar vacío" }
    }
}



@JvmInline
value class ProfileEmail(val value: String) {
    init {
        require(android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            "El email no es válido"
        }
    }
}

@JvmInline
value class ProfilePhone(val value: String) {
    init {
        require(value.matches(Regex("^[+]?[0-9]{7,15}$"))) {
            "El número de teléfono no es válido"
        }
    }
}
@JvmInline
value class ProfileSummary(val value: String)


data class ProfileModel(
    val pathUrl: String,
    val name: ProfileName,
    val email: ProfileEmail,
    val cellphone: ProfilePhone,
    val summary: ProfileSummary
)