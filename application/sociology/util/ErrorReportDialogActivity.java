package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ErrorReportDialogActivity extends Activity {
    public static final int SMARTNOTE_ERROR_REPORT = 1;
    public static final int SOURCE_BOOK_ERROR_REPORT = 2;

    @BindView(R.id.edtErrorMsgArea)
    EditText edtErrorMsgArea;
    @BindView(R.id.etRefer)
    EditText etRefer;

    @BindView(R.id.btn_group)
    RadioGroup btnGroup;
    @BindView(R.id.btn_content_error)
    RadioButton btn_content_error;
    @BindView(R.id.btn_question_error)
    RadioButton btn_question_error;
    @BindView(R.id.btn_typing_error)
    RadioButton btn_typing_error;
    @BindView(R.id.btn_other_error)
    RadioButton btn_other_error;
    @BindView(R.id.btn_system_error)
    RadioButton btn_system_error;

    String userCode;
    String ipIdx;
    int mode;

    private String NOW_CHECKED = CHECK_1;

    private static final String CHECK_1 = "A";
    private static final String CHECK_2 = "B";
    private static final String CHECK_3 = "C";
    private static final String CHECK_4 = "D";
    private static final String CHECK_5 = "E";

    private boolean isGubun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_send_error);
        ButterKnife.bind(this);

        ipIdx = getIntent().getStringExtra("ipIdx");
        userCode = getIntent().getStringExtra("userCode");
        mode = getIntent().getIntExtra("mode", 0);
        isGubun = getIntent().getBooleanExtra("gubun", false);

        btnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_content_error:
                        NOW_CHECKED = CHECK_1;
                        break;
                    case R.id.btn_question_error:
                        NOW_CHECKED = CHECK_2;
                        break;
                    case R.id.btn_typing_error:
                        NOW_CHECKED = CHECK_3;
                        break;
                    case R.id.btn_other_error:
                        NOW_CHECKED = CHECK_4;
                        break;
                    case R.id.btn_system_error:
                        NOW_CHECKED = CHECK_5;
                        break;
                }
            }
        });

        edtErrorMsgArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == R.id.edtErrorMsgArea && !TextUtils.isEmpty(edtErrorMsgArea.getText().toString())){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void captureCamera(){
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(takePictureIntent, PrefConsts.REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    //사진 절대 경로
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    //회전값
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    //정방향으로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    @OnClick(R.id.btnSend)
    void sendErrorReoprt() {
        if (CommonUtils.getConnectNetwork(this)) {

            String errorMsg = edtErrorMsgArea.getText().toString();

            if (StringUtil.isNotNull(errorMsg)) {
                if (mode == 0) {
                    JsonObject jo = new JsonObject();
                    jo.addProperty("user_code", userCode);
                    jo.addProperty("ip_idx", ipIdx);
                    if(isGubun){
                        jo.addProperty("gubun", "ox");
                    }else{
                        jo.addProperty("gubun", "");
                    }
                    jo.addProperty("report_gubun", NOW_CHECKED );
                    jo.addProperty("report_reference", etRefer.getText().toString().trim());
                    jo.addProperty("contents", errorMsg);
                    jo.addProperty("device", Build.MODEL);
                    jo.addProperty("version", Build.VERSION.RELEASE);
                    jo.addProperty("app_version", CommonUtils.getAppVersion(this));
                    jo.addProperty("contents_type", getIntent().getStringExtra("contents_type"));
                    jo.addProperty("contents_type_gubun", getIntent().getStringExtra("contents_type_gubun"));

                    RequestData.getInstance().sendErrorReport(jo, new NetworkResponse<JsonObject>() {

                        @Override
                        public void onSuccess(Call call, JsonObject clazz) {

                            Toast.makeText(ErrorReportDialogActivity.this, "오류신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFail(Call call, String msg) {
                            DisplayUtils.hideProgressDialog();
                            Toast.makeText(ErrorReportDialogActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });

                } else if(mode == SMARTNOTE_ERROR_REPORT){

                    JsonObject jo = new JsonObject();
                    jo.addProperty("user_code", userCode);
                    jo.addProperty("ip_idx", ipIdx);
                    jo.addProperty("report_gubun", NOW_CHECKED );
                    jo.addProperty("report_reference", etRefer.getText().toString().trim());
                    jo.addProperty("contents", errorMsg);
                    jo.addProperty("device", Build.MODEL);
                    jo.addProperty("version", Build.VERSION.RELEASE);
                    jo.addProperty("app_version", CommonUtils.getAppVersion(this));


                    RequestData.getInstance().getSmartNoteReport(jo, new NetworkResponse<JsonObject>() {
                        @Override
                        public void onSuccess(Call call, JsonObject clazz) {
                            Toast.makeText(ErrorReportDialogActivity.this, "오류신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFail(Call call, String msg) {
                            DisplayUtils.hideProgressDialog();
                            Toast.makeText(ErrorReportDialogActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }else if(mode == SOURCE_BOOK_ERROR_REPORT){
                    JsonObject jo = new JsonObject();
                    jo.addProperty("user_code", userCode);
                    jo.addProperty("ip_idx", ipIdx);
                    jo.addProperty("report_gubun", NOW_CHECKED );
                    jo.addProperty("report_reference", etRefer.getText().toString().trim());
                    jo.addProperty("contents", errorMsg);
                    jo.addProperty("device", Build.MODEL);
                    jo.addProperty("version", Build.VERSION.RELEASE);
                    jo.addProperty("app_version", CommonUtils.getAppVersion(this));


                    RequestData.getInstance().getHistoricalReport(jo, new NetworkResponse<JsonObject>() {
                        @Override
                        public void onSuccess(Call call, JsonObject clazz) {
                            Toast.makeText(ErrorReportDialogActivity.this, "사료학습 오류신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFail(Call call, String msg) {
                            DisplayUtils.hideProgressDialog();
                            Toast.makeText(ErrorReportDialogActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }

            } else {
                edtErrorMsgArea.requestFocus();
                Toast.makeText(ErrorReportDialogActivity.this, getResources().getString(R.string.dialog_msg_input), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnCancel)
    void btnCancle() {
        this.finish();
    }
}
