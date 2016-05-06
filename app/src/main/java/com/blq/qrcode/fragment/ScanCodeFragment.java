package com.blq.qrcode.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blq.qrcode.QRScaner.CameraManager;
import com.blq.qrcode.QRScaner.CameraPreview;
import com.blq.qrcode.R;
import com.blq.qrcode.activity.AnalyzeActivity;
import com.blq.qrcode.function.GenerateStyle;
import com.blq.snblib.util.MLog;
import com.dtr.zbar.build.ZBarDecoder;

import java.lang.reflect.Field;

public class ScanCodeFragment extends BaseFragment {
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;

    private FrameLayout scanPreview;
    private Button scanRestart;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;

    private Rect mCropRect = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private RelativeLayout rl_qr;


    @Override
    public void onResume() {
        super.onResume();
        MLog.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MLog.e("onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    @Override
    protected int layout() {
        return R.layout.fragment_scan_code;
    }

    protected void initDate() {

    }

    protected void initView(View view) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scanPreview = (FrameLayout)view.findViewById(R.id.capture_preview);
        scanRestart = (Button) view.findViewById(R.id.capture_restart_scan);
        scanContainer = (RelativeLayout) view.findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) view.findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) view.findViewById(R.id.capture_scan_line);
        rl_qr = (RelativeLayout) view.findViewById(R.id.rl_qr);

        autoFocusHandler = new Handler();
        mCameraManager = new CameraManager(getActivity().getBaseContext());
        mCameraManager.setCameraOpenListener(new CameraManager.OnCameraOpenListener() {
            @Override
            public void result(boolean result) {
                if (!result){

                }
            }
        });
        try {
            mCameraManager.openDriver();

            mCamera = mCameraManager.getCamera();
            mPreview = new CameraPreview(getContext(), mCamera, previewCb, autoFocusCB);
            scanPreview.addView(mPreview);
        } catch (Error e) {
            e.printStackTrace();
        }

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation
                .RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.85f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        scanLine.startAnimation(animation);
    }

    protected void bindEvent() {
        scanRestart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();

            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }

            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            initCrop();
            ZBarDecoder zBarDecoder = new ZBarDecoder();
            String result = zBarDecoder.decodeCrop(rotatedData, size.width, size.height, mCropRect.left, mCropRect
                    .top, mCropRect.width(), mCropRect.height());

            if (!TextUtils.isEmpty(result)) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                barcodeScanned = true;
                judgeQRResult(result);
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void judgeQRResult(String result) {
        MLog.e("result",result);

        String[] q =result.split("/#/");
        for (String ss :q){
            MLog.e("解析",ss);
        }
        if(q.length<2){
            Toast.makeText(getContext(),"请扫描该应用生成的二维码",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getActivity(), AnalyzeActivity.class);
        intent.putExtra("TYPE",q[0]);
        intent.putExtra("CONTENT",q[1]);
        startActivity(intent);
    }

}
