package sdwxwx.com.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.adapter.CityAdapter;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.fragment.SearchFragment;
import sdwxwx.com.home.model.PickCityModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class PickCityActivity extends AppCompatActivity {
    private List<CityEntity> mDatas;
    private SearchFragment mSearchFragment;
    private SearchView mSearchView;
    private FrameLayout mProgressBar;
    private PickCityModel mModel;
    private CityAdapter mAdapter;
    List<CityEntity> mGpsCity = new ArrayList<>();
    SimpleHeaderAdapter mGpsHeaderAdapter;

    public LocationClient mLocationClient = null;
    private CityPickLocationListener myListener = new CityPickLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_city);
        ViewUtils.setImgTransparent(this);
        // setAdapter
        mAdapter = new CityAdapter(this);
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        mSearchView = findViewById(R.id.searchview);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        //设置字体大小为14sp
        textView.setTextSize(14);//14sp
        mProgressBar = (FrameLayout) findViewById(R.id.progress);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));

        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);

        indexableLayout.setAdapter(mAdapter);

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();

        // 定位
        mGpsCity.add(new CityEntity("定位中..."));
        mGpsHeaderAdapter = new SimpleHeaderAdapter<>(mAdapter, "定", "定位到的位置", mGpsCity);
        indexableLayout.addHeaderAdapter(mGpsHeaderAdapter);

        // 搜索Demo
        initSearch();

        mDatas = LoginHelper.getInstance().getCityList();
        if (mDatas == null || mDatas.size() <= 0) {
            mModel = new PickCityModel();
            mModel.getCities(new BaseCallback<List<CityEntity>>() {
                @Override
                public void onSuccess(List<CityEntity> data) {
                    mDatas = data;
                    mAdapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CityEntity>() {
                        @Override
                        public void onFinished(List<EntityWrapper<CityEntity>> datas) {
                            // 数据处理完成后回调
                            mSearchFragment.bindDatas(mDatas);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onFail(String msg) {
                    Toast.makeText(PickCityActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mAdapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CityEntity>() {
                @Override
                public void onFinished(List<EntityWrapper<CityEntity>> datas) {
                    // 数据处理完成后回调
                    mSearchFragment.bindDatas(mDatas);
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }

        // 开始定位
        startLocation();

        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityEntity entity) {
                Intent intent = new Intent();
                intent.setAction(Constant.INTENT_FILTER_SELECT_CITY);
                intent.putExtra("name", entity.getName());
                intent.putExtra("code", entity.getId());
                sendBroadcast(intent);
                finish();

            }
        });

    }

    private void startLocation() {
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initSearch() {
        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
                    }
                } else {
                    if (!mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
                    }
                }

                mSearchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.isHidden()) {
            // 隐藏 搜索
            mSearchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
            return;
        }
        super.onBackPressed();
    }

    public void onBackClick(View v) {
        onBackPressed();
    }

    public class CityPickLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            final String city = location.getCity() == null ? "北京市" : location.getCity();    //获取城市
            mGpsCity.get(0).setName(city);
            if (mDatas == null) {
                mGpsCity.get(0).setId("1");
            } else {
                for (CityEntity cityEntity : mDatas) {
                    if (cityEntity.getName().equals(city)) {
                        mGpsCity.get(0).setId(cityEntity.getId());
                    }
                }
            }
            mGpsHeaderAdapter.notifyDataSetChanged();
        }
    }
}
