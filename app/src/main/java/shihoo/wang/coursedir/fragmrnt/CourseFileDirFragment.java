package shihoo.wang.coursedir.fragmrnt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import shihoo.wang.coursedir.utils.DateUtil;
import shihoo.wang.coursedir.widget.EditTextBottomDialog;
import shihoo.wang.coursedir.utils.FastClickUtil;
import shihoo.wang.coursedir.widget.MoreFileDialog;
import shihoo.wang.coursedir.widget.OrderDialog;
import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.bean.FileDirBean;
import shihoo.wang.coursedir.bean.netbean.CourseFileNetData;
import shihoo.wang.coursedir.coursefile.CourseFileDirEnum;
import shihoo.wang.coursedir.coursefile.CourseFileDirFragmentControl;
import shihoo.wang.coursedir.coursefile.CourseFileDirViewInterface;
import shihoo.wang.coursedir.coursefile.CourseTagBean;
import shihoo.wang.coursedir.coursefile.FolderCycleAdapter;


/**
 * Created by shihoo.wang on 2018/11/17.
 * Email shihu.wang@bodyplus.cc
 */

public class CourseFileDirFragment extends Fragment implements CourseFileDirViewInterface, View.OnClickListener {

    private LinearLayout ll_title;
    private TextView base_title;
    private ImageButton igBack;
    private RecyclerView recyclerView;
    private TextView tv_move;


    private ArrayList<FileDirBean> mList_template = new ArrayList<>();
    private FolderCycleAdapter myAdapter;

    private CourseFileDirEnum mType = CourseFileDirEnum.BASE;
    private String parentFolder = "0";
    private String titleName = "";
    private String depth = "0"; // 限定文件夹层级


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_personal_course_file_dir, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_title = view.findViewById(R.id.ll_title);
        base_title = view.findViewById(R.id.base_title_center_textView);
        igBack = view.findViewById(R.id.base_title_left_imageButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        tv_move = view.findViewById(R.id.tv_move);


        initView();
    }

