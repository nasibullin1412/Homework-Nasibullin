package com.homework.nasibullin.security

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.homework.nasibullin.App
import com.homework.nasibullin.fragments.MainFragment.Companion.ALL_GENRE


object SharedPreferenceUtils {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    private const val SHARED_PREF_NAME = "Genres"
    private const val MY_PREFS_ENCRYPTED_NAME = "MyPrefsFile"
    private const val PASSWORD_KEY = "user_mame_password"

    /**
     * set encrypted password to shared preference
     * @param password is password, which need to encrypt and add to shared preference
     * @param context app context
     */
    fun setPassword(password: String, context: Context) {
        val sharedPreferences = EncryptedSharedPreferences
                .create(MY_PREFS_ENCRYPTED_NAME,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val sharedPrefsEditor = sharedPreferences.edit()
        sharedPrefsEditor.putString(PASSWORD_KEY, password)
        sharedPrefsEditor.apply()
    }

    /**
     * get decrypted password from shared preference
     * @param context app context
     */
    fun getPassword(context: Context): String? {
        val sharedPreferences = EncryptedSharedPreferences
                .create(MY_PREFS_ENCRYPTED_NAME,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        return sharedPreferences.getString(PASSWORD_KEY, "Not Available")
    }


    fun setValueToSharedPreference(key: String, value: String){
        val sharedPreferences = App.appContext.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getSharedPreference(key: String): String{
        val sharedPreferences = App.appContext.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(key, ALL_GENRE) ?: ALL_GENRE
    }

}