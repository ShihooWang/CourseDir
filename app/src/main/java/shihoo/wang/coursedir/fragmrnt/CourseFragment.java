package shihoo.wang.coursedir.fragmrnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.utils.ViewAnimUtils;
import shihoo.wang.coursedir.coursefile.CourseFileDirEnum;
import shihoo.wang.coursedir.coursefile.CourseFileDirFragmentControl;
import shihoo.wang.coursedir.coursefile.CourseTagBean;
import shihoo.wang.coursedir.coursefile.FloatViewHelper;
import shihoo.wang.coursedir.floatviewgroup.FloatViewGroup;

/**
 * Created by shihoo.wang on 2018/11/7.
 * Email shihu.wang@bodyplus.cc
 */

public class CourseFragment extends Fragment  /*extends BaseFragment*/{

    private TabLayout viewpagerTab;
    private ViewPager viewpager;
    private FloatViewGroup mFloatViewGroup;
    private TextView base_title;
    private List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "训练教案", "视频课程"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_course, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        base_title = view.findViewById(R.id.base_title_center_textView);
        mFloatViewGroup = view.findViewById(R.id.float_view_group);
        viewpager = view.findViewById(R.id.viewpager);
        viewpagerTab = view.findViewById(R.id.viewpager_tab);
        initView();
    }

    private void initView() {

        mFragments = new ArrayList<>();
        mFragments.add(new CourseFileDirFragment());
        mFragments.add(new VideoCourseFragment());
        base_title.setText("课程");

        MyFragmentPagerItemAdapter adapter =
                new MyFragmentPagerItemAdapter(getActivity().getSupportFragmentManager());

        FloatViewHelper.getInstance().setFloatViewGroup(mFloatViewGroup);
        FloatViewHelper.getInstance().setFloatViewAdapter(getActivity());
        viewpager.setAdapter(adapter);
        viewpagerTab.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    ViewAnimUtils.startVisiableAnim(true,FloatViewHelper.getInstance().getFloatViewGroup());
                }else {
                    ViewAnimUtils.startVisiableAnim(false,FloatViewHelper.getInstance().getFloatViewGroup());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden && FloatViewHelper.getInstance().getFloatViewGroup()!=null){
            FloatViewHelper.getInstance().getFloatViewGroup().checkShrinkViews();
        }
    }

    private String fragmentTag;
    private class MyFragmentPagerItemAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerItemAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null){
                return mFragments.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            if (mFragments != null){
                return mFragments.size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                CourseFileDirFragment fragment = (CourseFileDirFragment) super.instantiateItem(container, position);
                if (fragment != null) {
                    String tag = fragment.getTag();
                    // 表示第一个
                    if (fragmentTag == null && tag.endsWith(":0")) {
                        fragmentTag = tag;
//                    CourseFileDirFragmentControl.getInstance().addCourseFileDirView(fragment,0,false);
                        CourseTagBean bean = new CourseTagBean();
                        bean.parentFolder = "0";
                        bean.type = CourseFileDirEnum.BASE;
                        bean.addToBackStack = false;
                        bean.depth = "0";
                        CourseFileDirFragmentControl.getInstance().addNewFragment(getActivity().getSupportFragmentManager(),fragment,bean);

                    }
                }
                return fragment;
            }
            return super.instantiateItem(container,position);
        }
    }

}

