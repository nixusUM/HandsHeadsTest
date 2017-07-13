package com.friends.handsheadstest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.friends.handsheadstest.mvp.SignInPresenter;
import com.friends.handsheadstest.mvp.SignInView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingInFragment extends Fragment implements SignInView, Validator.ValidationListener {

    @Order(1)
    @NotEmpty(sequence = 1, messageResId = R.string.field_is_required)
    @Email(sequence = 2, messageResId = R.string.invalid_email)
    @BindView(R.id.input_email)
    EditText email;

    @BindView(R.id.email_field)
    TextInputLayout emailField;

    @Order(2)
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    @NotEmpty(messageResId = R.string.field_is_required)
    @BindView(R.id.txt_password)
    EditText password;

    @BindView(R.id.password_field)
    TextInputLayout passField;

    private SignInPresenter presenter;
    private Validator validator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this);
        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initHintButton();
    }

    private void initHintButton() {
        password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    ((MainActivity) getActivity()).showErrorToast(R.string.password_hint);
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        activity.setUpBackNavigation();
    }

    @OnClick(R.id.btn_authorize)
    public void getWheather() {
        validator.validate();
    }

    @Override
    public void showSnackBar(String temperature) {
        ((MainActivity) getActivity()).showSnackBar(temperature);
    }

    @Override
    public void showLoading() {((MainActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((MainActivity) getActivity()).hideLoading();
    }

    @Override
    public void showErrorToast(int message) {
        ((MainActivity) getActivity()).showErrorToast(message);
    }

    @Override
    public void onValidationSucceeded() {
        clearErrors();
        presenter.getWheather();
        ((MainActivity) getActivity()).hideKeyboard();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(App.getInstance());

            if (view.getId() == email.getId()) {
                passField.setError(null);
                emailField.setError(message);
            } else if (view.getId() == password.getId()){
                emailField.setError(null);
                passField.setError(message);
            }
        }
    }

    private void clearErrors() {
        emailField.setError(null);
        passField.setError(null);
    }
}
