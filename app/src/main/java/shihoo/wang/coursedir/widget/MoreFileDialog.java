package shihoo.wang.coursedir.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.widget.BaseDialog;

/**
 * Created by shihoo.wang on 2018/11/22.
 * Email shihu.wang@bodyplus.cc
 */

public class MoreFileDialog extends BaseDialog {

    private Context mContext;
    private MoveFileDialogListener listener;
    private String[] strings;
    private MyAdapter adapter;
    public MoreFileDialog(Context context, String[] arr, MoveFileDialogListener listener) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.view_dialog_move_file);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        mContext = context;
        this.listener = listener;
        this.strings = arr;
        initView();
    }


    private void initView(){
        ListView list_view = (ListView) findViewById(R.id.list_view);
        adapter = new MyAdapter();
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                listener.onItemClick(position);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (strings != null) {
                return strings.length;
            }else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return strings[0];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,R.layout.item_move_file,null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(strings[position]);
            return view;
        }
    }

    public interface MoveFileDialogListener{
        void onItemClick(int position);
    }


}
