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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // hide title and title bar
         supportActionBar?.hide()

        // get submit components from view
        val submitButton : Button = findViewById(R.id.button_puzzle_id)
        val puzzleIdInput: EditText = findViewById(R.id.edit_text_puzzle_id)
        val loader = findViewById<ProgressBar>(R.id.loading_submit)

        submitButton.setOnClickListener {
            loader.isVisible = true; // enable loading animation

            // clean input
            val cleanedInputString = cleanPuzzleId(puzzleIdInput.text.trim().toString())

            // validate puzzle_id
            validatePuzzleId(cleanedInputString, loader)
        }
    }

    private fun validatePuzzleId(puzzleId:String, loader: ProgressBar) {
        database = FirebaseDatabase.getInstance().getReference("answers")
        database.child(puzzleId).get().addOnSuccessListener {
            if(it.exists()){
                // intent to pass data back to main activity
                val data = Intent()
                data.putExtra("puzzle_id", puzzleId)
                setResult(RESULT_OK, data)
                finish()
            }
            else{
                loader.isVisible = false; // enable loading animation
                Toast.makeText(this,"Puzzle does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            loader.isVisible = false; // enable loading animation
            Toast.makeText(this,"Error Checking Puzzle Id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cleanPuzzleId(inputString: String): String {

        // codes only contain uppercase characters
        var cleaned = inputString.uppercase()
        // only allow numbers and capital letters
        cleaned = inputString.replace("[^0-9A-Z]".toRegex(), "")

        return cleaned
    }

}