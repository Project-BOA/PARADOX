package team.boa.paradox.ui.tools

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import team.boa.paradox.databinding.FragmentCameraToolBinding

private const val CAMERA_REQUEST_CODE = 101

class CameraToolFragment : Fragment() {
    private lateinit var binding: FragmentCameraToolBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var activityContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPermission()
        Scanner()
    }


    private fun Scanner(){
        codeScanner = CodeScanner(activityContext, binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity?.mainExecutor?.execute {
                binding.qrText.text = it.text
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            activity?.mainExecutor?.execute {
                Toast.makeText(
                    activityContext, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            binding.scannerView.setOnClickListener {
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
        val permission = ContextCompat.checkSelfPermission(activityContext, android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED){
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE-> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activityContext, "You need this", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}