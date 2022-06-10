package com.compose.baseapp.tool


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

private const val FILE_NAME = "com_data"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FILE_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, FILE_NAME))
    }
)

object SPUtils {
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    private suspend fun setSuspendParam(
        context: Context,
        key: String?,
        `object`: Any
    ) {
        when (`object`.javaClass.simpleName) {
            "String" -> {
                val pKey = stringPreferencesKey(key ?: "")
                context.dataStore.edit { preferences ->
                    preferences[pKey] = `object` as String
                }
            }
            "Integer" -> {
                val pKey = intPreferencesKey(key ?: "")
                context.dataStore.edit { preferences ->
                    preferences[pKey] = `object` as Int
                }
            }
            "Boolean" -> {
                val pKey = booleanPreferencesKey(key ?: "")
                context.dataStore.edit { preferences ->
                    preferences[pKey] = `object` as Boolean
                }
            }
            "Float" -> {
                val pKey = floatPreferencesKey(key ?: "")
                context.dataStore.edit { preferences ->
                    preferences[pKey] = `object` as Float
                }
            }
            "Long" -> {
                val pKey = longPreferencesKey(key ?: "")
                context.dataStore.edit { preferences ->
                    preferences[pKey] = `object` as Long
                }
            }
        }
    }

    fun setParam(
        context: Context,
        key: String?,
        `object`: Any
    ) {
        when (`object`.javaClass.simpleName) {
            "String" -> {
                val pKey = stringPreferencesKey(key ?: "")
                runBlocking {
                    context.dataStore.edit { preferences ->
                        preferences[pKey] = `object` as String
                    }
                }
            }
            "Integer" -> {
                val pKey = intPreferencesKey(key ?: "")
                runBlocking {
                    context.dataStore.edit { preferences ->
                        preferences[pKey] = `object` as Int
                    }
                }
            }
            "Boolean" -> {
                val pKey = booleanPreferencesKey(key ?: "")
                runBlocking {
                    context.dataStore.edit { preferences ->
                        preferences[pKey] = `object` as Boolean
                    }
                }
            }
            "Float" -> {
                val pKey = floatPreferencesKey(key ?: "")
                runBlocking {
                    context.dataStore.edit { preferences ->
                        preferences[pKey] = `object` as Float
                    }
                }
            }
            "Long" -> {
                val pKey = longPreferencesKey(key ?: "")
                runBlocking {
                    context.dataStore.edit { preferences ->
                        preferences[pKey] = `object` as Long
                    }
                }
            }
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    suspend fun getSuspendParam(
        context: Context,
        key: String?,
        defaultObject: Any
    ): Any? {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[getPkey(defaultObject.javaClass.simpleName, key)] ?: defaultObject
            }.first()
    }

    fun getParam(
        context: Context,
        key: String?,
        defaultObject: Any
    ): Any? {
        var returnValue: Any
        runBlocking {
            returnValue = context.dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[getPkey(defaultObject.javaClass.simpleName, key)] ?: defaultObject
                }.first()
        }
        return returnValue
    }

    private fun getPkey(type: String, key: String?): Preferences.Key<*> {
        return when (type) {
            "String" -> stringPreferencesKey(key ?: "")
            "Integer" -> intPreferencesKey(key ?: "")
            "Boolean" -> booleanPreferencesKey(key ?: "")
            "Float" -> floatPreferencesKey(key ?: "")
            "Long" -> longPreferencesKey(key ?: "")
            else -> stringPreferencesKey(key ?: "")
        }
    }


    /**
     * 清除所有数据
     * @param context
     */
    fun clear(context: Context) {
        runBlocking {
            context.dataStore.edit {
                it.clear()
            }
        }
    }

}