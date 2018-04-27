package com.soowin.staremblem.ui.index.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.AddressAdapter;
import com.soowin.staremblem.ui.index.bean.AddressBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.RecordSQLiteOpenHelper;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.myView.EdittextCustomTypefaceSpan;
import com.soowin.staremblem.utils.myView.MyRadioButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 地址选择页面
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {
    public static final String ADDRESS = "address";//已有的目的地
    private final String TAG = AddressActivity.class.getCanonicalName();
    //标题栏
    TextView tvTopTitle;
    ImageView ivTopBack;
    List<AddressBean.DataBean.ResultBean> mList = new ArrayList<>();
    View footView;
    private EditText etAddress;
    private ImageView ivAddressClose;
    private ListView lvAddress;
    private TextView tvAddressSure;
    private AddressAdapter mAdapter;
    private ImageView ivAddressSearch;
    private LinearLayout llAddressClearHistory;
    private boolean flag = false;//点击的item的时候不进行查询
    private String keyWord;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private TextView tvClearHistory;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    private boolean isFirst = true;//是否为首次进入
    private String RealName;


    private static void initEditText(EditText editText, EditChange editChange) {
        String string = null;
        switch (editChange) {
            case HINT:
                string = editText.getHint().toString();
                break;
            case TEXT:
                string = editText.getText().toString();
                break;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        for (int i = 0; i < string.length(); i++) {
            String s = string.toString();
            String w = s.substring(i, i + 1);
            if (isEnglish(w)) {//判断是否为英文
                ssb.setSpan(new EdittextCustomTypefaceSpan(w, PublicApplication.typefaceE), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (isChinese(w)) {
                ssb.setSpan(new EdittextCustomTypefaceSpan(w, PublicApplication.typefaceC), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                ssb.setSpan(new TypefaceSpan(w), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        switch (editChange) {
            case HINT:
                editText.setHint(ssb);
                break;
            case TEXT:
                editText.setText(ssb);

                break;
        }

    }
    public static boolean isChinese(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 0x4e00) && (i <= 0x9fbb)) {
            return true;
        } else if (i >= 48 && i <= 57) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isEnglish(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ScreenManager.getScreenManager().pushActivity(this);
        initTitle();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 第一次进入查询所有的历史记录
     */
    private void initData() {
        RealName = PublicApplication.loginInfo.getString("RealName", PublicApplication.resourceText.getString("app_city_name", getResources().getString(R.string.app_city_name)));
        queryData();
        if (getIntent().getExtras() != null) {
            etAddress.setText(getIntent().getExtras().getString(ADDRESS));
        } else {
            isFirst = false;
        }

    }

    /**
     * 模糊查询数据
     */
    private void queryData() {

        Cursor cursor = helper.getReadableDatabase().rawQuery("select * from records where city2 = \"" + RealName + "\" order by id desc", null);
        mList.clear();
        List<AddressBean.DataBean.ResultBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name2 = cursor.getString(cursor.getColumnIndex("name"));
            String district2 = cursor.getString(cursor.getColumnIndex("district"));
            String city2 = cursor.getString(cursor.getColumnIndex("city"));
            AddressBean.DataBean.ResultBean bean = new AddressBean.DataBean.ResultBean();
            bean.setName(name2);
            bean.setCity(city2);
            bean.setDistrict(district2);
            result.add(bean);
        }
        mList.addAll(result);
        if (!mList.isEmpty()) {
            lvAddress.addFooterView(footView);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 插入数据name,city, district
     */
    private void insertData(String name, String city, String district) {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records where name=\"" + name + "\"");
        db.execSQL("insert into records(name,city,district,city2) values(\"" + name + "\"" + "," + "\"" + city + "\"" + "," + "\"" + district + "\"" + "," + "\"" + RealName + "\")");
        db.close();
    }

    private void initTitle() {
        tvTopTitle = findViewById(R.id.tv_top_title);
        ivTopBack = findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(this);
        tvTopTitle.setText(PublicApplication.resourceText.getString("app_address_title", getResources().getString(R.string.app_address_title)));

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivTopBack);

    }

    private void initView() {
        etAddress = findViewById(R.id.et_address_search);
        lvAddress = findViewById(R.id.lv_address);
        ivAddressClose = findViewById(R.id.iv_address_close);
        ivAddressClose.setOnClickListener(this);
        mAdapter = new AddressAdapter(this, mList, R.layout.item_address_layout);
        lvAddress.setAdapter(mAdapter);

        tvAddressSure = findViewById(R.id.tv_address_sure);
        tvAddressSure.setText(PublicApplication.resourceText.getString("app_address_sure", getResources().getString(R.string.app_address_sure)));

        tvAddressSure.setOnClickListener(this);

        footView = LayoutInflater.from(this).inflate(R.layout.address_foot, null);
        llAddressClearHistory = footView.findViewById(R.id.ll_address_clear_history);
        llAddressClearHistory.setOnClickListener(this);

        tvClearHistory = footView.findViewById(R.id.tv_clear_history);
        tvClearHistory.setText(PublicApplication.resourceText.getString("app_address_clear", getResources().getString(R.string.app_address_clear)));

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                flag = true;
                etAddress.setText(mList.get(i).getName());
                String name = mList.get(i).getName();
                String city = mList.get(i).getCity();
                String district = mList.get(i).getDistrict();
                insertData(name, city, district);
                if (isFirst) {
                    isFirst = false;
                }
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isFirst) {
//                    before = 0 时文字在添加
                    if (!flag && before == 0 && s.length() > 0) {
                        doSearchQuery();
                    } else {
                        flag = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!isFirst) {
                    etAddress.removeTextChangedListener(this);
                    initEditText(etAddress, EditChange.TEXT);
                    etAddress.setSelection(etAddress.length());
                    etAddress.addTextChangedListener(this);
                    keyWord = etAddress.getText().toString();
                    if (TextUtils.isEmpty(keyWord)) {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    isFirst = false;
                }
            }
        });

        ivAddressSearch = findViewById(R.id.iv_address_search);
//        ivAddressClose
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_search", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_search))
                .error(getResources().getDrawable(R.drawable.img_icon_search))
                .into(ivAddressSearch);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_search_close", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_search_close))
                .error(getResources().getDrawable(R.drawable.img_search_close))
                .into(ivAddressClose);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_address_close:
                lvAddress.removeFooterView(footView);
                etAddress.setText("");
                mList.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_address_sure:
                sureButton();
                break;
            case R.id.ll_address_clear_history:
                deleteData();
                mList.clear();
                mAdapter.notifyDataSetChanged();
//                llAddressClearHistory.setVisibility(View.GONE);
                lvAddress.removeFooterView(footView);
                break;
        }
    }

    /**
     * 点击确定按钮
     */
    public void sureButton() {
        keyWord = etAddress.getText().toString();
        if ("".equals(keyWord)) {
            showToast(PublicApplication.resourceText.getString("app_address_keyword", getResources().getString(R.string.app_address_keyword)), 3);
            return;
        } else {
            //将keyWord传到上个页面  传递0作为选择的标记
            Intent intent = new Intent();
            setResult(0, intent);
            intent.putExtra(AddressActivity.ADDRESS, keyWord);
            finish();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        lvAddress.removeFooterView(footView);

        showDialog();
        keyWord = etAddress.getText().toString();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.Address(keyWord, PublicApplication.loginInfo.getString("RealName", PublicApplication.resourceText.getString("app_city_name", getResources().getString(R.string.app_city_name))));

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, AddressBean>() {
                    @Override
                    public AddressBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, AddressBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(AddressBean baseBean) {

                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                mList.clear();
                                List<AddressBean.DataBean.ResultBean> resultBeans = baseBean.getData().getResult();
                                for (int i = 0; i < resultBeans.size(); i++) {
                                    if (resultBeans.get(i) != null) {
                                        mList.add(resultBeans.get(i));
                                    }
                                }
//                                mList.addAll(baseBean.getData().getResult());
                                mAdapter.notifyDataSetChanged();
                                break;
                            case 500:
                                showToast(baseBean.get_metadata().getMessage(), 3);
                                break;

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    enum EditChange {
        HINT, TEXT;
    }
}
