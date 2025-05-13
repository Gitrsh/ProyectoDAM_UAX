package com.rsh.app_jornet

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavegacionTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        // Mock estático de FirebaseAuth.getInstance()
        mockkStatic(FirebaseAuth::class)

        val mockUser = mockk<FirebaseUser>()
        every { mockUser.uid } returns "usuarioDePrueba"
        every { mockUser.isEmailVerified } returns true
        every { mockUser.email } returns "test@ejemplo.com"

        val mockAuth = mockk<FirebaseAuth>()
        every { mockAuth.currentUser } returns mockUser
        every { FirebaseAuth.getInstance() } returns mockAuth
    }

    @Test
    fun navegarACrearParteDesdeHome() {
        composeRule.onNodeWithTag("botonCrearParte").performClick()
        composeRule.onNodeWithText("PARTE DIARIO").assertExists()
    }
}