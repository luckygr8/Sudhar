package com.sih2020.project.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializers
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.home.HomeFragment
import com.sih2020.project.transferObjects.User
import com.sih2020.project.utility.Cypher
import com.sih2020.project.utility.Functions
import org.json.JSONArray
import org.json.JSONObject


class LoginFragment : Fragment(), Initializers,
    HttpRequests {

    private lateinit var root: View
    private lateinit var fragment: Fragment

    // login view
    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPassword: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var signupLink: TextView
    private lateinit var forgotPasswordLink: TextView
    //

    private lateinit var register_user_screen: LinearLayout
    private lateinit var user_login_screen: LinearLayout

    // tokens
    private val loginToken = 1
    private val signupToken = 2
    private val checkToken = 3
    //

    // signup view
    private lateinit var name: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var signupToggelable: LinearLayout
    private lateinit var checkEmail: MaterialButton
    private lateinit var verifyOTP: EditText

    private lateinit var verify: MaterialButton
    private lateinit var signupButton: MaterialButton

    override fun bindViews() {
        // for login view
        loginEmail = root.findViewById(R.id.loginEmail)
        loginPassword = root.findViewById(R.id.loginPassword)
        loginButton = root.findViewById(R.id.loginButton)
        signupLink = root.findViewById(R.id.signupLink)

        signupLink.setOnClickListener {

            user_login_screen.animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(200)
                .withEndAction {
                    user_login_screen.visibility = View.GONE
                    register_user_screen.visibility = View.VISIBLE
                }
        }
        forgotPasswordLink = root.findViewById(R.id.forgotPasswordLink)

        forgotPasswordLink.setOnClickListener {

        }
        loginButton.setOnClickListener { login() }

        //

        register_user_screen = root.findViewById(R.id.register_user_screen)
        user_login_screen = root.findViewById(R.id.user_login_screen)

        /**
         * @author Lakshay Dutta
         * @since 23-01-2020
         *
         * <b> Flow of register </b>
         *
         * 1. Check email
         * check the email if its empty or not according to format, then sends it to server
         * @see RestURLs.POST_CHECK_EMAIL
         * @see checkEmail
         *
         * 2. Verify OTP
         * otp field is returned in the first step. It is cached locally for <duration> and
         * it is deleted after <duration> ends.
         * @see Constants.DURATION_OTP
         * @see Functions.setOTP
         * @see verifyOTP
         *
         * 3. sign up using the details { email , username , password ( hashed )
         * @see signup
         */
        // register
        name = root.findViewById(R.id.name)
        email = root.findViewById(R.id.email)
        password = root.findViewById(R.id.password)
        confirmPassword = root.findViewById(R.id.confirmPassword)
        checkEmail = root.findViewById(R.id.checkEmail) // Button for check email
        verify = root.findViewById(R.id.verify) // Button for verify OTP
        verifyOTP = root.findViewById(R.id.verifyOTP)
        verify.setOnClickListener { verifyOTP() }
        checkEmail.setOnClickListener { checkEmail() }
        signupButton = root.findViewById(R.id.signupButton) // Button for sign up
        signupButton.setOnClickListener { signup() }
        signupToggelable = root.findViewById(R.id.signupToggelable)
        //

        toggleNonOtpWidgets(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_login, container, false)
        fragment = this
        bindViews()

        return root
    }

    private fun verifyOTP(){
        val otp = verifyOTP.text.toString()
        if (!otp.isBlank()) {
            if (verify(otp)) {
                Functions.showToast(R.string.OtpMatchSuccess,false)
                Functions.removeCallbacks()
                freeze(email,checkEmail,verifyOTP,verify) // Can not allow to modify the email at this stage
                toggleNonOtpWidgets(true)
            }
            else{
                Functions.showToast(R.string.OtpMatchError,true)
            }
        } else {
            Functions.showToast(R.string.OtpEmpty,true)
        }
    }

    private fun login() {

        val LOGINEMAIL = loginEmail.text.toString()
        val LOGINPASSWORD = loginPassword.text.toString()

        if (LOGINEMAIL.isEmpty()) {
            loginEmail.error = Constants.ERROR_REQUIRED
            return
        }
        if (!Functions.isEmailValid(LOGINEMAIL)) {
            loginEmail.error = Constants.ERROR_INVALID_EMAIL
            return
        }

        if (LOGINPASSWORD.isEmpty()) {
            loginPassword.error = Constants.ERROR_REQUIRED
            return
        }

        val user = User(email = LOGINEMAIL , password = Cypher.hashPassword(LOGINPASSWORD))
        Functions.postJsonObject(
            RestURLs.POST_LOGIN_T, this,
            Constants.OBJECT_TYPE_USER, user, loginToken
        )

    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(volleyError: VolleyError) {
        Log.d(Constants.LOG_TAG, volleyError.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Functions.setOTP("")
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        when (token) {
            loginToken -> {
                user_login_screen.visibility = View.GONE
                //report_problem_1.visibility = View.VISIBLE
            }
            signupToken -> {
                Log.d(Constants.LOG_TAG, jsonObject.toString())
                // TODO employ success code
                Functions.showToast(R.string.signUpSuccess,true)
                Functions.setCurrentUser(
                    User(
                        email = email.text.toString(),
                        username = name.text.toString()
                    )
                )

                /**
                 * Replaces current fragment with HOME fragment
                 * @see HomeFragment
                 */
                switchToHome()

            }
            checkToken -> {
                Log.d(Constants.LOG_TAG, jsonObject.toString())

                Functions.setOTP(jsonObject.getString("otp"))
                Functions.showToast(R.string.checkYourOtp,false)
                freeze(checkEmail,email)
            }
        }
    }

    private fun switchToHome(){
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.nav_host_fragment, HomeFragment())
        fragmentTransaction?.commit()
    }

    private fun checkEmail() {

        val EMAIL = email.text.toString()

        // see if email is empty
        if (EMAIL.isEmpty()) {
            email.error = Constants.ERROR_REQUIRED
            return
        }
        // see if the email is according to the format
        if (!Functions.isEmailValid(EMAIL)) {
            email.error = Constants.ERROR_INVALID_EMAIL
            return
        }

        Functions.postJsonObject(
            RestURLs.POST_CHECK_T, this, Constants.OBJECT_TYPE_USER,
            User(email = EMAIL
            ), checkToken
        )


    }

    private fun verify(otp: String): Boolean = Functions.getOTP().equals(otp)

    private fun toggleNonOtpWidgets(toggle: Boolean) =
        /**
         * We need to disable these fields before the email verification is complete via OTP
         * Users can not enter any data here unless the OTP is verified
         *
         * @author Lakshay Dutta
         * @since 22-01-20
         */
        when (toggle) {
            false -> signupToggelable.visibility = View.INVISIBLE
            true -> signupToggelable.visibility = View.VISIBLE
        }

    private fun signup() {
        val NAME = name.text.toString()
        val PASSWORD = password.text.toString()
        val CONFIRMPASSWORD = confirmPassword.text.toString()
        val EMAIL = email.text.toString()
        if (NAME.isEmpty()) {
            name.error = Constants.ERROR_REQUIRED
            return
        }

        if (PASSWORD.isEmpty()) {
            password.error = Constants.ERROR_REQUIRED
            return
        }
        if (CONFIRMPASSWORD.isEmpty()) {
            confirmPassword.error = Constants.ERROR_REQUIRED
            return
        }

        if (CONFIRMPASSWORD != PASSWORD) {
            confirmPassword.error = Constants.ERROR_PASSWORD_DONT_MATCH
            return
        }

        val user = User(email = EMAIL, password = Cypher.hashPassword(PASSWORD), username = NAME)
        Functions.postJsonObject(
            RestURLs.POST_REGISTER_T,
            this,
            Constants.OBJECT_TYPE_USER,
            user,
            signupToken
        )
        freeze(email,name,password,confirmPassword,verify,checkEmail)
        // TODO progress bar of signup
    }

    /**
     * @author Lakshay Dutta
     * @since 23-01-2020
     *
     * @see freeze is used for disabling views to prevent changing of already set values
     */
    private fun freeze(vararg views:View) = views.forEach { it.isEnabled =false }
}