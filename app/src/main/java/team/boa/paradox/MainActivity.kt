package team.boa.paradox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide title and title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();



        setContentView(R.layout.activity_main)
    }
}