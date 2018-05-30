package woxingwoxiu.com.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.databinding.ActivityHomeBinding;
import woxingwoxiu.com.home.fragment.HomeFragment;
import woxingwoxiu.com.me.fragment.MeHomeFragment;
import woxingwoxiu.com.message.fragment.MessageListFragment;
import woxingwoxiu.com.near.fragment.NearFragment;
import woxingwoxiu.com.release.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomePresenter> implements HomeContract.View {

    FragmentManager fm;
    List<Fragment> list;
    List<Integer> selectedImg;
    List<Integer> unSelectImg;
    List<ImageView> navBtmIv;

    @Override
    protected void initViews() {
        ViewUtils.setImgTransparent(this);

        mDataBinding.setPresenter(mPresenter);
        initFragment();
        initSelectedImg();
        initUnSelectImg();
        initNavBtm();
        setDefaultFragment();

    }

    private void initNavBtm() {
        navBtmIv = new ArrayList<>();
        navBtmIv.add(mDataBinding.homeBtmMenuHome);
        navBtmIv.add(mDataBinding.homeBtmMenuNear);
        navBtmIv.add(mDataBinding.homeBtmMenuMsg);
        navBtmIv.add(mDataBinding.homeBtmMenuMe);
    }

    private void clearSelectStatus() {
        for (int i = 0; i < 4; i++) {
            Glide.with(this).load(unSelectImg.get(i)).into(navBtmIv.get(i));
        }
    }

    private void setSelectStatus(int pos) {

        Glide.with(this).load(selectedImg.get(pos)).into(navBtmIv.get(pos));

    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        list = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        list.add(homeFragment);
        NearFragment nearFragment = new NearFragment();
        list.add(nearFragment);
        MessageListFragment messageListFragment = new MessageListFragment();
        list.add(messageListFragment);
        MeHomeFragment meHomeFragment = new MeHomeFragment();
        list.add(meHomeFragment);
    }

    private void initUnSelectImg() {
        unSelectImg = new ArrayList<>();
        unSelectImg.add(R.drawable.home_no_select);
        unSelectImg.add(R.drawable.near_no_select);
        unSelectImg.add(R.drawable.info_no_select);
        unSelectImg.add(R.drawable.me_no_select);
    }

    private void initSelectedImg() {
        selectedImg = new ArrayList<>();
        selectedImg.add(R.drawable.home_selected);
        selectedImg.add(R.drawable.near_selected);
        selectedImg.add(R.drawable.info_selected);
        selectedImg.add(R.drawable.me_selected);
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void setDefaultFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        // update by lily
        // 获取要显示的FragmentIndex
        int intentIndex = getIntent().getIntExtra(Constant.BACK_HOME_KEY, 0);
        transaction.replace(R.id.home_content, list.get(intentIndex));
        transaction.commit();
        clearSelectStatus();
        setSelectStatus(intentIndex);
    }

    @Override
    public void setShowFragment(int position) {
//        if (!isLogin() && position == 3) {
//            actionStartActivity(LoginActivity.class);
//            return;
//        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.home_content, list.get(position));
        transaction.commit();
        clearSelectStatus();
        setSelectStatus(position);
    }

}
