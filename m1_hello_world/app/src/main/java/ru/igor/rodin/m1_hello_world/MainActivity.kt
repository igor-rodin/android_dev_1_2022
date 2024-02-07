package ru.igor.rodin.m1_hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.igor.rodin.m1_hello_world.databinding.ActivityMainBinding
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("")

    private var occupiedPlaces = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            seatsCount.text = occupiedPlaces.toString()
            minusBtn.isEnabled = occupiedPlaces > 0

            addBtn.setOnClickListener {
                addPassenger()
            }

            minusBtn.setOnClickListener {
                removePassenger()
            }

            resetBtn.setOnClickListener {
                reset()
            }
        }
    }

    private fun reset() {
        occupiedPlaces = 0
        binding.resetBtn.visibility = View.GONE
        updateInfo(occupiedPlaces)
    }

    /**
     * Remove passenger from the bus
     */
    private fun removePassenger() {
        occupiedPlaces--
        updateInfo(occupiedPlaces)
    }

    /**
     * Add passenger to the bus
     */
    private fun addPassenger() {
        occupiedPlaces++
        updateInfo(occupiedPlaces)
    }


    /**
     * Updates availability information
     */
    private fun updateInfo(freeSeats: Int) {
        with(binding) {
            resetBtn.visibility = if(freeSeats > maxSeats) View.VISIBLE else View.GONE
            minusBtn.isEnabled = freeSeats > 0
            addBtn.isEnabled = freeSeats <= maxSeats
            seatsCount.text = freeSeats.toString()
            when {
                freeSeats == 0 -> {
                    infoMessage.text = getString(R.string.seats_free_message)
                    infoMessage.setTextColor(getColor(R.color.free_seats))
                }

                freeSeats <= maxSeats -> {
                    infoMessage.text = getString(R.string.seats_count_message, maxSeats - freeSeats)
                    infoMessage.setTextColor(getColor(R.color.regular_seats))
                }

                else -> {
                    infoMessage.text = getString(R.string.no_seats_message)
                    infoMessage.setTextColor(getColor(R.color.no_seats))
                }
            }


        }

    }

    companion object {
        private const val maxSeats = 49
    }
}