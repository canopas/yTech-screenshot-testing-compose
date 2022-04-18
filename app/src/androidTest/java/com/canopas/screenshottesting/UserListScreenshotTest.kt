package com.canopas.screenshottesting

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import org.junit.Rule
import org.junit.Test

class UserListScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testUserList() {

        composeRule.setContent {

            PreviewUserList()
        }

        assertScreenshotMatchesGolden("Test", "user_list", composeRule.onRoot())
    }
}