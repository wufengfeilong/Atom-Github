package lohas.crypto.presenter;

import android.util.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lohas.crypto.Bean.Result;
import lohas.crypto.Bean.User;
import lohas.crypto.iview.MainInterface;
import lohas.crypto.util.MD5Util;
import lohas.crypto.util.RetrofitManager;

public class MainPresenter {
    private static final String TAG = "MainPresenter";
    private MainInterface mainInterface;

    public MainPresenter(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    public void login(User user) {
        Log.d(TAG, "login: name="+user.getName());
        Log.d(TAG, "login: pwd="+user.getPwd());
        user.setPwd(MD5Util.encrypt(user.getPwd()));
        mainInterface.showDialog();
        RetrofitManager
                .getInstance()
                .getRetrofitAPIService()
                .login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {

                    @Override
                    public void accept(Result result) throws Exception {
                        mainInterface.login(result.getMsg());
                        mainInterface.closeDialog();
                    }
                });
    }

    public void test1() {
        RetrofitManager
                .getInstance()
                .getRetrofitAPIService()
                .ctyptoTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mainInterface.crypyoTest(s);
                    }
                });
//                .subscribe(new Consumer<Result>() {
//                    @Override
//                    public void accept(Result s) throws Exception {
//                        mainInterface.crypyoTest(s.getMsg());
//                    }
//                });
    }
}
