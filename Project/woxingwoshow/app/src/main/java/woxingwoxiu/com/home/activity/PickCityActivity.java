package woxingwoxiu.com.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
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
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.home.adapter.CityAdapter;
import woxingwoxiu.com.home.bean.CityEntity;
import woxingwoxiu.com.home.fragment.SearchFragment;
import woxingwoxiu.com.home.model.PickCityModel;
import woxingwoxiu.com.release.utils.ViewUtils;

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
        mModel = new PickCityModel();
        mModel.getCitys(new BaseCallback<List<CityEntity>>() {
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
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mProgressBar = (FrameLayout) findViewById(R.id.progress);
        // 开始定位
        startLocation();

        indexableLayout.setLayoutManager(new LinearLayoutManager(this));

        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        // setAdapter
        mAdapter = new CityAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        // set Datas
//        mDatas = initDatas();

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();

        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityEntity entity) {
                Intent intent = new Intent();
                intent.setAction(Constant.INTENT_FILTER_SELECT_CITY);
                intent.putExtra("name",entity.getName());
                intent.putExtra("code",entity.getId());
                sendBroadcast(intent);
                finish();

            }
        });

        // 定位
        mGpsCity.add(new CityEntity("定位中..."));
        mGpsHeaderAdapter = new SimpleHeaderAdapter<>(mAdapter, "定", "定位到的城市", mGpsCity);
        indexableLayout.addHeaderAdapter(mGpsHeaderAdapter);

        // 搜索Demo
        initSearch();
    }

    private void startLocation(){
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    private List<CityEntity> initDatas() {
        List<CityEntity> list = new ArrayList<>();
//        List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
        List<String> cityStrings = new ArrayList<>();
        cityStrings.add("杭州市");
        cityStrings.add("北京市");
        cityStrings.add("上海市");
        cityStrings.add("山东省");
        cityStrings.add("广州市");
        for (String item : cityStrings) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(item);
            list.add(cityEntity);
        }
        return list;
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
        public void onReceiveLocation(BDLocation location){
            final String city = location.getCity();    //获取城市
            mGpsCity.get(0).setName(city);
            for (CityEntity cityEntity : mDatas) {
                if (cityEntity.getName().equals(city)) {
                    mGpsCity.get(0).setId(cityEntity.getId());
                }
            }
            mGpsHeaderAdapter.notifyDataSetChanged();
        }
    }
}
