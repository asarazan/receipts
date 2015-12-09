package net.sarazan.receipts.util.google

import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by Aaron Sarazan on 12/8/15
 * Copyright(c) 2015 Level, Inc.
 */

fun defaultSignIn() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("918453345648-vjmla6lq4iecass47if2inlhggqnbo4p.apps.googleusercontent.com")
        .build()

fun FragmentActivity.googleSignInClient(onFail: (ConnectionResult) -> Unit): GoogleApiClient {
    return GoogleApiClient.Builder(this)
            .enableAutoManage(this, onFail)
            .addApi(Auth.GOOGLE_SIGN_IN_API, defaultSignIn())
            .build()
}