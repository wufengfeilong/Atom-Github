package lohas.crypto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lohas.crypto.Bean.Result;
import lohas.crypto.Bean.User;
import lohas.crypto.activity.ChangePWdActivity;
import lohas.crypto.iview.MainInterface;
import lohas.crypto.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainInterface{

    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_change_password)
    TextView loginChangePassword;
    @BindView(R.id.login_register)
    TextView loginRegister;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this);
    }

    @OnClick({R.id.login_btn, R.id.login_change_password, R.id.login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                mainPresenter.login(new User(loginAccount.getText().toString(),loginPassword.getText().toString()));
                break;
            case R.id.login_change_password:
                startActivity(new Intent(this, ChangePWdActivity.class));
                break;
            case R.id.login_register:
                mainPresenter.test1();
                break;
        }
    }

    @Override
    public void crypyoTest(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        Toast.makeText(this,"show dialog",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDialog() {
        Toast.makeText(this,"close dialog",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(String result) {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }
}
