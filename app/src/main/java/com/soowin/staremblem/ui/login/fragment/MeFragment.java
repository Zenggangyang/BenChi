package com.soowin.staremblem.ui.login.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.CouponListActivity;
import com.soowin.staremblem.ui.index.activity.MainActivity;
import com.soowin.staremblem.ui.index.activity.MeInfoActivity;
import com.soowin.staremblem.ui.index.activity.OrderListActivity;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.OrderListBean;
import com.soowin.staremblem.ui.index.bean.PageHtmlBean;
import com.soowin.staremblem.ui.index.bean.UserInfoBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.ui.login.activity.WebViewActivity;
import com.soowin.staremblem.utils.Base64Encoder;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;
import com.soowin.staremblem.utils.myView.CustomToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
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
 * 我的
 * Created by hxt on 2018/3/10 0010.
 */

public class MeFragment extends Fragment implements View.OnClickListener {
    //拍照部分回掉
    private static final int PHOTOZOOM_CODE = 1003;
    //权限部分回掉
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1004;
    private final String TAG = MeFragment.class.getCanonicalName();
    private final int FILESELECT_CODE = 1001;
    private final int CAMERA_CODE = 1002;
    MainActivity a;
    List<UserInfoBean.DataBean> data1 = new ArrayList<>();
    //    未过期数据
    List<CouponListBean.DataBean> noOutDate = new ArrayList<>();
    List<CouponListBean.DataBean> OutDate = new ArrayList<>();
    List<OrderListBean.DataBean> OrderData = new ArrayList<>();
    UserInfoBean.DataBean dataBean = new UserInfoBean.DataBean();
    private View view;
    //拍照
    private Uri mUri;
    private String picturePath = "";
    //内容部分
    private ImageView ivAvatar;
    private TextView tvTitle;
    //拍照部分的弹出选择框
    private PopupWindow mPopupWindow;
    /*跳转部分*/
    private RelativeLayout rlCoupon;//跳转到我的卡券
    private RelativeLayout rlOrder;//跳转到出行订单
    private RelativeLayout rlUserAgreeMent;//跳转到用户协议
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    private String PageHtmlUrl = "";//用户协议网址
    private TextView tvRelaName;
    private TextView tvBalance;//余额
    private LinearLayout rlPersonal;
    /*屏幕适配*/
    private int width;
    private int height;
    private TextView tvBgaCoupon;
    private TextView tvLoginOff;
    //登陆
    private boolean isChongFuLogin = false;//初始化设置为没有进入过登陆页
    /*静态展示页*/
    private TextView tvStringYue;
    private TextView tvStringCoupon;
    private TextView tvStringMineOrder;
    private TextView tvStringUserAgrement;
    private ImageView ivUserAgreement;
    private ImageView ivCouponOrderBack;
    private ImageView ivCouponBack;
    private LinearLayout llYuE;

    //图片转化成base64字符串
    public static String getImageStr(String path) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            Log.e("base64", "getImageStr: ", e);
            e.printStackTrace();
        }
        //对字节数组Base64编码
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), R.layout.fragment_me, null);
        }

        a = (MainActivity) getActivity();
        a.displayPage = 1;
        initWindow();
