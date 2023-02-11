package team.boa.paradox

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class WelcomeActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var submitButton: Button
    private lateinit var puzzleIdInput: EditText
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // hide title and title bar
         supportActionBar?.hide()

        // get submit components from view
        submitButton  = findViewById(R.id.button_puzzle_id)
        puzzleIdInput = findViewById(R.id.edit_text_puzzle_id)
        loader = findViewById(R.id.loading_submit)

        submitButton.setOnClickListener {
            loader.isVisible = true // enable loading animation
            submitButton.isClickable = false // disable button from being clicked again

            // clean input
            val cleanedInputString = cleanPuzzleId(puzzleIdInput.text.trim().toString())

            if (cleanedInputString.length < 3) {
                loader.isVisible = false // disable loading animation
                Toast.makeText(this,"Error Invalid Puzzle Id", Toast.LENGTH_SHORT).show()
                submitButton.isClickable = true // enable button to allow for retry
            }

            if (cleanedInputString.length >= 3) {
                // validate puzzle_id
                validatePuzzleId(cleanedInputString)
            }
        }
    }

    private fun validatePuzzleId(puzzleId:String) {
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
                loader.isVisible = false // disable loading animation
                Toast.makeText(this,"Puzzle does not exist", Toast.LENGTH_SHORT).show()
                submitButton.isClickable = true // enable button to allow for retry
            }
        }.addOnFailureListener() {
            loader.isVisible = false // disable loading animation
            Toast.makeText(this,"Error could not validate Puzzle Id", Toast.LENGTH_SHORT).show()
            submitButton.isClickable = true // enable button to allow for retry
        }
    }

    private fun cleanPuzzleId(inputString: String): String {

        // codes only contain uppercase characters
        // only allow numbers and capital letters
        return inputString.uppercase().replace("[^0-9A-Z]".toRegex(), "")
    }
}