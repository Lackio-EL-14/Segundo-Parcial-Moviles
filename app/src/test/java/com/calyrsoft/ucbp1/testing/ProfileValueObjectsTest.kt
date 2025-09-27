package com.calyrsoft.ucbp1.features.profile.domain.model

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail

class ProfileValueObjectsTest {

    // -------- ProfileName --------
    @Test
    fun ProfileNameaceptanombresnovacios() {
        val vo = ProfileName("Homero")
        assertEquals("Homero", vo.value)
    }

    @Test
    fun ProfileNamefallavacio() {
        try {
            ProfileName("")
            fail("Debió lanzar IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("no puede estar vacío"))
        }
    }

    // -------- ProfilePhone --------
    @Test
    fun ProfilePhoneaceptaformatointernacionalperosolosimple() {
        val vo = ProfilePhone("+19395557422")
        assertEquals("+19395557422", vo.value)
    }

    @Test
    fun ProfilePhonefallasiformatoesinvalidogeneral() {
        try {
            ProfilePhone("abc-123")
            fail("Debió lanzar IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("no es válido"))
        }
    }

    // -------- ProfileSummary --------
    @Test
    fun ProfileSummaryaceptacualquiercalsedetexto() {
        val vo = ProfileSummary("Inspector de la planta nuclear")
        assertEquals("Inspector de la planta nuclear", vo.value)
    }
}