package com.app.ardeco.network

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder

class SignUpViewModel {
    fun signUp(
        name: String,
        email: String,
        mobile: String,
        password: String,
        dateOfBirth: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val client = OkHttpClient()

        val encodedName = URLEncoder.encode(name, "UTF-8")
        val encodedEmail = URLEncoder.encode(email, "UTF-8")
        val encodedMobile = URLEncoder.encode(mobile, "UTF-8")
        val encodedPassword = URLEncoder.encode(password, "UTF-8")
        val encodedDateOfBirth = URLEncoder.encode(dateOfBirth, "UTF-8")

        val url = "https://tgali.com/ardeco/ardeco/api/v1/signup?name=$encodedName&email=$encodedEmail&mobile=$encodedMobile&password=$encodedPassword&dateOfBirth=$encodedDateOfBirth"


        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(false, "Network error: ${e.message}")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful) {
                        try {
                            // Parse JSON using Gson
                            val json = Gson().fromJson(responseBody, JsonObject::class.java)
                            val message = json.get("message")?.asString ?: "Sign-up successful"
                            CoroutineScope(Dispatchers.Main).launch {
                                onResult(true, message)
                            }
                        } catch (e: Exception) {
                            CoroutineScope(Dispatchers.Main).launch {
                                onResult(false, "Response parsing error: ${e.message}")
                            }
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            onResult(false, "Error: ${responseBody ?: "Unknown error"}")
                        }
                    }
                }
            })
        }
    }
}


class SignInViewModel {
    fun signIn(
        email: String,
        password: String,
        onResult: (Boolean, String, Int, String) -> Unit
    ) {
        val client = OkHttpClient()

        val encodedEmail = URLEncoder.encode(email, "UTF-8")
        val encodedPassword = URLEncoder.encode(password, "UTF-8")

        val url = "https://tgali.com/ardeco/ardeco/api/v1/login?email=$encodedEmail&password=$encodedPassword"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(false, "Network error: ${e.message}", 0, "")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()

                    if (response.isSuccessful) {
                        try {
                            val json = Gson().fromJson(responseBody, JsonObject::class.java)
                            val message = json.get("message")?.asString ?: "Sign-in successful"
                            val user = json.getAsJsonObject("user")
                            val userId = user?.get("id")?.asInt ?: -1
                            val name = user?.get("name")?.asString ?: ""

                            CoroutineScope(Dispatchers.Main).launch {
                                onResult(true, message, userId, name)
                            }
                        } catch (e: Exception) {
                            CoroutineScope(Dispatchers.Main).launch {
                                onResult(false, "Response parsing error: ${e.message}", 0, "")
                            }
                        }
                    } else {
                        val json = Gson().fromJson(responseBody, JsonObject::class.java)
                        val message = json.get("message")?.asString ?: "Email or password is incorrect"
                        CoroutineScope(Dispatchers.Main).launch {
                            onResult(false, message, 0, "")
                        }
                    }
                }
            })
        }
    }
}
