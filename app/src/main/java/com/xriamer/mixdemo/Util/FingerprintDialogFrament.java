package com.xriamer.mixdemo.Util;

import android.app.DialogFragment;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xriamer.mixdemo.Activitys.LoginActivity;
import com.xriamer.mixdemo.R;

import javax.crypto.Cipher;

import static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import static android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import static android.hardware.fingerprint.FingerprintManager.CryptoObject;

/**
 * FingerprintTest;
 * com.xriamer.fingerprinttest;
 * Created by Xriam on 12/26/2018;
 */
public class FingerprintDialogFrament extends DialogFragment {
    private FingerprintManager fingerprintManager;
    private CancellationSignal mCancellationSignal;
    private Cipher mCipher;
    private LoginActivity mActivity;
    private TextView errorText;

    private boolean isSelfCancelled;

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LoginActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        errorText = v.findViewById(R.id.err_text);
        TextView cancel = v.findViewById(R.id.cancel_text);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopListening();
            }

        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        startListener(mCipher);
    }

    private void startListener(Cipher cipher) {
        isSelfCancelled = false;
        mCancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(new CryptoObject(cipher), mCancellationSignal, 0, new AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                if (!isSelfCancelled) {
                    errorText.setText(errString);
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }

            @Override
            public void onAuthenticationFailed() {
                errorText.setText("指纹认证失败，请再试一遍");
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                errorText.setText(helpString);
            }

            @Override
            public void onAuthenticationSucceeded(AuthenticationResult result) {
                mActivity.onAuthenticated();
            }
        }, null);
    }

    private void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }
}




