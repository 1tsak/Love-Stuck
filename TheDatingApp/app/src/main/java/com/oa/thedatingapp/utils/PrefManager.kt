package com.oa.thedatingapp.utils

import android.content.SharedPreferences

class PrefManager {
// Shared Preference Manager
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0
    private val PREF_NAME = "TheDatingApp"
    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    private val IS_LOGGED_IN = "IsLoggedIn"
    private val IS_PROFILE_CREATED = "IsProfileCreated"
    private val IS_PROFILE_UPDATED = "IsProfileUpdated"
    private val IS_PROFILE_COMPLETED = "IsProfileCompleted"
    private val IS_PROFILE_VERIFIED = "IsProfileVerified"

    // Constructor
    constructor(pref: SharedPreferences) {
        this.pref = pref
        editor = pref.edit()
    }

}