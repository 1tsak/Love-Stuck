package com.oa.thedatingapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker
import com.oa.thedatingapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.transform.ErrorListener
import kotlin.concurrent.schedule

class AuthActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth;
    private lateinit var otpEt: EditText
    private lateinit var resend: TextView
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var resendTime = 60
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = Firebase.auth
        otpEt = findViewById(R.id.otpEt)
        resend = findViewById(R.id.resend)
        phoneNumber = intent.getStringExtra("phone").toString()
        val submit = findViewById<CardView>(R.id.submit);
        submit.setOnClickListener {
            val otp = otpEt.text.toString()
            if (otp.isEmpty()) {
                otpEt.error = "Enter OTP"
                otpEt.requestFocus()
                return@setOnClickListener
            }
            verifyPhoneNumberWithCode(storedVerificationId, otp)
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    e.message?.let { Log.d(TAG, it) }
                } else if (e is FirebaseTooManyRequestsException) {
                    e.message?.let { Log.d(TAG, it) }

                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                resend.setOnClickListener(View.OnClickListener {
                    //
                })
                resend.setTextColor(resources.getColor(R.color.gray))
                updateResendTime()
            }
        }
        startPhoneNumberVerification(phoneNumber);
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
        // [END verify_with_code]
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    updateUI()

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    // [END sign_in_with_phone]

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }

    fun submitLogin(view: View) {
        var otp = otpEt.text.toString().trim()
        verifyPhoneNumberWithCode(storedVerificationId, otp)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
    }

    // android kotlin coroutines
    private fun updateResendTime() {
        CoroutineScope(Dispatchers.Main).launch {
            while (resendTime > 0) {
                resend.text = "Resend OTP in ${resendTime.toString()} sec"
                delay(1000)
                resendTime--
            }
            resend.text = "Resend"
            resend.setTextColor(resources.getColor(R.color.black))
            resend.setOnClickListener(View.OnClickListener {

                resendVerificationCode(phoneNumber, resendToken)

            })
        }
    }

    fun back(view: View) {
        // prompt user to confirm
        AlertDialog .Builder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to go back?")
            .setPositiveButton("Yes") { dialog, which ->
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }


}