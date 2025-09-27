package com.calyrsoft.ucbp1.features.profile.domain.usecase

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileEmail
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileName
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfilePhone
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileSummary
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProfileUseCaseTest {

    // Regla para que las corrutinas usen el TestDispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: IProfileRepository = mockk()

    @Test
    fun repo_success_returns_model() = runTest {
        // arrange
        val expected = ProfileModel(
            pathUrl    = "https://example.com/homer.jpg",
            name       = ProfileName("Homero J. Simpson"),
            email      = ProfileEmail("homero.simpson@springfieldmail.com"),
            cellphone  = ProfilePhone("+19395557422"),
            summary    = ProfileSummary("Inspector de seguridad en la Planta Nuclear.")
        )
        coEvery { repository.fetchData() } returns Result.success(expected)

        val useCase = GetProfileUseCase(repository)

        // act
        val deferred = async { useCase.invoke() }   // arranca la llamada suspend
        advanceTimeBy(3000)                         // avanzamos el tiempo del delay(3000)
        val result = deferred.await()

        // assert
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
        coVerify(exactly = 1) { repository.fetchData() }
    }

    @Test
    fun repo_failure() = runTest {
        // arrange
        val boom = RuntimeException("boom")
        coEvery { repository.fetchData() } returns Result.failure(boom)
        val useCase = GetProfileUseCase(repository)

        // act
        val result = useCase.invoke()

        // assert
        assertTrue(result.isFailure)
        assertEquals("boom", result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { repository.fetchData() }
    }
}