    private void initView() {
        initType();
        myAdapter = new FolderCycleAdapter(getActivity());
        myAdapter.setData(mList_template);
        myAdapter.setType(mType);
        myAdapter.setCourseFileDirViewInterface(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        igBack.setOnClickListener(this);
        tv_move.setOnClickListener(this);
    }



    private void initType(){
        if (ll_title == null){
            return;
        }
        if (mType == CourseFileDirEnum.BASE){
            ll_title.setVisibility(View.GONE);
            tv_move.setVisibility(View.GONE);
        }else if (mType == CourseFileDirEnum.SHOW){
            ll_title.setVisibility(View.VISIBLE);
            igBack.setVisibility(View.VISIBLE);
            base_title.setText(titleName);
            tv_move.setVisibility(View.GONE);
        }else if (mType == CourseFileDirEnum.MOVE){
            ll_title.setVisibility(View.VISIBLE);
            igBack.setVisibility(View.VISIBLE);
            base_title.setText(titleName);
            tv_move.setVisibility(View.VISIBLE);
        }else if (mType == CourseFileDirEnum.SELECT){
            ll_title.setVisibility(View.VISIBLE);
            igBack.setVisibility(View.VISIBLE);
            base_title.setText(titleName);
            tv_move.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateNewFolder() {
        if (depth!=null && depth.length()>0){
            try {
                int deep = Integer.parseInt(depth);
                if (deep > 1){
                    Toast.makeText(getActivity(),"此层级不可再创建专题",Toast.LENGTH_LONG).show();
                }else {
                    showCreateFolder(parentFolder);
                }
            }catch (Exception e){

            }

        }
    }

    @Override
    public void onCreateNewFile() {
        showCreateFile(parentFolder);
//        Intent intent = new Intent();
//        intent.putExtra("parentId",parentFolder);
//        intent.setClass(getActivity(), PersonalCourseCreateActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        CourseFileDirFragmentControl.getInstance().removeCourseFileDirView(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == igBack){
            closedSelf();
        }if (v == tv_move){
            CourseFileDirFragmentControl.getInstance().moveCourseFileToHere(parentFolder);
        }
    }

    @Override
    public void setFolderAndType(CourseTagBean bean) {
        this.mType = bean.type;
        this.titleName = bean.folderName;
        this.parentFolder = bean.parentFolder;
        this.depth = bean.depth;
        initType();
        if (myAdapter != null) {
            myAdapter.setType(bean.type);
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setFolderDirData(CourseFileNetData data) {
        mList_template.clear();
        if (data.folderList != null) {
            mList_template.addAll(data.folderList);
        }
        if (mType != CourseFileDirEnum.MOVE){
            if (data.tplData != null) {
                mList_template.addAll(data.tplData);
            }
        }
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public String getParentFolder() {
        return parentFolder;
    }

    @Override
    public void closedSelf() {
        // 关闭自己
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.remove(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            transaction.commitNow();
//        } else {
//            transaction.commit();
//        }
        // 这里要使用fragment栈管理 使用出栈的方式
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager!=null && fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onMoreClick(int position) {
        if (FastClickUtil.isFastClick()) {
            return;
        }
        FileDirBean bean = mList_template.get(position);
        showMoreDialog(bean);
    }

    @Override
    public void onItemLongClick(int position) {
        FileDirBean bean = mList_template.get(position);
//        showLongClickDialog(bean);
    }

    @Override
    public void onItemClick(int position) {
        if (FastClickUtil.isFastClick()) {
            return;
        }
        FileDirBean bean = mList_template.get(position);
        if (bean.getFolderType().equals("4")) {
            CourseTagBean tagBean = new CourseTagBean();

            // 此处 手动匹配布局id，略有不适
            if (mType == CourseFileDirEnum.MOVE) {
                tagBean.type = CourseFileDirEnum.MOVE;
                tagBean.viewId = R.id.full_screen;
            } else if (mType==CourseFileDirEnum.SHOW || mType==CourseFileDirEnum.BASE){
                tagBean.type = CourseFileDirEnum.SHOW;
                tagBean.viewId = R.id.fg_content;
            }else if (mType == CourseFileDirEnum.SELECT){
                tagBean.type = CourseFileDirEnum.SELECT;
//                tagBean.viewId = R.id.fg_select_course;
            }
            tagBean.parentFolder = bean.getFolderId();
            tagBean.addToBackStack = true;
            tagBean.folderName = bean.getFolderName();
            tagBean.depth = bean.getDepth();
            CourseFileDirFragmentControl.getInstance().addNewFragment(getActivity().getSupportFragmentManager(),null, tagBean);
        }else if (bean.getTemplateId() != null){
            if (mType == CourseFileDirEnum.SELECT){
                // 选中课程  返回
                CourseFileDirFragmentControl.getInstance().setSelect(getActivity(),bean);
            }else {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), PersonalCourseDetailsActivity.class);
//                intent.putExtra("templateId", bean.getTemplateId());
//                startActivity(intent);
            }
        }
    }


    private void showMoreDialog(final FileDirBean bean){
        String[] arrs = new String[]{"重命名","删除"};
        if (!bean.getFolderType().equals("4")){
            arrs = new String[]{"重命名","移动","创建教案副本","删除"};
        }
        final String[] arr = arrs;
        MoreFileDialog moveFileDialog = new MoreFileDialog(getActivity(), arr,new MoreFileDialog.MoveFileDialogListener() {
            @Override
            public void onItemClick(int position) {
                onMoreDetails(bean,arr[position]);
            }
        });
        moveFileDialog.show();
    }

    private void onMoreDetails(FileDirBean bean, String s){
        if (s.equals("重命名")){
            showRenameDialog(bean);
        }else if (s.equals("删除")){
            showDeleteDialog(bean);
        }else if (s.equals("移动")){
            CourseFileDirFragmentControl.getInstance().selectMoveFile(getActivity().getSupportFragmentManager(),bean);
        }else if (s.equals("创建教案副本")){
            showCopyDialog(bean);
        }
    }




    private void showCopyDialog(final FileDirBean bean){
        EditTextBottomDialog dialog = new EditTextBottomDialog(getActivity(),3);
        dialog.setContent(bean.getTemplateName());
        dialog.setTitleTxt("创建教案副本");

        dialog.setDialogClickListener(new EditTextBottomDialog.OnInputDialogClickListener() {
            @Override
            public void onCancelBtnClick() {
            }

            @Override
            public void onConfirmBtnClick(String content) {
                if (content!=null && content.trim().length()>0) {
                    CourseFileDirFragmentControl.getInstance().copyFileToNet(content,bean);
                }else {
                    Toast.makeText(getActivity(),"请输入副本名称",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void showRenameDialog(final FileDirBean bean){
        EditTextBottomDialog dialog = new EditTextBottomDialog(getActivity(),3);
        if (bean.getFolderType().equals("4")) {
            dialog.setContent(bean.getFolderName());
            dialog.setTitleTxt("重命名专题");
        }else {
            dialog.setContent(bean.getTemplateName());
            dialog.setTitleTxt("重命名训练教案");
        }
        dialog.setDialogClickListener(new EditTextBottomDialog.OnInputDialogClickListener() {
            @Override
            public void onCancelBtnClick() {
            }

            @Override
            public void onConfirmBtnClick(String content) {
                if (content!=null && content.trim().length()>0) {
                    CourseFileDirFragmentControl.getInstance().reNameFolderNet(content,bean);
                }else {
                    Toast.makeText(getActivity(),"请输入正确名称",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void showDeleteDialog(final FileDirBean bean){
        OrderDialog dialog = new OrderDialog(getActivity());
        if (bean.getFolderType().equals("4")) {
            dialog.setTitleTxt("删除 "+bean.getFolderName() +" 专题下所有内容？");
        }else {
            dialog.setTitleTxt("删除 "+ bean.getTemplateName() +" 教案？");
        }
        dialog.setDialogClickListener(new OrderDialog.OnInputDialogClickListener() {
            @Override
            public void onCancelBtnClick() {
            }

            @Override
            public void onConfirmBtnClick() {
                CourseFileDirFragmentControl.getInstance().deleteFolderToNet(bean);
            }
        });
        dialog.show();
    }


    private void showCreateFolder(final String parentFolder){

        EditTextBottomDialog dialog = new EditTextBottomDialog(getActivity(),3);
        dialog.setEditHintText("专题名称");
        dialog.setTitleTxt("创建专题");

        dialog.setDialogClickListener(new EditTextBottomDialog.OnInputDialogClickListener() {
            @Override
            public void onCancelBtnClick() {
            }

            @Override
            public void onConfirmBtnClick(String content) {
                if (content!=null && content.trim().length()>0) {
                    FileDirBean bean = new FileDirBean();
                    bean.setParentFolder(parentFolder);
                    bean.setFolderName(content);
                    bean.setFolderType("4");
                    bean.setFolderId(""+System.currentTimeMillis()/1000);
                    bean.setLastDatetime(DateUtil.getStringDate());
                    CourseFileDirFragmentControl.getInstance().createFolderToNet(bean);
                }else {
                    Toast.makeText(getActivity(),"请输入专题名称",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void showCreateFile(final String parentFolder){

        EditTextBottomDialog dialog = new EditTextBottomDialog(getActivity(),3);
        dialog.setEditHintText("教案名称");
        dialog.setTitleTxt("创建教案");

        dialog.setDialogClickListener(new EditTextBottomDialog.OnInputDialogClickListener() {
            @Override
            public void onCancelBtnClick() {
            }

            @Override
            public void onConfirmBtnClick(String content) {
                if (content!=null && content.trim().length()>0) {
                    FileDirBean bean = new FileDirBean();
                    bean.setParentFolder(parentFolder);
                    bean.setTemplateName(content);
                    bean.setFolderType("0");
                    bean.setLastDatetime(DateUtil.getStringDate());
                    bean.setTotalStep(""+content.length());
                    CourseFileDirFragmentControl.getInstance().createFolderToNet(bean);
                }else {
                    Toast.makeText(getActivity(),"请输入教案名称",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

}