package team.boa.paradox.ui.tools

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import team.boa.paradox.R
import team.boa.paradox.util.LogTags

private const val CAMERA_REQUEST_CODE = 101

class CameraActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        setupPermission()
        scanner()
    }


    private fun scanner(){
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val c : TextView = findViewById(R.id.qr_Text)
                c.text = it.text
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Log.d(LogTags.CAMERA_ACTIVITY.toString(), "Camera initialization error11: ${it.message}")
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }


    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    private fun setupPermission(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE-> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Error Camera permission is required for this tool", Toast.LENGTH_SHORT).show()
                    setupPermission()
                }
            }
        }
    }
}