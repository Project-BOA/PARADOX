package team.boa.paradox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import team.boa.paradox.databinding.ActivityMainBinding
import team.boa.paradox.ui.welcome.WelcomeActivity
import team.boa.paradox.util.LogTags


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

    private lateinit var puzzleId: String
    private lateinit var puzzleAnswer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // hide title and title bar
         supportActionBar?.hide()

        // Launch welcome activity
        val intent = Intent(this, WelcomeActivity::class.java)

        startActivityWithResult(intent) { result ->
            puzzleId = result.data?.getStringExtra("puzzle_id").toString()
            puzzleAnswer = result.data?.getStringExtra("puzzle_answer").toString()
            Log.d(LogTags.MAIN_ACTIVITY.toString(), "Puzzle Id is: $puzzleId and Puzzle Answer is: $puzzleAnswer")
        }

        // add navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}
