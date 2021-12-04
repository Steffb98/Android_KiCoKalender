package nl.inholland.android_kicokalender.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.databinding.ActivityRegister2Binding
import nl.inholland.android_kicokalender.databinding.ActivityRegisterBinding
import nl.inholland.android_kicokalender.model.Role
import nl.inholland.android_kicokalender.model.User
import nl.inholland.android_kicokalender.model.UserResponse
import nl.inholland.android_kicokalender.utils.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register2Activity : AppCompatActivity() {

    private var spinnerRoles: AppCompatSpinner? = null
    private lateinit var binding: ActivityRegister2Binding
    private lateinit var user: User
    private var role: String = "Parent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val roles: MutableList<String> = resources.getStringArray(R.array.roles).toMutableList()
        user = intent.getSerializableExtra("user") as User
        initWidgets(roles)
    }

    private fun initWidgets(roles: MutableList<String>) {
        spinnerRoles = findViewById(R.id.spinner_roles)
        val adapter = ArrayAdapter(this, R.layout.role_item, roles)
        spinnerRoles!!.adapter = adapter
        spinnerRoles!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                role = roles[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                role = "Parent"
            }
        }

        binding.buttonRegisterNextStep2.setOnClickListener {
            submitForm()
        }

        houseNumberFocusListener()
        streetFocusListener()
        cityFocusListener()
        postalCodeFocusListener()
    }

    private fun postalCodeFocusListener() {
        binding.editZip.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutZip.helperText = validZip()
            }
        }
    }

    private fun validZip(): CharSequence? {
        val streetText = binding.editZip.text.toString()
        if (streetText == ""){
            return "Required"
        }
        return null
    }

    private fun cityFocusListener() {
        binding.editCity.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutCity.helperText = validCity()
            }
        }
    }

    private fun validCity(): String? {
        val streetText = binding.editCity.text.toString()
        if (streetText == ""){
            return "Required"
        }
        return null
    }

    private fun streetFocusListener() {
        binding.editStreet.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutStreet.helperText = validStreet()
            }
        }
    }

    private fun validStreet(): String? {
        val streetText = binding.editStreet.text.toString()
        if (streetText == ""){
            return "Required"
        }
        return null
    }

    private fun houseNumberFocusListener() {
        binding.editHouseNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutHouseNumber.helperText = validHouseNumber()
            }
        }
    }

    private fun validHouseNumber(): String? {
        val houseNumberText = binding.editHouseNumber.text.toString()
        if(!houseNumberText.matches(".*[0-9].*".toRegex())){
            return "Must be all digits"
        }
        if(houseNumberText.length >= 5){
            return "Max 5 digits"
        }
        return null
    }

    private fun submitForm() {
        binding.layoutCity.helperText = validCity()
        binding.layoutZip.helperText = validZip()
        binding.layoutStreet.helperText = validStreet()
        binding.layoutHouseNumber.helperText = validHouseNumber()

        val validCity = binding.layoutCity.helperText == null
        val validZip = binding.layoutZip.helperText == null
        val validStreet = binding.layoutStreet.helperText == null
        val validHouserNumber = binding.layoutHouseNumber.helperText == null

        if (validCity && validHouserNumber && validStreet && validZip)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm() {
        var message = ""
        if (binding.layoutStreet.helperText != null)
            message += "\n\nStreet: " + binding.layoutStreet.helperText
        if (binding.layoutHouseNumber.helperText != null)
            message += "\n\nHouse number: " + binding.layoutHouseNumber.helperText
        if (binding.layoutZip.helperText != null)
            message += "\n\nPostal code: " + binding.layoutZip.helperText
        if (binding.layoutCity.helperText != null)
            message += "\n\nCity: " + binding.layoutCity.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                //do nothing
            }.show()
    }

    private fun resetForm() {
        user.postcode = binding.editZip.text.toString()
        val address = "${binding.editStreet.text.toString()} ${binding.editHouseNumber.text.toString()}, ${binding.editCity.text.toString()}"
        user.address = address
        user.role = role
        binding.layoutStreet.helperText = "Required"
        binding.layoutZip.helperText = "Required"
        binding.layoutHouseNumber.helperText = "Required"
        binding.layoutCity.helperText = "Required"

        addUser()
    }

    private fun addUser() {
        RetrofitBuilder.buildService().registerUser(user).enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if(response.code() == 409){
                        Toast.makeText(this@Register2Activity, "email already exists", Toast.LENGTH_SHORT).show()
                    }
                    if (response.code() == 200){
                        AlertDialog.Builder(this@Register2Activity)
                            .setTitle("User registered")
                            .setMessage("Click next step to invite others to your group")
                            .setPositiveButton("Next step") { _, _ ->
                                startActivity(Intent(this@Register2Activity, InviteOthersActivity::class.java))
                            }.show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Register2Activity", "onFailure: "+t.message)
                }
            }
        )
    }
}