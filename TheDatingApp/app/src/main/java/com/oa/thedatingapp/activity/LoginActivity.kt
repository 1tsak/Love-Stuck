package com.oa.thedatingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.hbb20.CountryCodePicker
import com.oa.thedatingapp.R

class LoginActivity : AppCompatActivity() {
    lateinit var phoneEt: EditText
    lateinit var continueBtn: CardView
    lateinit var continueTxt: TextView
    lateinit var ccp: CountryCodePicker
    var continueEnabled = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phoneEt = findViewById(R.id.phoneEt)
        continueBtn = findViewById(R.id.continueBtn)
        continueTxt = findViewById(R.id.continueTxt)

        ccp= findViewById(R.id.countryCode_picker)

//        ccp.registerCarrierNumberEditText(phoneEt)


        phoneEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (phoneEt.text.length == 10) {
                    continueBtn.setCardBackgroundColor(resources.getColor(R.color.main_color_dark))
                    continueTxt.setTextColor(resources.getColor(R.color.white))
                    continueEnabled = true
                } else {
                    continueBtn.setCardBackgroundColor(resources.getColor(R.color.gray))
                    continueTxt.setTextColor(resources.getColor(R.color.dark_gray))
                    continueEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    fun continueLogin(view: View) {
        if (continueEnabled) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("phone",ccp.selectedCountryCodeWithPlus.toString()+phoneEt.text.toString())
            Toast.makeText(this,ccp.selectedCountryCodeWithPlus,Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
    }
}