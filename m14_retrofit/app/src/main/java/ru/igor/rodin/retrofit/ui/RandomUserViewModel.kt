package ru.igor.rodin.retrofit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.igor.rodin.retrofit.RandomUserApp
import ru.igor.rodin.retrofit.data.ProgressState
import ru.igor.rodin.retrofit.data.ViewUserData
import ru.igor.rodin.retrofit.data.api.RandomUserApi
import ru.igor.rodin.retrofit.data.api.UserData

class RandomUserViewModel(private val randomUserApi: RandomUserApi) : ViewModel() {

    private val _state: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState.Success())
    val state get() = _state.asStateFlow()

    private val _randomUsers: MutableStateFlow<List<UserData>> = MutableStateFlow(mutableListOf())
    val viewUser: MutableStateFlow<ViewUserData?> = MutableStateFlow(null)

    init {
        getRandomUser()
    }

    fun getRandomUser() {
        viewModelScope.launch {
            try {
                _state.value = ProgressState.Loading()
                val randomUsers = randomUserApi.getRandomUser().results
                _state.value = ProgressState.Success()
                _randomUsers.value = randomUsers
                randomUsers.first().let {
                    viewUser.value = ViewUserData(
                        "${it.userName.lastName} ${it.userName.firstName}",
                        it.avatar.picture,
                        it.email,
                        it.phone,
                        it.location.country
                    )
                }
            } catch (e: Exception) {
                _state.value = ProgressState.Error(e.message.toString())
            }

        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(RandomUserViewModel::class.java)) {
                    val app = requireNotNull(extras[APPLICATION_KEY]) as RandomUserApp
                    return RandomUserViewModel(app.randomUserApi) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
