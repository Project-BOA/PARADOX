package team.boa.paradox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // hide title and title bar
         supportActionBar?.hide()

        // get submit components from view
        val submitButton : Button = findViewById(R.id.button_puzzle_id)
        val puzzle_id_input: EditText = findViewById(R.id.edit_text_puzzle_id)

        // intent to pass data back to main activity
        val data = Intent()

        submitButton.setOnClickListener{

            var cleaned_input_string = puzzle_id_input.text.trim().toString();

            data.putExtra("puzzle_id", cleaned_input_string)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}