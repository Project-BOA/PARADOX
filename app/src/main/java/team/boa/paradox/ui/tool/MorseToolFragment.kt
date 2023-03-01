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
import team.boa.paradox.databinding.FragmentMorseToolBinding
import team.boa.paradox.viewmodel.ToolViewModel

class MorseToolFragment : Fragment() {

    private val toolViewModel: ToolViewModel by activityViewModels()
    private lateinit var binding: FragmentMorseToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    //private var alphabet = arrayOf('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

    // array of morsecode characters in alphabetical order
    private var morsecode = arrayOf(".-", "-...", "-.-.", "-..", '.', "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
        "--.-", ".-.", "...", '-', "..-", "...-", ".--", "-..-",
        "-.--", "--..")
    private var morseMap: HashMap<String, Char> = HashMap<String, Char>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMorseToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Morse Code"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        // create hashmap of morse code
        // the keys are using the morse code array which is in alphabetical order
        // the valuation are the alphabet translation from ascii
        for (i in morsecode.indices) {
            morseMap[morsecode[i].toString()] = (i+65).toChar()
        }

        // attach listeners
        binding.spaceButton.setOnClickListener { addText(" ") }
        binding.dotButton.setOnClickListener { addText(".") }
        binding.dashButton.setOnClickListener { addText("-") }

        binding.backspaceButton.setOnClickListener {
            val str = binding.inText.text.toString()
            if (str.isNotEmpty()) {
                binding.inText.text = str.substring(0, str.length - 1)
            }
        }
        binding.conButton.setOnClickListener {
            for(morseChar in binding.inText.toString().split(" ")) {
                binding.outText.text = binding.outText.text.toString() + morseMap.get(morseChar)
            }
        }
    }


    private fun addText(textToAdd: String) {
        if (!binding.inText.text.isEmpty()) {
            binding.inText.text = binding.inText.text.toString() + textToAdd
        }
    }
}