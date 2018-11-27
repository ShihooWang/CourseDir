package shihoo.wang.coursedir;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shihoo.wang.coursedir.fragmrnt.CourseFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout viewTab1, viewTab2, viewTab3, viewTab4;
    private TextView tabMenu1,tabMenu2,tabMenu3,tabMenu4;

    private CourseFragment mFragmentCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

       initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState){
        setSelectItem(2);

//        FragmentCourse
        if (savedInstanceState == null)
        {
            mFragmentCourse = new CourseFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.content, mFragmentCourse, "FRAGMENT_COURSE"); // 添加到课程fragment到对应的坑位
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tx.commitNow();
            } else {
                tx.commit();
            }
        }
    }

    private void initView(){
        viewTab1 = findViewById(R.id.view_tab1);
        viewTab2 = findViewById(R.id.view_tab2);
        viewTab3 = findViewById(R.id.view_tab3);
        viewTab4 = findViewById(R.id.view_tab4);
        tabMenu1 = findViewById(R.id.tab_menu1);
        tabMenu2 = findViewById(R.id.tab_menu2);
        tabMenu3 = findViewById(R.id.tab_menu3);
        tabMenu4 = findViewById(R.id.tab_menu4);

        viewTab1.setOnClickListener(this);
        viewTab2.setOnClickListener(this);
        viewTab3.setOnClickListener(this);
        viewTab4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_tab1:
                setSelectItem(0);
                break;
            case R.id.view_tab2:
                setSelectItem(1);
                break;
            case R.id.view_tab3:
                setSelectItem(2);
                break;
            case R.id.view_tab4:
                setSelectItem(3);
                break;
        }
    }

    private void setSelectItem(int index){
        switch (index){
            case 0:
                if (!viewTab1.isSelected()) {
                    setMenuNormal();
                    setMenuTextNormal();
                    tabMenu1.setSelected(true);
                    viewTab1.setSelected(true);
                }
                break;
            case 1:
                if (!viewTab2.isSelected()) {
                    setMenuNormal();
                    setMenuTextNormal();
                    tabMenu2.setSelected(true);
                    viewTab2.setSelected(true);
                }
                break;
            case 2:
                if (!viewTab3.isSelected()) {
                    setMenuNormal();
                    setMenuTextNormal();
                    tabMenu3.setSelected(true);
                    viewTab3.setSelected(true);
                }
                break;
            case 3:
                if (!viewTab4.isSelected()) {
                    setMenuNormal();
                    setMenuTextNormal();
                    tabMenu4.setSelected(true);
                    viewTab4.setSelected(true);
                }
                break;
        }
    }

    private void setMenuNormal() {
        viewTab1.setSelected(false);
        viewTab2.setSelected(false);
        viewTab3.setSelected(false);
        viewTab4.setSelected(false);
    }

    private void setMenuTextNormal() {
        tabMenu1.setSelected(false);
        tabMenu2.setSelected(false);
        tabMenu3.setSelected(false);
        tabMenu4.setSelected(false);
    }
}
