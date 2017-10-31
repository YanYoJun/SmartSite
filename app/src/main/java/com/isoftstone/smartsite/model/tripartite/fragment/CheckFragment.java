package com.isoftstone.smartsite.model.tripartite.fragment;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.tripartite.data.ITime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 点击验收后，下面验收的编辑框
 * Created by yanyongjun on 2017/10/30.
 */

public class CheckFragment extends BaseFragment {
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;

    private Resources mRes = null;
    private Drawable mWaittingAdd = null;
    private Drawable mWattingChanged = null;

    private TextView mAddressSpinner = null;
    private TextView mStatusSpinner = null;
    private TextView mTypesSpinner = null;
    private TextView mLabRevisitTime = null;
    private TextView mEditRevisitTime = null;

    private ITime mRevisitDate = null;

    private Calendar mCal = null;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recheck_check_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    public void init() {
        mRes = getResources();
        mWaittingAdd = mRes.getDrawable(R.drawable.addcolumn);
        mWaittingAdd.setBounds(0, 0, mWaittingAdd.getIntrinsicWidth(), mWaittingAdd.getIntrinsicHeight());
        mWattingChanged = mRes.getDrawable(R.drawable.editcolumn);
        mWattingChanged.setBounds(0, 0, mWattingChanged.getIntrinsicWidth(), mWattingChanged.getIntrinsicHeight());

        mCal = Calendar.getInstance();
        initView();
        initListener();
        initGridView();
    }

    public void initView() {
        mLabRevisitTime = (TextView) getView().findViewById(R.id.lab_revisit_time);
        mEditRevisitTime = (TextView) getView().findViewById(R.id.edit_lab_revisit_time);
    }

    public void initListener() {
        mEditRevisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEditRevisitTime.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mRevisitDate = new ITime(year, monthOfYear + 1, dayOfMonth);
                        mEditRevisitTime.setTextColor(mRes.getColor(R.color.main_text_color));
                        mLabRevisitTime.setCompoundDrawables(mWattingChanged, null, null, null);
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    public void initGridView() {
        mAttachView = (GridView) getView().findViewById(R.id.grid_view);

        mData = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<>();
        data.put("image", R.drawable.attachment);
        mData.add(data);
        mAttachAdapter = new SimpleAdapter(getActivity(), mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                if (position == mData.size() - 1) {
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image*//*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }*/
            }
        });
    }
}
