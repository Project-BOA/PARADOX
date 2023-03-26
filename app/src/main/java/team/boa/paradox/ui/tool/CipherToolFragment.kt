package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.databinding.FragmentCipherToolBinding

class CipherToolFragment : Fragment() {

    private lateinit var binding: FragmentCipherToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCipherToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Cipher"
        return binding.root
    }

    private val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    //out = (x-n)%26
    private fun shiftText(text: String, key:Int): String {
        val sb = StringBuilder()

        for(ch in text.lowercase()) {
            var newChar = ((ch.code - key) % 128).toChar()
            if (newChar.code < 0) {
                newChar = 128.toChar()
            }
            sb.append(newChar)
        }
        return sb.toString().uppercase()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        binding.decode.setOnClickListener {
            if (binding.inputText.text.isNotEmpty() && binding.numberPush.text.isNotEmpty())
                binding.outputText.text = shiftText(
                    binding.inputText.text.toString(),
                    binding.numberPush.text.toString().toInt()
                )
        }
    }
}