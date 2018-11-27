package shihoo.wang.coursedir.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shihoo.wang.coursedir.R;


/**
 * Created by shihoo.wang on 2018/6/10.
 * Email shihu.wang@bodyplus.cc
 *
 */

public class EditTextBottomDialog extends BaseDialog implements View.OnClickListener{
    private Activity mContext;
    private View mainView;
    private TextView titleTv;
    private TextView title_tis;
    private ImageButton cancel;
    private ImageButton confirm;
    private LengthLimitEditTextView edit_content;
    private EditText edit_content_txt;
    private OnInputDialogClickListener mListener;
    private int dialogType = 1;

    public EditTextBottomDialog(Activity context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.view_dialog_personal_class);
        mContext = context;
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }
    public EditTextBottomDialog(Activity context, int dialogType) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.view_dialog_personal_class);
        mContext = context;
        this.dialogType =  dialogType;
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    public void setTitleTxt(String str){
        titleTv.setText(str);
    }
    public void setTitleTxtTis(String str){
        title_tis.setText(str);
    }
    public void setEditHintText(String str){
        edit_content.setHint(str);
    }
    public void setEditContentHintText(String str){edit_content_txt.setHint(str);}
    public void setEditContentText(String str){
        if(str!= null){
            edit_content_txt.setText(str);
            edit_content_txt.setSelection(str.length());
        }
    }

    public void setContent(String str){
        str = subString(str,20);
        edit_content.setVisibility(View.VISIBLE);
        edit_content.setText(str);
        edit_content.setSelection(str.length());
    }

    public void setDialogClickListener(OnInputDialogClickListener listener){
        mListener = listener;
    }

    private void initView(){
        mainView = findViewById(R.id.main);
        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        titleTv = (TextView) findViewById(R.id.input_title);
        title_tis = (TextView) findViewById(R.id.title_tis);
        edit_content = (LengthLimitEditTextView)findViewById(R.id.edit_content);
        edit_content_txt = (EditText)findViewById(R.id.edit_content_txt);

        switch (dialogType){
            case 1:
                title_tis.setVisibility(View.VISIBLE);
                edit_content.setVisibility(View.VISIBLE);
                edit_content.setFocusable(true);
                edit_content.setFocusableInTouchMode(true);
                edit_content.requestFocus();
                break;
            case 2:
                edit_content.setVisibility(View.GONE);
                edit_content_txt.setVisibility(View.VISIBLE);
                edit_content_txt.setFocusable(true);
                edit_content_txt.setFocusableInTouchMode(true);
                edit_content_txt.requestFocus();
                break;
            case 3:
                edit_content.setVisibility(View.VISIBLE);
                edit_content.setFocusable(true);
                edit_content.setFocusableInTouchMode(true);
                edit_content.requestFocus();
                break;
        }


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput( 0 , InputMethodManager.SHOW_FORCED);
            }
        },200);
    }

    @Override
    public void onClick(View v) {
        if(v == cancel){
            hintKeyboard();
            mListener.onCancelBtnClick();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    dismiss();
                }
            },200);
        }else if(v == confirm){
            if(dialogType == 2){
                hintKeyboard();
                mListener.onConfirmBtnClick(edit_content_txt.getText().toString());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },200);
            }else{
                if(edit_content.getText().toString().trim().equalsIgnoreCase("")){

                    return;
                }
                hintKeyboard();
                mListener.onConfirmBtnClick(edit_content.getText().toString());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },200);
            }
        }

    }

    public interface OnInputDialogClickListener{
        void onCancelBtnClick();
        void onConfirmBtnClick(String content);
    }

    private void hintKeyboard() {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private String subString(String strSrc, int maxLength){
        if (TextUtils.isEmpty(strSrc)){
            return "";
        }
        int dindex = 0;
        int count = 0;
        while (count <= maxLength && dindex < strSrc.length()) {
            char c = strSrc.charAt(dindex++);
            if (c < 123 && c > 96) {
                // 97 - 123 26个小写字母
                count = count + 1;
            } else if (c < 58 && c > 47){
                // 48 - 57 10个数字
                count = count + 1;
            }else {
                count = count + 2;
            }
        }
        if (count > maxLength) {
            return strSrc.subSequence(0, dindex - 1).toString();
        }

        return strSrc;

    }
}
