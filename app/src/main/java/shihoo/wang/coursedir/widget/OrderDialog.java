package shihoo.wang.coursedir.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.widget.BaseDialog;

/**
 * Created by shihoo.wang on 2018/11/16.
 * Email shihu.wang@bodyplus.cc
 */

public class OrderDialog extends BaseDialog implements View.OnClickListener{
    private Context mContext;
    private View mainView;
    private TextView titleTv;
    private TextView cancelBtn;
    private TextView confirmBtn;
    private TextView content;
    private OnInputDialogClickListener mListener;

    public OrderDialog(Context context) {
        super(context);
        setContentView(R.layout.layout_dialog_order);
        mContext = context;
        initView();
    }

    public void setTitleTxt(String str){
        titleTv.setText(str);
    }
    public void setCancleTxt(String str){
        cancelBtn.setText(str);
    }
    public void setConfirmTxt(String str){
        confirmBtn.setText(str);
    }
    public void setContent(String str){
        content.setVisibility(View.VISIBLE);
        content.setText(str);
    }

    public void setDialogClickListener(OnInputDialogClickListener listener){
        mListener = listener;
    }

    private void initView(){
        mainView = findViewById(R.id.main);
        cancelBtn = (TextView) findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(this);
        confirmBtn = (TextView) findViewById(R.id.dialog_confirm_btn);
        confirmBtn.setOnClickListener(this);
        titleTv = (TextView) findViewById(R.id.input_title);
        content = (TextView) findViewById(R.id.input_content);
    }

    @Override
    public void onClick(View v) {
        if(v == cancelBtn){
            dismiss();
            mListener.onCancelBtnClick();
        }else if(v == confirmBtn){
            dismiss();
            mListener.onConfirmBtnClick();
        }
    }




    public interface OnInputDialogClickListener{
        void onCancelBtnClick();
        void onConfirmBtnClick();
    }
}