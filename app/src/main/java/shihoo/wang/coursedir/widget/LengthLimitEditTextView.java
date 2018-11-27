package shihoo.wang.coursedir.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import shihoo.wang.coursedir.R;

/**
 * Created by shihoo.wang on 2018/11/24.
 * Email shihu.wang@bodyplus.cc
 *
 * 限制长度的TextView
 * 符号等小写字母占一个长度，汉字、大写字母占两个长度
 */

public class LengthLimitEditTextView extends android.support.v7.widget.AppCompatEditText{

    private int mLimitLength;

    public LengthLimitEditTextView(Context context) {
        super(context);
        init(context, null);
    }

    public LengthLimitEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LengthLimitEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LimitLengthEditText, 0, 0);
            try {
                if(a.hasValue(R.styleable.LimitLengthEditText_max_text_length)){
                    mLimitLength =  a.getInt(R.styleable.LimitLengthEditText_max_text_length, 20); // 默认20个长度
                    super.setFilters(new InputFilter[]{filter});
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }


    InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence src, int start, int end, Spanned dest, int dstart, int dend) {
            int dindex = 0;
            int count = 0;

            while (count <= mLimitLength && dindex < dest.length()) {
                char c = dest.charAt(dindex++);
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

            if (count > mLimitLength) {
                return dest.subSequence(0, dindex - 1);
            }

            int sindex = 0;
            while (count <= mLimitLength && sindex < src.length()) {
                char c = src.charAt(sindex++);
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

            if (count > mLimitLength) {
                sindex--;
            }
            return src.subSequence(0, sindex);
        }
    };
}
