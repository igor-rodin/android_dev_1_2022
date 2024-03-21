package ru.igor.rodin.retrofit.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.igor.rodin.retrofit.data.ProgressState
import ru.igor.rodin.retrofit.databinding.FragmentRandomUserBinding


class RandomUserFragment : Fragment() {

    private var _binding: FragmentRandomUserBinding? = null
    private val binding get() = _binding!!
    private val randomUserViewModel: RandomUserViewModel by viewModels { RandomUserViewModel.Factory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomUserBinding.inflate(inflater, container, false)
        binding.userViewModel = randomUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnUpdate.setOnClickListener {
                randomUserViewModel.getRandomUser()
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userViewModel?.viewUser?.collect {
                        Glide.with(this@RandomUserFragment).load(it?.avatarUrl)
                            .circleCrop()
                            .into(userCard.userAvatar)
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userViewModel?.state?.collect {
                        if (it is ProgressState.Error) {
                            showErrorMessage(it.message)
                        }
                    }
                }
            }
        }

    }

    private fun showErrorMessage(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}