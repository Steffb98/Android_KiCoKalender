package nl.inholland.android_kicokalender.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.*
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.databinding.ActivityRegisterBinding
import nl.inholland.android_kicokalender.model.User

class RegisterActivity : AppCompatActivity() {
    private var profilePicButton: Button? = null
    private var nextStepButton: Button? = null
    private var profileImage: ImageView? = null
    private lateinit var binding: ActivityRegisterBinding

    val getContent = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            profileImage!!.setImageResource(
                result.data!!.getIntExtra(
                    "image",
                    R.drawable.lady_red_blondhair
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWidgets()
    }

    private fun initWidgets() {
        profilePicButton = findViewById(R.id.add_profile_picture)
        profilePicButton!!.setOnClickListener {
            getContent.launch(Intent(this, ProfilePicActivity::class.java))
            //startActivity(Intent(this, ProfilePicActivity::class.java))
        }
        nextStepButton = findViewById(R.id.button_register_next_step)
        nextStepButton!!.setOnClickListener {
            submitForm()
        }
        profileImage = findViewById(R.id.chosen_pp_image_view)

        emailFocusListener()
        passwordRegexFocusListener()
        passwordCorrectFocusListener()
        firstNameFocusListener()
        lastNameFocusListener()
    }

    private fun firstNameFocusListener() {
        binding.editFirstName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutFirstName.helperText = validFirstName()
            }
        }
    }

    private fun validFirstName(): String? {
        return if (binding.editFirstName.text.toString() == "")
            "Required" else
            null
    }

    private fun lastNameFocusListener() {
        binding.editLastName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutLastName.helperText = validLastName()
            }
        }
    }

    private fun validLastName(): String? {
        return if (binding.editLastName.text.toString() == "")
            "Required" else
            null
    }

    private fun submitForm() {
        binding.layoutFirstName.helperText = validFirstName()
        binding.layoutLastName.helperText = validLastName()
        binding.layoutEmail.helperText = validEmail()
        binding.layoutPw1.helperText = validPassword()
        binding.layoutPw2.helperText = correspondingPassword()

        val validFirstName = binding.layoutFirstName.helperText == null
        val validLastName = binding.layoutLastName.helperText == null
        val validEmail = binding.layoutEmail.helperText == null
        val validPw1 = binding.layoutPw1.helperText == null
        val validPw2 = binding.layoutPw2.helperText == null

        if (validFirstName && validLastName && validEmail && validPw1 && validPw2)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm() {
        var message = ""
        if (binding.layoutFirstName.helperText != null)
            message += "\n\nFirst name: " + binding.layoutFirstName.helperText
        if (binding.layoutLastName.helperText != null)
            message += "\n\nLast name: " + binding.layoutLastName.helperText
        if (binding.layoutEmail.helperText != null)
            message += "\n\nEmail: " + binding.layoutEmail.helperText
        if (binding.layoutPw1.helperText != null)
            message += "\n\nPassword: " + binding.layoutPw1.helperText
        if (binding.layoutPw2.helperText != null)
            message += "\n\nPassword: " + binding.layoutPw2.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                //do nothing
            }.show()
    }

    private fun resetForm() {
        var user: User = User()
        user.firstName = binding.editFirstName.text.toString()
        user.lastName = binding.editLastName.text.toString()
        user.email = binding.editEmail.text.toString()
        user.password = binding.editPw1.text.toString()
        val intent = Intent(this, Register2Activity::class.java)
        intent.putExtra("user", user)

        binding.layoutFirstName.helperText = "Required"
        binding.layoutLastName.helperText = "Required"
        binding.layoutEmail.helperText = "Required"
        binding.layoutPw1.helperText = "Required"
        binding.layoutPw2.helperText = "Required"

        startActivity(intent)
    }

    private fun emailFocusListener() {
        binding.editEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutEmail.helperText = validEmail()
            }
        }
    }

    private fun passwordRegexFocusListener() {
        binding.editPw1.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutPw1.helperText = validPassword()
            }
        }
    }

    private fun passwordCorrectFocusListener() {
        binding.editPw2.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.layoutPw2.helperText = correspondingPassword()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.editEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid email address"
        }
        return null
    }

    private fun validPassword(): String? {
        val pwText = binding.editPw1.text.toString()
        if (pwText.length < 8) {
            return "Minimum 8 character password"
        }
        if (!pwText.matches(".*[A-Z].*".toRegex())) {
            return "Must contain 1 capital character"
        }
        if (!pwText.matches(".*[a-z].*".toRegex())) {
            return "Must contain 1 lower case character"
        }
        if (!pwText.matches(".*[@#$%^&+=].*".toRegex())) {
            return "Must contain 1 special character"
        }
        return null
    }

    private fun correspondingPassword(): String? {
        val pw1text = binding.editPw1.text.toString()
        val pw2text = binding.editPw2.text.toString()
        if (pw2text == "")
            return "Required"
        if (pw1text != pw2text) {
            return "passwords do not match"
        }
        return null
    }
}