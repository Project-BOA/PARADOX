package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentConverterToolBinding
import team.boa.paradox.viewmodel.ToolViewModel

class ConverterToolFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val toolViewModel: ToolViewModel by activityViewModels()
    private lateinit var binding: FragmentConverterToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Converter"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        binding.option.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            activityContext,
            R.array.conversion_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.option.adapter = adapter
        }

        val inputText: TextView = binding.enterText
        val outputText: TextView = binding.outputText

        binding.decodeButton.setOnClickListener {
            when (binding.option.selectedItem.toString()) {
                "Binary" -> outputText.text = binToString(inputText.text.toString())
                "Octal" -> outputText.text = octToString(inputText.text.toString())
                "Hexadecimal" -> outputText.text = hexToString(inputText.text.toString())
                else -> {
                    Toast.makeText(activityContext, "Error decoding", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.aButton.setOnClickListener {inputText.text = inputText.text.toString() + "A" }
        binding.bButton.setOnClickListener {inputText.text = inputText.text.toString() + "B" }
        binding.cButton.setOnClickListener {inputText.text = inputText.text.toString() + "C" }
        binding.dButton.setOnClickListener {inputText.text = inputText.text.toString() + "D" }
        binding.eButton.setOnClickListener {inputText.text = inputText.text.toString() + "E" }
        binding.fButton.setOnClickListener {inputText.text = inputText.text.toString() + "F" }
    }

    private fun binToString(binary:String): String {
        val sb = StringBuilder()

        for (bin in binary.trim().split(" ")) {
            try {
                sb.append(Integer.parseInt(bin, 2).toChar())
            } catch (e: NumberFormatException) {
                return "Not a Binary Number"
            }
        }

        return sb.toString()
    }

    private fun octToString(octal:String): String {
        val sb = StringBuilder()

        for (oct in octal.trim().split(" ")) {
            try {
                sb.append(Integer.parseInt(oct, 8).toChar())
            } catch (e: NumberFormatException) {
                return "Not an Octal Number"
            }
        }

        return sb.toString()
    }

    private fun hexToString(hexadecimal:String): String{
        val sb = StringBuilder()

        for (hex in hexadecimal.trim().split(" ")) {
            try {
                sb.append(Integer.parseInt(hex, 16).toChar())
            } catch (e: NumberFormatException) {
                return "Not a Hexadecimal Number"
            }
        }

        return sb.toString()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (binding.option.getItemAtPosition(p2).toString() == "Hexadecimal") {
            binding.converterButtons.visibility = View.VISIBLE
        }
        else {
            binding.converterButtons.visibility = View.GONE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}