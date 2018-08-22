package com.ssitcloud.app_reader.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.AppSettingEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoMenuViewEntity;
import com.ssitcloud.app_reader.myview.SlidingMenu;
import com.ssitcloud.app_reader.myview.myViewInterface.SlidingMenuOpenListener;
import com.ssitcloud.app_reader.presenter.ContentViewPresenter;
import com.ssitcloud.app_reader.presenter.ReaderInfoMenuPresenter;
import com.ssitcloud.app_reader.service.DataUpdateService;
import com.ssitcloud.app_reader.view.viewInterface.ContentViewI;
import com.ssitcloud.app_reader.view.viewInterface.ReaderInfoMenuViewI;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/15.
 * app主界面
 */

public class MainActivity extends BaseActivity implements ReaderInfoMenuViewI, ContentViewI {
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;

    private SlidingMenu sm;
    private View menuView;
    private View contentView;

    //menu child view
    private TextView readerName;//读者姓名
    private TextView cardNo;//卡号
    private TextView countBorrow;//已经借阅数
    private TextView canBorrow;//总共可借阅数
    private TextView advanceCharge;//预付款
    private TextView deposit;//押金
    private TextView arrears;//欠款
    private TextView myCardTV;//我的读者证
    private TextView readerLibraryName;
    //我的借阅点击栏
    private View menuMyBorrow;
    //content View
    private ViewGroup contentFounctionView;
    private View contentWaitView;
    private TextView contentLibraryName;
    private TextView contentReaderName;
    //founction view
    private View readerAuth;//读者认证
    private final String readerAuthServiceType = "020101";
    private View bookSeach;//图书检索
    private final String bookSeachServiceType = "020102";
    private ViewGroup renewView;//续借菜单
    private final String renewServiceType = "020103";
    private View newMessage;//最新消息
    private final String newMessageServiceType = "020105";
    private View reservationView;//预借
    private final String reservationServiceType= "020104";
    //presenter
    private ReaderInfoMenuPresenter readerInfoMenuPresenter;
    private ContentViewPresenter contentViewPresenter;
    //other args
    private volatile boolean alreadySetMenuView = false;//是否已经设置过了菜单视图
    private volatile boolean alreadySetContentView = false;//是否已经设置过了主视图
    private volatile boolean alreadyFinish = false;//是否已经关闭过此视图
    private volatile int updateMenuState = 0;//0 unUpdate,1 updateing...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        readerInfoMenuPresenter = new ReaderInfoMenuPresenter(this, this);
        contentViewPresenter = new ContentViewPresenter(this, this);

        sm = (SlidingMenu) findViewById(R.id.slidingMenu);
        View mengban = sm.findViewById(R.id.context_foreground);
        sm.setContextForeground(mengban);

