package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.databinding.FragmentCipherToolBinding
import team.boa.paradox.viewmodel.ToolViewModel

class CipherToolFragment : Fragment() {

    private val toolViewModel: ToolViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.decode.setOnClickListener {
            binding.outputText.text = pushAlphabet(
                binding.inputText.text.toString(),
                binding.numberPush.text.toString().toInt()
            )
        }
    }

    val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    //out = (x-n)%26
    private fun pushAlphabet(text: String, s:Int): String {
        val sb = StringBuilder()

        for(ch in text.lowercase()) {
            val pos = ALPHABET.indexOf(ch)
            var newPos = (pos - s) % 26
            if (newPos < 0) {
                newPos += ALPHABET.length
            }
            sb.append(ALPHABET[newPos])
        }
        return sb.toString().uppercase()
    }
}