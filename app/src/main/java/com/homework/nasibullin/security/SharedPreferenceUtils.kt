package com.homework.nasibullin.security

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.homework.nasibullin.App
import com.homework.nasibullin.fragments.MainFragment.Companion.ALL_GENRE

object SharedPreferenceUtils {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private const val SHARED_PREF_NAME = "Genres"
    private const val MY_PREFS_ENCRYPTED_NAME = "MyPrefsFile"
    const val PASSWORD_KEY = "user_mame_password"
    const val SESSION_ID = "session_id"
    const val DEFAULT_VALUE = "Not Available"
    const val IS_AUTO_UPDATE = "auto_update"

    private val encryptSharedPref: SharedPreferences
    get(){
        return EncryptedSharedPreferences
            .create(
                MY_PREFS_ENCRYPTED_NAME,
                masterKeyAlias,
                App.appContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    /**
     * set encrypted password to shared preference
     * @param key is the key by which the value will be put
     * @param value is value which will be put
     */
    fun setEncryptedValue(key: String, value: String) {
        val sharedPrefsEditor = encryptSharedPref.edit()
        sharedPrefsEditor.putString(key, value)
        sharedPrefsEditor.apply()
    }

    /**
     * get decrypted password from shared preference
     * @param key is key in Shared Preference
     * @return decrypted value from Encrypted Shared Preference
     */
    fun getEncryptedValue(key: String): String? {
        val sharedPreferences = encryptSharedPref
        return sharedPreferences.getString(key, DEFAULT_VALUE)
    }

    /**
     * set value for shared preference
     * @param key is the key by which the value will be put
     * @param value is value which will be put
     */
    fun setValueToSharedPreference(key: String, value: String){
        val sharedPreferences = App.appContext.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * get value from shared pref by key
     * @param key is the key by which the value will be get
     */
    fun getSharedPreference(key: String): String{
        val sharedPreferences = App.appContext.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(key, ALL_GENRE) ?: ALL_GENRE
    }
}