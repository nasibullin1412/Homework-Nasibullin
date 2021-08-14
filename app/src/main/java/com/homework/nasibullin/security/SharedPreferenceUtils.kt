package com.homework.nasibullin.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


object SharedPreferenceUtils {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    private const val myPrefsName = "MyPrefsFile"
    private const val passwordKey = "user_mame_password"

    /**
     * set encrypted password to shared preference
     * @param password is password, which need to encrypt and add to shared preference
     * @param context app context
     */
    fun setPassword(password: String, context: Context) {
        val sharedPreferences = EncryptedSharedPreferences
                .create(myPrefsName,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val sharedPrefsEditor = sharedPreferences.edit()
        sharedPrefsEditor.putString(passwordKey, password)
        sharedPrefsEditor.apply()
    }

    /**
     * get decrypted password from shared preference
     * @param context app context
     */
    fun getPassword(context: Context): String? {
        val sharedPreferences = EncryptedSharedPreferences
                .create(myPrefsName,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        return sharedPreferences.getString(passwordKey, "Not Available")
    }

}