        menuView = sm.findViewById(R.id.reader_info_menu);
        contentView = sm.findViewById(R.id.context_view);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            int statusBarHeight = -1;
            //设置侧滑视图的状态内边距
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
            if (statusBarHeight != -1) {
                menuView.setPadding(0, statusBarHeight, 0, 0);
            }
        }

        //init menuView child view
        readerName = (TextView) menuView.findViewById(R.id.reader_name);
        cardNo = (TextView) menuView.findViewById(R.id.reader_card_no);
        countBorrow = (TextView) menuView.findViewById(R.id.count_borrow);
        canBorrow = (TextView) menuView.findViewById(R.id.can_borrow);
        advanceCharge = (TextView) menuView.findViewById(R.id.advanceCharge);
        deposit = (TextView) menuView.findViewById(R.id.deposit);
        arrears = (TextView) menuView.findViewById(R.id.arrears);
        myCardTV = (TextView) menuView.findViewById(R.id.myCardTV);
        readerLibraryName = (TextView) menuView.findViewById(R.id.readerLibraryName);
        //我的读者证点击栏
        View myCard = menuView.findViewById(R.id.myCard);
        myCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReaderCardListActivity.class);
                startActivity(intent);
            }
        });
        //个人设置点击栏
        View menuPersonalSetting = menuView.findViewById(R.id.readerPersonalSetting);
        menuPersonalSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personalSetting = new Intent(MainActivity.this, PersonalSettingActivity.class);
                startActivity(personalSetting);
            }
        });
        //我的借阅点击栏
        menuMyBorrow = menuView.findViewById(R.id.readerMyBorrow);
        menuMyBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RenewActivity.class);
                ReaderCardDbEntity card = (ReaderCardDbEntity) v.getTag();
                if (card != null) {
                    intent.putExtra("surplusBorrow", StringUtils.getStr(card.getSurplus_count(), "0"));//剩余可借阅数
                    intent.putExtra("countBorrow", StringUtils.getStr(card.getAllown_loancount(), "0"));//总共可以借阅数
                }
                startActivity(intent);
            }
        });
        //我的收藏点击栏
        View readerFavorit = findViewById(R.id.readerFavorit);
        readerFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavoritActivity.class);
                startActivity(i);
            }
        });
        //设置打开菜单监听事件
        sm.setSlidingMenuOpenListener(new SlidingMenuOpenListener() {
            @Override
            public void onOpenListener(View menu) {
                loadMenuView();
            }
        });

        //init content view
        contentFounctionView = (ViewGroup) contentView.findViewById(R.id.contentFunctionView);
        contentWaitView = contentView.findViewById(R.id.sub_main_function_wait);
        contentLibraryName = (TextView) contentView.findViewById(R.id.libraryName);
        contentLibraryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReaderCardListActivity.class);
                startActivity(intent);
            }
        });
        contentReaderName = (TextView) contentView.findViewById(R.id.readerName);
        View userButton = contentView.findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.showMenu();
            }
        });
        //init content view
        LayoutInflater inflater = LayoutInflater.from(this);
        renewView = (ViewGroup) inflater.inflate(R.layout.sub_function_book_renew, null);
        renewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent renewIntent = new Intent(MainActivity.this, RenewActivity.class);
                ReaderCardDbEntity card = (ReaderCardDbEntity) v.getTag();
                if (card != null) {
                    renewIntent.putExtra("surplusBorrow", StringUtils.getStr(card.getSurplus_count(), "0"));//剩余可借阅数
                    renewIntent.putExtra("countBorrow", StringUtils.getStr(card.getAllown_loancount(), "0"));//总共可以借阅数
                }
                startActivity(renewIntent);
            }
        });
        readerAuth = inflater.inflate(R.layout.sub_function_reader_auth, null);
        readerAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReaderAuthActivity.class);
                startActivity(i);
            }
        });
        bookSeach = inflater.inflate(R.layout.sub_function_book_seach, null);
        bookSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BookItemActivity.class);
                startActivity(i);
            }
        });
        newMessage = inflater.inflate(R.layout.sub_function_electroin, null);
        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ElectronicCertificateActivity.class);
                startActivity(i);
            }
        });
        reservationView = inflater.inflate(R.layout.sub_function_reservation,null);
        reservationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(i);
            }
        });

        View scanBarcodeView = findViewById(R.id.scanBarcode);
        scanBarcodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ScannerBarcodeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!alreadyFinish) {
            contentViewPresenter.loadData();
            loadMenuView();
        }
        if (Build.VERSION.SDK_INT >= 23) {//申请写卡权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alreadyFinish = true;
        Intent stopUpdateService = new Intent(this, DataUpdateService.class);
        stopService(stopUpdateService);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (sm.isShowMenu()) {
                sm.closeMenu();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSIONS_REQUEST_STORAGE){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("需要写SD卡权限，用于缓存")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_STORAGE);
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
        }
    }

    @Override
    public boolean alreadySetMenuView() {
        return alreadySetMenuView;
    }

    @Override
    public boolean alreadySetContentView() {
        return alreadySetContentView;
    }

    @Override
    public void setView(ReaderInfoMenuViewEntity data) {
        if (alreadyFinish) {
            return;
        }
        updateMenuState = 0;
        ReaderCardDbEntity card = data.getReaderCard();
        LoginInfoDbEntity readerInfo = data.getReaderInfo();

        readerName.setText(readerInfo.getReader_name());
        Resources res = getResources();
        String moneyUnit = res.getString(R.string.local_money);
        if (card == null) {//没有绑定读者证
            updateRenewView("0");
            canBorrow.setText("/0");
            advanceCharge.setText("0");
            deposit.setText(String.format("0%s", moneyUnit));
            arrears.setText(String.format("0%s", moneyUnit));
            cardNo.setText(res.getString(R.string.reader_info_ubbind_my_card_tip2));
            cardNo.setTextColor(Color.parseColor("#EB9C32"));
            myCardTV.setText(res.getString(R.string.reader_info_ubbind_my_card_tip2));
            readerLibraryName.setVisibility(View.GONE);

            //update context library
            contentLibraryName.setText(res.getString(R.string.reader_info_unBind_cardno_tip));//设置图书馆名称
        } else {

            Integer allown_loancount = card.getAllown_loancount();
            Integer surplus_count = card.getSurplus_count();
            if (allown_loancount != null && surplus_count != null) {
                //更新主界面的已经借书
                updateRenewView(String.valueOf(allown_loancount - surplus_count));
            } else {
                //更新主界面的已经借书
                updateRenewView("0");
            }
            canBorrow.setText("/" + StringUtils.getStr(card.getAllown_loancount(), "0"));
            advanceCharge.setText(StringUtils.getStr(card.getAdvance_charge(), "0"));
            deposit.setText(StringUtils.getStr(card.getDeposit(), "0") + moneyUnit);//押金
            arrears.setText(StringUtils.getStr(card.getArrearage(), "0") + moneyUnit);
            cardNo.setText(String.format(res.getString(R.string.reader_info_reader_card_tip), StringUtils.getStr(card.getCard_no(), res.getString(R.string.reader_info_unknow_cardno_tip))));
            cardNo.setTextColor(Color.parseColor("#888888"));
            readerLibraryName.setVisibility(View.VISIBLE);
            readerLibraryName.setText(StringUtils.getStr(card.getLib_name(), res.getString(R.string.reader_info_unknow_library_tip)));
            myCardTV.setText(res.getString(R.string.reader_info_my_card_tip2));
            menuMyBorrow.setTag(card);
            //update context library
            contentLibraryName.setText(StringUtils.getStr(card.getLib_name(), res.getString(R.string.reader_info_unknow_library_tip)));
        }
        //同步数据到主界面的视图
        syncContentView(card, readerInfo);
        alreadySetMenuView = true;
    }

    @Override
    public void otherView(Map<String, Object> data, int code) {
        if (alreadyFinish) {
            return;
        }
        if (code == -1) {
            alreadyFinish = true;
            Toast.makeText(this, getResources().getString(R.string.unlogin_message), Toast.LENGTH_SHORT).show();
            this.logout();
        }

    }

    @Override
    public void setView(Map<String, Object> data) {
        if (alreadyFinish) {
            return;
        }
        ReaderCardDbEntity readerCardData = (ReaderCardDbEntity) data.get("ReaderCardDbEntity");
        if (readerCardData != null) {
            contentLibraryName.setText(
                    StringUtils.getStr(readerCardData.getLib_name(),
                            getResources().getString(R.string.reader_info_unknow_library_tip))
            );

            //other function view
            Integer allown_loancount = readerCardData.getAllown_loancount();
            Integer surplus_count = readerCardData.getSurplus_count();
            String countBorrowStr;
            if (allown_loancount != null && surplus_count != null) {
                countBorrowStr = String.valueOf(allown_loancount - surplus_count);
            } else {
                countBorrowStr = "0";
            }
            updateRenewView(countBorrowStr);
            if (renewView != null) {
                renewView.setTag(readerCardData);
            }
        }

        LoginInfoDbEntity readerInfoData = (LoginInfoDbEntity) data.get("LoginInfoDbEntity");
        if (readerInfoData != null) {
            contentReaderName.setText(readerInfoData.getReader_name());
            alreadySetContentView = true;
        }
    }

    @Override
    public void setFouctionView(List<AppSettingEntity> contentFcution) {
        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams dividingLineParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(this, 1.0f));
        contentFounctionView.removeAllViews();
        for (AppSettingEntity appSettingEntity : contentFcution) {
            View fuctionView = getFuctionView(appSettingEntity.getService_id());
            if (fuctionView != null && fuctionView.getParent() == null) {
                LinearLayout dividingLine = new LinearLayout(this);
                dividingLine.setBackgroundColor(ContextCompat.getColor(this, R.color.border_line_corlor));
                contentFounctionView.addView(fuctionView, params);
                contentFounctionView.addView(dividingLine, dividingLineParams);//加入分割线
            }
        }

    }

    @Override
    public void showWait() {
        if (contentWaitView != null) {
            contentWaitView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideWait() {
        if (contentWaitView != null) {
            contentWaitView.setVisibility(View.GONE);
        }
    }

    private void updateRenewView(String s) {
        if (renewView.getParent() != null) {
            TextView alreadyRenew = (TextView) renewView.findViewById(R.id.subRenewCount);
            alreadyRenew.setText(String.format(getResources().getString(R.string.main_view_renew_count), s));
        }
        if (countBorrow != null) {
            countBorrow.setText(s);
        }
    }

    private View getFuctionView(String serviceType) {
        if (serviceType.equals(renewServiceType)) {
            return renewView;
        } else if (serviceType.equals(readerAuthServiceType)) {
            return readerAuth;
        } else if (serviceType.equals(bookSeachServiceType)) {
            return bookSeach;
        } else if (serviceType.equals(newMessageServiceType)) {
            return newMessage;
        } else if(serviceType.equals(reservationServiceType)){
            return reservationView;
        }
        return null;
    }

    private void syncContentView(ReaderCardDbEntity card, LoginInfoDbEntity read) {
        if (renewView != null) {
            renewView.setTag(card);
        }
        if (card == null) {
            contentFounctionView.removeAllViews();//移除功能界面
        } else {
            if (!card.getLib_idx().equals(contentFounctionView.getTag())) {//比较是否需要更新功能视图
                contentFounctionView.setTag(card.getLib_idx());
                contentViewPresenter.loadData();
            }
        }

        if (read != null) {
            contentReaderName.setText(read.getReader_name());
        }
    }

    private void loadMenuView() {
        if (updateMenuState == 0) {
            updateMenuState = 1;
            readerInfoMenuPresenter.loadData();
        }
    }
}
