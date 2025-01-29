package com.app.ardeco.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object ModelRepository {
    private val cache = mutableMapOf<String, List<Model>>() // Cache map with key as page

    // Retrieve cached data by key
    fun getCachedData(key: String): List<Model>? = cache[key]

    // Store data in cache with key
    fun setCachedData(key: String, data: List<Model>) {
        cache[key] = data
    }
}
object SavedDesignRepository {
    private val cache = mutableMapOf<String, List<SavedDesign>>() // Cache map with key as page

    // Retrieve cached data by key
    fun getCachedData(key: String): List<SavedDesign>? = cache[key]

    // Store data in cache with key
    fun setCachedData(key: String, data: List<SavedDesign>) {
        cache[key] = data
    }
}


data class Model (
    val id: Int,
    val title: String,
    val thumb: String,
    val file: String,
    val created_at: String
)


data class SavedDesign(
    val id: Int,
    val model: String,
    val image: String,
    val created_at: String
)

class ModelViewModel : ViewModel() {

    private val client = OkHttpClient()
    private val gson = Gson()

    private val _modelData = MutableStateFlow<List<Model>>(emptyList())
    private val _savedDesignData = MutableStateFlow<List<SavedDesign>>(emptyList())
    private val _isLoading = MutableStateFlow<Boolean>(true)

    val savedDesignData: StateFlow<List<SavedDesign>> get() = _savedDesignData
    val modelData: StateFlow<List<Model>> get() = _modelData
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchModelData(modelUrl: String, pageKey: String) {
        // Check if data for the pageKey is already cached
        val cachedData = ModelRepository.getCachedData(pageKey)
        if (cachedData != null) {
            _isLoading.value = false
            // Use cached data
            _modelData.value = cachedData
            return
        }

        // Fetch data from the server if not in cache
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder().url(modelUrl).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val body = response.body?.string()
                    body?.let {
                        val type = object : TypeToken<Map<String, List<Model>>>() {}.type
                        val jsonResponse: Map<String, List<Model>> = gson.fromJson(it, type)
                        val models = jsonResponse["data"] ?: emptyList()
                        _isLoading.value = false

                        // Cache the data and emit it
                        ModelRepository.setCachedData(pageKey, models)
                        _modelData.value = models
                    }
                } else {
                    _isLoading.value = false

                    throw Exception("Failed to fetch model: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun fetchSavedDesigns(modelUrl: String, pageKey: String) {
        // Check if data for the pageKey is already cached
//        val cachedData = SavedDesignRepository.getCachedData(pageKey)
//        if (cachedData != null) {
//            _isLoading.value = false
//            // Use cached data
//            _savedDesignData.value = cachedData
//            return
//        }

        Log.d("ar",":::"+modelUrl)
        // Fetch data from the server if not in cache
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder().url(modelUrl).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val body = response.body?.string()
                    body?.let {
                        val type = object : TypeToken<Map<String, List<SavedDesign>>>() {}.type
                        val jsonResponse: Map<String, List<SavedDesign>> = gson.fromJson(it, type)
                        val models = jsonResponse["data"] ?: emptyList()
                        _isLoading.value = false

                        // Cache the data and emit it
//                        SavedDesignRepository.setCachedData(pageKey, models)
                        _savedDesignData.value = models
                    }
                } else {
                    _isLoading.value = false

                    throw Exception("Failed to fetch model: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


// User Repository with Cache
object UserRepository {
    private val cache = mutableMapOf<String, UserModel>()

    // Retrieve cached data by key
    fun getCachedData(key: String): UserModel? = cache[key]

    // Store data in cache with key
    fun setCachedData(key: String, data: UserModel) {
        cache[key] = data
    }
}

// UserModel
data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val mobile: String,
    val dob: String,
)


class UserViewModel : ViewModel() {

    private val client = OkHttpClient()
    private val gson = Gson()

    private val _modelData = MutableStateFlow<UserModel?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(false)

    val modelData: StateFlow<UserModel?> get() = _modelData

    fun getUserData(modelUrl: String) {
        _isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {

                // Fetch from API if not cached
                val request = Request.Builder().url(modelUrl).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val body = response.body?.string()
                    body?.let {
                        val type = object : TypeToken<Map<String, UserModel>>() {}.type
                        val jsonResponse: Map<String, UserModel> = gson.fromJson(it, type)

                        val user = jsonResponse["data"]
                        user?.let {
                            _modelData.value = it
                        }
                    }
                } else {
                    throw Exception("Failed to fetch user: ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateUser(
        modelUrl: String,
        updatedUser: UserModel,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        _isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userJson = Gson().toJson(updatedUser)
                val requestBody = userJson.toRequestBody("application/json".toMediaType())
                val request = Request.Builder()
                    .url(modelUrl)
                    .put(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val body = response.body?.string()

                    if (body == null) {
                        throw Exception("Response body is null. Response code: ${response.code}")
                    }

                    // Parse response and update UI state
                    val type = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(body, type)
                    val updatedUserJson = Gson().toJson(responseMap["user"])
                    val updatedResponse = Gson().fromJson<UserModel>(updatedUserJson, UserModel::class.java)

                    withContext(Dispatchers.Main) {
                        _modelData.value = updatedResponse
                        onSuccess()
                    }
                } else {
                    throw Exception("Failed to update user: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onError(e) }
            } finally {
                _isLoading.value = false
            }
        }
    }

}
