package team.boa.paradox

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import team.boa.paradox.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // hide title and title bar
        supportActionBar?.hide()

        binding.buttonPuzzleId.setOnClickListener {
            binding.loadingSubmit.isVisible = true // enable loading animation
            binding.buttonPuzzleId.isClickable = false // disable button from being clicked again

            // clean input
            val cleanedPuzzleId = cleanPuzzleId(binding.editTextPuzzleId.text.toString())

            if (cleanedPuzzleId.length < 3) {
                binding.loadingSubmit.isVisible = false // disable loading animation
                Toast.makeText(this,"Error Invalid Puzzle Id", Toast.LENGTH_SHORT).show()
                binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
            }

            if (cleanedPuzzleId.length >= 3) {
                // lookup puzzle_id
                getPuzzleFromId(cleanedPuzzleId)
            }
        }
    }

    // get the puzzle id in the database
    private fun getPuzzleFromId(puzzleId:String) {
        database = FirebaseDatabase.getInstance().getReference("answers")
        database.child(puzzleId).get().addOnSuccessListener {
            if (it.exists()) {
                // intent to pass data back to main activity
                val data = Intent()
                data.putExtra("puzzle_id", it.key.toString())
                data.putExtra("puzzle_answer", it.value.toString())
                setResult(RESULT_OK, data)
                finish()
            }
            else {
                binding.loadingSubmit.isVisible = false // disable loading animation
                binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
                Toast.makeText(this,"Puzzle does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener() {
            binding.loadingSubmit.isVisible = false // disable loading animation
            binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
            Toast.makeText(this,"Error could not locate Puzzle Id", Toast.LENGTH_SHORT).show()
        }
    }

    // clean a string to match puzzle id format
    private fun cleanPuzzleId(inputString: String): String {
        return inputString
            .uppercase() // convert input to uppercase if not already
            .replace("[^0-9A-Z ]".toRegex(), "") // only allow numbers, capital letters and spaces
            .trim() // trim any excess spaces
    }
}