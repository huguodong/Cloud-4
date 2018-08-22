package com.ssitcloud.app_reader.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;
import com.ssitcloud.app_reader.databinding.ActivityScannerBarcodeBinding;
import com.ssitcloud.app_reader.presenter.DecodeBarcodePresenter;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LXP on 2017/5/5.
 * 二维码扫描界面
 */

public class ScannerBarcodeActivity extends BaseActivity implements SurfaceHolder.Callback, Camera.AutoFocusCallback {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private Camera camera;
    private Timer timer;
    private SurfaceHolder holder;
    private SurfaceView surfaceView;
    private volatile boolean decodeing = false;
    private volatile boolean decodeSuccess = false;//获取二维码数据成功
    private ActivityScannerBarcodeBinding databind;
    private volatile int[] windowLayout = new int[]{-1,-1,-1,-1};
    private DecodeBarcodePresenter presenter;
    private DecodeBarcodePresenter.DecodeCallBack decodeCallBack = new DecodeBarcodePresenter.DecodeCallBack() {
        @Override
        public void decodeResult(String s) {
            if (s != null) {
                decodeSuccess = true;
                presenter.sendBarcode(s, sendBarcodeCallBack);
            }
            decodeing = false;
        }
    };
    private DecodeBarcodePresenter.SendBarcodeCallBack sendBarcodeCallBack = new DecodeBarcodePresenter.SendBarcodeCallBack() {
        @Override
        public void result(SEND_BARCODE_STATE state) {
            if (state == SEND_BARCODE_STATE.auth_fail) {
                logout();
            } else if (state == SEND_BARCODE_STATE.network_error) {
                setMessage("网络连接失败");
            } else if (state == SEND_BARCODE_STATE.unbind_card) {
                setMessage("请先绑定读者证");
            } else if (state == SEND_BARCODE_STATE.success) {
                ScannerBarcodeActivity.this.finish();
                Toast.makeText(ScannerBarcodeActivity.this, "发送数据成功", Toast.LENGTH_SHORT).show();
            } else if (state == SEND_BARCODE_STATE.fail) {
                decodeSuccess = false;
                Toast.makeText(ScannerBarcodeActivity.this, "扫描失败，请重新扫描", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databind = DataBindingUtil.setContentView(this, R.layout.activity_scanner_barcode);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#221E18"));
        }

        presenter = new DecodeBarcodePresenter(this);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        holder = surfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);

        //关闭界面
        View closeView = findViewById(R.id.closeView);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannerBarcodeActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databind.line.clearAnimation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            int actionBarHight = DisplayUtil.dp2px(this,50);
            int[] location = new int[2];
            databind.window.getLocationOnScreen(location);
            windowLayout[0] = location[0];
            windowLayout[1] = location[1]-actionBarHight>0?location[1]-actionBarHight:0;
            windowLayout[2] = databind.window.getWidth();
            windowLayout[3] = databind.window.getHeight();

            //扫描线动画
            databind.line.clearAnimation();
            Animation animation = new TranslateAnimation(0, 0, 0, databind.window.getHeight());
            animation.setDuration(5000);
            animation.setRepeatCount(Animation.INFINITE);
            databind.line.startAnimation(animation);
        }
    }

    private int getBackCameraId() {
        int cameraId = -1;
        int cameraCount = Camera.getNumberOfCameras();
        for (int i = 0; i < cameraCount; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        closeCamera();
    }

    private synchronized void autoFocus() {
        if (camera != null) {
            camera.autoFocus(this);
        }
    }

    private void initCamera(SurfaceHolder holder) {
        findViewById(R.id.messageView).setVisibility(View.INVISIBLE);
        if (camera == null) {
            int backCameraId = getBackCameraId();
            if (backCameraId != -1) {
                try {
                    camera = Camera.open(backCameraId);
                } catch (Exception e) {
                    openCameraFail();
                    return;
                }
            }
        }

        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
            Camera.Size optionSize = getOptimalPreviewSize(sizeList, surfaceView.getWidth(), surfaceView.getHeight(), true);//获取一个最为适配的camera.size
            parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
            camera.setParameters(parameters);//把parameters设置给camera
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                openCameraFail();
                return;
            }
            camera.startPreview();
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    autoFocus();
                }
            }, 0, 3_000);
        }
    }

    private void closeCamera() {
        if (camera != null) {
            timer.cancel();
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 打开摄像头失败
     */
    private void openCameraFail() {
        if (timer != null) {
            timer.cancel();
        }
        surfaceDestroyed(null);
        databind.messageView.setVisibility(View.VISIBLE);
        databind.message.setText("打开摄像头失败");
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success && !decodeing && !decodeSuccess) {
            decodeing = true;
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    //裁剪图片
                    bitmap = cropBitmap(bitmap);
                    presenter.decode(bitmap, decodeCallBack);
                    camera.startPreview();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("需要摄像头权限以扫描二维码")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(ScannerBarcodeActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
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
            } else {
                initCamera(holder);
            }
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int surfaceWidth, int surfaceHeight, boolean isPortrait) {

        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isPortrait) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : sizes) {
            if ((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : sizes) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    private void setMessage(String s) {
        timer.cancel();
        surfaceDestroyed(null);
        findViewById(R.id.messageView).setVisibility(View.VISIBLE);
        TextView messageTv = (TextView) findViewById(R.id.message);
        messageTv.setText(s);
    }

    /**
     * 安装视图大小裁剪掉图片无用部分
     *
     * @param bitmap 摄像头捕获的图像
     * @return 裁剪好的图片
     */
    private Bitmap cropBitmap(Bitmap bitmap) {

        if (windowLayout[2] > 0 && windowLayout[3] > 0) {
            int displayW = DisplayUtil.getScreenWidth(this);
            int bitmapH = bitmap.getHeight();
            float coefficient = bitmapH * 1.0f / displayW;//计算系数
            int y = (int) (windowLayout[0] * coefficient);
            int x = (int) (windowLayout[1] * coefficient);
            int w = (int) (windowLayout[3] * coefficient);
            int h = (int) (windowLayout[2] * coefficient);
            //加入偏差值
            int deviation = DisplayUtil.dp2px(this, 10);
            x = x - deviation > 0 ? x : 0;
            y = y - deviation > 0 ? y : 0;
            w += deviation;
            h += deviation;
            if (x >= 0 && y >= 0 && w > 0 && h > 0 && x + w < bitmap.getWidth() && y + h < bitmapH) {
                return Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
            }
        }
        return bitmap;
    }

    public void showMyBarcode(View v){
        Intent i = new Intent(this,ReaderAuthActivity.class);
        startActivity(i);
    }
}
