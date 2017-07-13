package com.friends.handsheadstest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_create)
    TextView createButton;

    @BindView(R.id.rotate_loading)
    RotateLoading progress;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        showMainFragment();
    }

    public void showMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new MainFragment(), MainFragment.class.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    public void setUpBackNavigation() {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            createButton.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.sign_in);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        createButton.setOnClickListener(v -> createEmailBox());
    }

    private void setDefaultToolbar() {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.app_name);
            createButton.setVisibility(View.GONE);
        }
    }

    private void createEmailBox() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com")));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setDefaultToolbar();
    }

    public void showSignInFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new SingInFragment(), SingInFragment.class.getSimpleName())
                .addToBackStack(SingInFragment.class.getSimpleName())
                .commitAllowingStateLoss();
    }

    public void hideLoading() {
        runOnUiThread(() -> {
            if (progress != null) {
                progress.setVisibility(View.INVISIBLE);
                progress.stop();
            }
        });
    }

    public void showLoading() {
        runOnUiThread(() -> {
            if (progress != null) {
                progress.setVisibility(View.VISIBLE);
                progress.bringToFront();
                progress.start();
            }
        });
    }

    public void showErrorToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(String temperature) {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.text_temp, temperature), Snackbar.LENGTH_SHORT).show();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View currentFocus = getCurrentFocus();
            if (currentFocus == null) {
                return;
            }
            if (currentFocus.getWindowToken() == null) {
                return;
            }

            input.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("Exception on keyboard:", e.getMessage());
        }
    }
}
