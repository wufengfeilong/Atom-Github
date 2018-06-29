package com.fujisoft.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fujisoft.test.R;
import com.fujisoft.test.bean.Name;
import com.fujisoft.test.bean.User;
import com.fujisoft.test.iview.IMVPView;
import com.fujisoft.test.presenter.MVPPresenter;

public class MVPModelActivity extends AppCompatActivity implements IMVPView {

    @BindView(R.id.person_name)
    EditText personName;

    MVPPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpmodel);
        ButterKnife.bind(this);
        mvpPresenter = new MVPPresenter(this);
    }

    public void toSend(View view) {
        mvpPresenter.sendNameDate(Integer.parseInt(personName.getText().toString()));
    }

    @Override
    public void showProgressbar() {
        Toast.makeText(this, "showProgressbar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressbar() {
        Toast.makeText(MVPModelActivity.this, "hideProgressbar", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSendSuccess(User user) {
        Toast.makeText(this, user.getUserName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendFail(int errCode, String errMsg) {
        Toast.makeText(this, errCode+":"+errMsg, Toast.LENGTH_SHORT).show();
    }
}
