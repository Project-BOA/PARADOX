package team.boa.paradox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import team.boa.paradox.databinding.ActivityMainBinding


// https://kaustubhpatange.github.io/blog/post/kotlin-activity-result/
fun <I, O> ComponentActivity.launchWithResult(
    input: I,
    contract: ActivityResultContract<I, O>,
    onResult: (O) -> Unit
) {
    val contractKey = "contract_${UUID.randomUUID()}"
    var launcher: ActivityResultLauncher<I?>? = null
    launcher = activityResultRegistry.register(contractKey, contract) { result ->
        launcher?.unregister()
        onResult(result)
    }
    launcher.launch(input)
}

fun ComponentActivity.startActivityWithResult(
    input: Intent,
    onActivityResult: (ActivityResult) -> Unit
) : Unit = launchWithResult(input, ActivityResultContracts.StartActivityForResult(), onActivityResult)


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var puzzleId: String? = ""
    private var puzzleAnswer: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // hide title and title bar
        // supportActionBar?.hide()

        // Launch welcome activity
        val intent = Intent(this, WelcomeActivity::class.java)

        startActivityWithResult(intent) { result ->
            puzzleId = result.data?.getStringExtra("puzzle_id").toString()
            puzzleAnswer = result.data?.getStringExtra("puzzle_answer").toString()
            Log.d(LogTags.MAIN_ACTIVITY.toString(), "Puzzle Id is: $puzzleId and Puzzle Answer is: $puzzleAnswer")
            binding.textDisplay.text = "Puzzle Id is: $puzzleId and Puzzle Answer is: $puzzleAnswer"
        }
    }
}