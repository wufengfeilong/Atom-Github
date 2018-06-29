package com.fujisoft.test.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.fujisoft.test.R;
import com.fujisoft.test.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class TabbedActivity extends AppCompatActivity {
    static List<Question> list;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        list = new ArrayList<>();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setData();
    }

    private void setData() {
        Question question = new Question();
        question.setTitle("1.当前市场手机系统占比最多的是？");
        question.setOptionA("IOS系统");
        question.setOptionB("Android系统");
        question.setOptionC("WP系统");
        question.setOptionD("塞班系统");
        question.setAnswer("Android系统");
        list.add(question);

        Question question1 = new Question();
        question1.setTitle("2.五百个人围成一圈，分别编号1到500，由第一个开始数数，1，2，3，1，2，3，1，2，3地数下去，当数到三的人退出圆圈，不断循环，直到圈子里剩下一个人，求最后一个人的编号是多少？");
        question1.setOptionA("321");
        question1.setOptionB("436");
        question1.setOptionC("275");
        question1.setOptionD("153");
        question1.setAnswer("436");
        list.add(question1);

        Question question2 = new Question();
        question2.setTitle("3.假设有一个池塘，里面有无穷多的水。现有2个空水壶，容积分别为5升和6升。问题是如何只用这2个水壶从池塘里取得3升的水？");
        question2.setOptionA("不可能");
        question2.setOptionB("有可能");
        question2.setOptionC("不知道");
        question2.setOptionD("干嘛非得取3升水");
        question2.setAnswer("有可能");
        list.add(question2);

        Question question3 = new Question();
        question3.setTitle("4.一条船上有75头牛，34头羊，问船长几岁？");
        question3.setOptionA("75");
        question3.setOptionB("34");
        question3.setOptionC("55");
        question3.setOptionD("不知道");
        question3.setAnswer("不知道");
        list.add(question3);

        Question question4 = new Question();
        question4.setTitle("5.小兔子问：我在排队，排在我前面有5个人，我后面的人比前面少2个人，队里总共有几人？");
        question4.setOptionA("6");
        question4.setOptionB("7");
        question4.setOptionC("8");
        question4.setOptionD("9");
        question4.setAnswer("8");
        list.add(question4);

        Question question5 = new Question();
        question5.setTitle("6.下列哪一项不是Android四大基本组件？");
        question5.setOptionA("Provider");
        question5.setOptionB("Service");
        question5.setOptionC("Intent");
        question5.setOptionD("Receiver");
        question5.setAnswer("Intent");
        list.add(question5);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String QUESTION_NUMBER = "question_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putInt(QUESTION_NUMBER, sectionNumber-1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

            final int which = getArguments().getInt(QUESTION_NUMBER);

            TextView num = rootView.findViewById(R.id.question_num);
            num.setText("第"+(which+1)+"/"+list.size()+"题");

            TextView title = rootView.findViewById(R.id.question_title);
            title.setText(list.get(which).getTitle());

            RadioButton qa = rootView.findViewById(R.id.qa);
            qa.setText(list.get(which).getOptionA());
            RadioButton qb = rootView.findViewById(R.id.qb);
            qb.setText(list.get(which).getOptionB());
            RadioButton qc = rootView.findViewById(R.id.qc);
            qc.setText(list.get(which).getOptionC());
            RadioButton qd = rootView.findViewById(R.id.qd);
            qd.setText(list.get(which).getOptionD());

            final RadioGroup options = rootView.findViewById(R.id.options);
            options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                    Toast.makeText(getContext(), "自己去找答案吧！", Toast.LENGTH_SHORT).show();
                    RadioButton checkBtn = options.findViewById(i);
                    if(checkBtn.getText().equals(list.get(which).getAnswer())){
                        Toast.makeText(getContext(), "选择正确！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "选择错误！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return list.size();
        }


    }
}
