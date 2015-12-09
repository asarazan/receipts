package net.sarazan.receipts

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.client.AuthData
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.plus.Plus
import com.levelmoney.velodrome.Velodrome
import com.levelmoney.velodrome.annotations.OnActivityResult
import net.sarazan.receipts.databinding.ActivityLoginBinding
import net.sarazan.receipts.util.google.googleSignInClient
import org.jetbrains.anko.async
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private val binding by bind<ActivityLoginBinding>(R.layout.activity_login)

    private val client by lazy {
        googleSignInClient {
            toast("Something went wrong!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.onCreate()
        binding.data.signInButton.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
            startActivityForResult(signInIntent, 0);
        }
    }

    @OnActivityResult(0)
    fun onSignIn(data: Intent) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            val email = acct.email
            val scopes = "oauth2:profile email"
            async {
                val token = GoogleAuthUtil.getToken(applicationContext, email, scopes)
                MyApplication.firebase.authWithOAuthToken("google", token, object : Firebase.AuthResultHandler {
                    override fun onAuthenticated(p0: AuthData) {
                        toast("Logged In: ${p0.providerData["email"]}")
                    }

                    override fun onAuthenticationError(p0: FirebaseError?) {
                        toast("Something went wrong.")
                    }
                })
            }
        } else {
            toast("Something went wrong.")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Velodrome.handleResult(this, requestCode, resultCode, data)
    }
}
