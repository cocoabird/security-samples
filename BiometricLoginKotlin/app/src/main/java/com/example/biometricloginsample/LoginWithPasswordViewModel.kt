
/*
 * Copyright (C) 2020 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.biometricloginsample

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginWithPasswordViewModel(application: Application) : AndroidViewModel(application){

    private val _loginForm = MutableLiveData<LoginWithPasswordFormState>()
    val loginWithPasswordFormState: LiveData<LoginWithPasswordFormState> = _loginForm

    fun onLoginDataChanged(username:String, password:String){
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginWithPasswordFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginWithPasswordFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginWithPasswordFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun login(username: String, password: String):Boolean {
        if(isUserNameValid(username) && isPasswordValid(password)){
            // Normally this method would asynchronously send this to your server and your sever
            // would return a token. For high sensitivity apps such as banking, you would keep that
            // token in transient memory similar to my SampleAppUser object. This way the user
            // must login each time they start the app.
            // In this sample, we don't call a server. Instead we use a fake token that we set
            // right here:

            SampleAppUser.username=username
            SampleAppUser.fakeToken = java.util.UUID.randomUUID().toString()
            return true
        }
        return false
    }
}