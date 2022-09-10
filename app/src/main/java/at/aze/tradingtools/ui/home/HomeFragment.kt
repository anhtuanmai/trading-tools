package at.aze.tradingtools.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import at.aze.tradingtools.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set click event on the button
        _binding?.btnIncrease?.setOnClickListener {
            onButtonIncrease()
        }

        // set init value for the text
        _binding?.txtCurrentValue?.text = "0"
    }

    private fun onButtonIncrease() {
        var level = 1 // default value
        try {
            // try to read user input value and secure this procedure in a Try-Catch block
            level = _binding?.edtLevel?.text.toString().toInt()
        } catch (e: Exception) {
            Timber.e(e) // print error log

            // notify error
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                .show()
        }

        var currentCount = 0
        try {
            currentCount = _binding?.txtCurrentValue?.text.toString().toInt()
        } catch (e: Exception) {
            Timber.e(e)
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                .show()
        }

        // display new value
        val newValue = currentCount + level
        _binding?.txtCurrentValue?.text = newValue.toString()
    }
}