//        initRefresh();
        initView();
        return view;
    }

    private void initWindow() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        width = dm.widthPixels;
        height = dm.heightPixels;

    }

    private void initView() {
        ivAvatar = view.findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(this);
        initContentView();
        tvStringYue = view.findViewById(R.id.tv_string_yue);
        tvStringYue.setText(PublicApplication.resourceText.getString("yue", getResources().getString(R.string.yue)));
        tvStringCoupon = view.findViewById(R.id.tv_string_coupon);
        tvStringCoupon.setText(PublicApplication.resourceText.getString("app_coupon_title", getResources().getString(R.string.app_coupon_title)));
        tvStringMineOrder = view.findViewById(R.id.tv_string_mine_order);
        tvStringMineOrder.setText(PublicApplication.resourceText.getString("app_personal_center_order", getResources().getString(R.string.app_personal_center_order)));
        tvStringUserAgrement = view.findViewById(R.id.tv_string_user_agrement);
        tvStringUserAgrement.setText(PublicApplication.resourceText.getString("app_personal_center_user_agreement", getResources().getString(R.string.app_personal_center_user_agreement)));
        ivUserAgreement = view.findViewById(R.id.iv_user_agreement);
        ivCouponOrderBack = view.findViewById(R.id.iv_coupon_order_back);
        ivCouponBack = view.findViewById(R.id.iv_coupon_back);
        llYuE = view.findViewById(R.id.ll_yu_e);
        llYuE.setOnClickListener(null);
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_right_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_right_back))
                .error(getResources().getDrawable(R.drawable.img_right_back))
                .into(ivUserAgreement);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_right_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_right_back))
                .error(getResources().getDrawable(R.drawable.img_right_back))
                .into(ivCouponOrderBack);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_right_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_right_back))
                .error(getResources().getDrawable(R.drawable.img_right_back))
                .into(ivCouponBack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);// 在onDestory()方法中解绑EventBus
        a.dismissDialog();
    }

    /**
     * 初始化数据 需要请求两个接口  1 个人信息 2 用户协议
     */
    private void initData() {
        getPersonalInformation();
        getUserAgreMent();
        getCouponNoOutDate();
        getCouponOutDate();
    }

    /*没有过期的卡券*/
    private void getCouponNoOutDate() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MyCardList("0", "");
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CouponListBean>() {
                    @Override
                    public CouponListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CouponListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CouponListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CouponListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                noOutDate = data.getData();
                                if (noOutDate.size() > 0) {
                                    tvBgaCoupon.setVisibility(View.VISIBLE);
                                    tvBgaCoupon.setText("" + String.valueOf(noOutDate.size()));
                                } else {
                                    tvBgaCoupon.setVisibility(View.GONE);
                                }
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }

    /*过期的卡券*/
    private void getCouponOutDate() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MyCardList("1", "");
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CouponListBean>() {
                    @Override
                    public CouponListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CouponListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CouponListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CouponListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                OutDate = data.getData();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }

    /*用户协议*/
    private void getUserAgreMent() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.LoadPageHtml();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, PageHtmlBean>() {
                    @Override
                    public PageHtmlBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, PageHtmlBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageHtmlBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PageHtmlBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                PageHtmlUrl = data.getData().getPageHtmlUrl();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*获取个人的用户信息  userinfo */
    private void getPersonalInformation() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.UserInfo();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, UserInfoBean>() {
                    @Override
                    public UserInfoBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, UserInfoBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(UserInfoBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:

                                dataBean = data.getData();
                                Glide.with(getActivity())
                                        .load(Utlis.getAvatar(dataBean.getHeadImgShow()))
                                        .asBitmap()
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .error(R.drawable.img_avatar_null)
                                        .placeholder(R.drawable.img_avatar_null)
                                        .centerCrop()
                                        .listener(new RequestListener<String, Bitmap>() {
                                            @Override
                                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                                Log.e(TAG, "onException: "+e.getMessage() );
                                               return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                Log.e(TAG, "onResourceReady: " );
                                                return false;
                                            }
                                        })
                                        .into(new BitmapImageViewTarget(ivAvatar) {
                                            @Override
                                            protected void setResource(Bitmap resource) {
                                                if (getActivity() != null && getActivity().getResources() != null) {
                                                    RoundedBitmapDrawable circularBitmapDrawable =
                                                            RoundedBitmapDrawableFactory.create(
                                                                    getActivity().getResources(),
                                                                    resource);
                                                    circularBitmapDrawable.setCircular(true);
                                                    ivAvatar.setImageDrawable(circularBitmapDrawable);
                                                }
                                            }
                                        });
                                tvBalance.setText(String.valueOf(dataBean.getBalance()));
                                tvRelaName.setText(dataBean.getRealName());
                             /* PublicApplication.pathAvatar = dataBean.getHeadImgShow();*/
                                PublicApplication.loginInfo.edit().putString("UserID", String.valueOf(dataBean.getUserID())).apply();
                                PublicApplication.loginInfo.edit().putString("Mobile", dataBean.getMobile()).apply();
                                PublicApplication.loginInfo.edit().putString("RealName", dataBean.getRealName()).apply();
                                PublicApplication.loginInfo.edit().putString("NickName", dataBean.getNickName()).apply();
                                PublicApplication.loginInfo.edit().putString("avatar", dataBean.getAvatar()).apply();
                                PublicApplication.loginInfo.edit().putString("Sex", String.valueOf(dataBean.getSex())).apply();
                                PublicApplication.loginInfo.edit().putString("Balance", String.valueOf(dataBean.getBalance())).apply();
                                PublicApplication.loginInfo.edit().putString("OpenID", String.valueOf(dataBean.getOpenID())).apply();
                                PublicApplication.loginInfo.edit().putString("ServiceTel", String.valueOf(dataBean.getServiceTel())).apply();
                                break;
                            case 500:
                                CustomToast.showFailYellowToast(MeFragment.this.getActivity(), data.get_metadata().getMessage());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.showFailToast(getActivity(), PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*初始化内容部分*/
    private void initContentView() {
        rlPersonal = view.findViewById(R.id.rl_personal);
        rlPersonal.setOnClickListener(this);
        rlCoupon = view.findViewById(R.id.rl_coupon);
        rlOrder = view.findViewById(R.id.rl_order);
        rlUserAgreeMent = view.findViewById(R.id.rl_user_agreement);
        rlCoupon.setOnClickListener(this);
        rlOrder.setOnClickListener(this);
        rlUserAgreeMent.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.app_personal_center));
        tvRelaName = view.findViewById(R.id.iv_me_name);
        tvBalance = view.findViewById(R.id.tv_yu_e);

        tvRelaName.setOnClickListener(this);
        tvBgaCoupon = view.findViewById(R.id.tv_bga_daichuli);
        tvLoginOff = view.findViewById(R.id.tv_login_off);
        tvLoginOff.setOnClickListener(this);
        tvLoginOff.setText(PublicApplication.resourceText.getString("app_personal_center_tuichu", getResources().getString(R.string.app_personal_center_tuichu)));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CODE:
                if (resultCode == -1)
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                        photoZoom();
                    else
                        startPhotoZoom(mUri);
                break;
            case PHOTOZOOM_CODE:
                /*Glide.with(this)
                        .load(PublicApplication.pathAvatar)
                        .asBitmap()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.img_avatar_null)
                        .placeholder(R.drawable.img_avatar_null)
                        .centerCrop()
                        .into(new BitmapImageViewTarget(ivAvatar) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                                circmularBitmapDrawable.setCircular(true);
                                ivAvatar.setImageDrawable(circularBitmapDrawable);
                            }
                        });*/
                if (data != null) {
                    updateAvatar(PublicApplication.pathAvatar);//上传头像
                    mPopupWindow.dismiss();
                } else {
                    mPopupWindow.dismiss();
                }

                break;
            case FILESELECT_CODE:
                if (data != null) {
                    if (resultCode == -1) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            Log.e("onActivityResult: ", data.toString());
                            Uri uri = data.getData();
                            photoZoom(uri);
                        } else {
                            Uri uri = data.getData();
                            startPhotoZoom(uri);
                        }
                    }
                }
                break;
        }
    }

    /**
     * 上传头像
     */
    private void updateAvatar(final String path) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.LoginInfo(PublicApplication.loginInfo.getString("Mobile", ""),
                        "",
                        "", getImageStr(path));

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, BaseBean>() {
                    @Override
                    public BaseBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, BaseBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                CustomToast.showSuccessToast(getActivity(), getActivity().getResources().getString(R.string.app_photo_success));
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.showFailToast(getActivity(), PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)));
                    }

                    @Override
                    public void onComplete() {
                        getPersonalInformation();
                    }
                });
    }


    /**
     * 弹出修改头像提示框
     */
    private void mShowDialog() {
        showPopUpWindow();
    }

    /**
     * 显示出拍摄照片的选择的PopUpWindow
     */
    @SuppressLint("WrongConstant")
    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_pop_take_photo, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView tvTakePhoto = contentView.findViewById(R.id.tv_photo);
        TextView tvAlbum = contentView.findViewById(R.id.tv_album);
        TextView tvCancel = contentView.findViewById(R.id.tv_cancel);
        LinearLayout llPop = contentView.findViewById(R.id.ll_top);
        llPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        /*拍照*/
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCamera();
                mPopupWindow.dismiss();
            }
        });
        /*选取照片*/
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(intent, FILESELECT_CODE);
                mPopupWindow.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失

        mPopupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_me, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);

        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 7。0拍照
     */
    public void onClickCamera() {
        File externalDir = Environment.getExternalStorageDirectory();
        //头像地址
        File path = new File(externalDir.getAbsolutePath() + "/StarEmblem/Images");
        File path1 = new File(externalDir.getAbsolutePath() + "/StarEmblem/Picture");
        if (!path.exists()) { // 判断目录是否存在
            if (path.mkdirs()) {
                Log.e(TAG, "mkdirs-->true");
            }// 创建目录
        }
        if (!path1.exists()) { // 判断目录是否存在
            if (path1.mkdirs()) {
                Log.e(TAG, "mkdirs-->true");
            }// 创建目录
        }
        PublicApplication.pathAvatar = path + "/heard.jpg";
        picturePath = path1 + "/heard.jpg";
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {

            File mCurrentPhotoFile = new File(picturePath);
            mUri = Uri.fromFile(mCurrentPhotoFile);
            Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.soowin.staremblem.myprovider", mCurrentPhotoFile);
                intentC.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentC.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intentC.putExtra("return-data", true);
                intentC.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            }
            startActivityForResult(intentC, CAMERA_CODE);
        } else {
            a.showToast("没有SD卡", 2);
        }
    }

    /**
     * 7.0裁剪图片
     */
    private void photoZoom() {
        Uri imageUri = FileProvider.getUriForFile(getActivity(),
                "com.soowin.staremblem.myprovider",
                new File(picturePath));//通过FileProvider创建一个content类型的Uri

        startPhotoZoom(imageUri);
    }

    /**
     * 7.0裁剪图片
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void photoZoom(Uri uri) {
        String inputImg = Utlis.getPath(getActivity(), uri);
        try {
            uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.soowin.staremblem.myprovider", new File(inputImg));
            startPhotoZoom(uri);
        } catch (Exception e) {
            a.showToast("该图片无法使用", 2);
        }
    }

    /**
     * 跳转到剪切界面
     **/
    private void startPhotoZoom(Uri uri) {
        // 输出地址
        File externalDir = Environment.getExternalStorageDirectory();
        File path = new File(externalDir.getAbsolutePath() + "/StarEmblem/Images");
        if (!path.exists()) { // 判断目录是否存在
            if (path.mkdirs()) {
                Log.e("dbMethond", "mkdirs-->true");
            }// 创建目录
        }
        PublicApplication.pathAvatar = path + "/heard.jpg";

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        //设置输出地址
        intent.putExtra("output", Uri.fromFile(new File(PublicApplication.pathAvatar)));
        intent.putExtra("outputFormat", "JPEG");// 返回格式

        startActivityForResult(intent, PHOTOZOOM_CODE);
    }

    /**
     * 初始化权限
     */
    private void initPermissions() {
        /**android 6.0 权限申请**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                mShowDialog();
            }
        } else {
            mShowDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限申请结果
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            boolean b = true;
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {/*用户拒绝了权限*/
                    b = false;
                }
            }
            if (b)
                mShowDialog();
            else
                a.showMissingPermissionDialog();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isChongFuLogin) {//判断是否重复进入过登陆页
            isChongFuLogin = false;
            if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {//如果没有第二次进入当前页面 并且没有登陆信息 再进行操作
                MainActivity a = (MainActivity) getActivity();
                a.loginStatus(0);
            }
        } else if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {//没有进入过登陆页且没有登陆信息
            if (!a.mShowRloginDialog.isShowing()) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                isChongFuLogin = true;
            }
        } else
            isChongFuLogin = true;
        if (!StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {//是否有登陆信息
            initData();
        }
    }

    /**
     * 这段可以解决fragment嵌套fragment会崩溃的问题
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            //参数是固定写法
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar://点击头像
                initPermissions();
                break;
            case R.id.rl_coupon://跳转到我的卡券
                Intent intent2 = new Intent(getActivity(), CouponListActivity.class);

                intent2.putExtra(CouponListActivity.NOOUTDATE, (Serializable) noOutDate );
                intent2.putExtra(CouponListActivity.OUTDATE, (Serializable) OutDate );
                intent2.putExtra(CouponListActivity.ORDERID, "");
                startActivity(intent2);

                break;
            case R.id.rl_order://跳转到出行订单
                Intent intent3 = new Intent(getActivity(), OrderListActivity.class);
                intent3.putExtra("nulldata", (Serializable) OrderData);
                startActivity(intent3);

                break;
            case R.id.rl_user_agreement://跳转到用户协议 webview
                if (StrUtils.isEmpty(PageHtmlUrl)) {
                    CustomToast.showFailToast(getActivity(), PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)));
                } else {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.URLS, PageHtmlUrl);
                    intent.putExtra(WebViewActivity.TITLE, "服务协议");
                    startActivity(intent);
                }
                break;
            case R.id.iv_me_name:
                if (dataBean != null) {
                    Intent intent1 = new Intent(getActivity(), MeInfoActivity.class);
                    intent1.putExtra("data", (Serializable) dataBean);
                    startActivity(intent1);
                } else {
                    CustomToast.showFailToast(getActivity(), PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)));
                }

                break;
            case R.id.rl_personal:

                if (dataBean != null) {
                    Intent intent4 = new Intent(getActivity(), MeInfoActivity.class);
                    intent4.putExtra("data", (Serializable) dataBean);
                    startActivity(intent4);
                } else {
                    CustomToast.showFailToast(getActivity(), PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)));

                }

                break;
            case R.id.tv_login_off:
                PublicApplication.loginInfo.edit().putString("token", "").apply();
                onResume();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

}
