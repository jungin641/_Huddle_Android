package com.example.sungshin.huddle.LoginJoin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    Button btnCheckID, btnCompleteJoin, btnInputProfile, btnBack;
    EditText inputId, inputPasswd, inputRePasswd, inputName, inputPhone, inputHome, inputWork;
    ImageView profile;
    //중복체크해주는 boolean 변수
    Boolean checkID = false;

    final int REQ_CODE_SELECT_IMAGE = 100;
    String imgUrl = "";
    Uri data;
    NetworkService service;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btnCheckID = (Button) findViewById(R.id.btnCheckId);
        btnCompleteJoin = (Button) findViewById(R.id.btnCompleteJoin);
        btnInputProfile = (Button) findViewById(R.id.btnInputProfile);
        btnBack = (Button) findViewById(R.id.btnBack);
        inputId = (EditText) findViewById(R.id.inputID);
        inputPasswd = (EditText) findViewById(R.id.inputPasswd);
        inputRePasswd = (EditText) findViewById(R.id.inputRePasswd);
        inputName = (EditText) findViewById(R.id.inputName);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputHome = (EditText) findViewById(R.id.inputHome);
        inputWork = (EditText) findViewById(R.id.inputWork);
        profile = (ImageView) findViewById(R.id.profile);


//        // 회원 가입버튼을 눌렀을 때, 사용자에게 회원 가입중 중이라는 것을 보여줄 필요가 있습니다.
//        mProgressDialog = new ProgressDialog(JoinActivity.this);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setMessage("회원가입중...");
//        mProgressDialog.setIndeterminate(true);

        //미리 retrofit를 bulid 한 것을 가져온다.
        service = ApplicationController.getInstance().getNetworkService();


        //사진 갤러리 호출 (자신의 스마트 폰에서 이미지를 가져오는 부분)
        btnInputProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 갤러리 호출
                // 아래의 코드는 스마트폰의 앨범에서 이미지를 가져오는 부분입니다.
                Intent intent = new Intent(Intent.ACTION_PICK);
                File tempFile = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
                Uri tempUri = Uri.fromFile(tempFile);
                intent.putExtra("crop", "true");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                intent.setType("image/*");
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });


        inputId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkID = false;
            }
        });
        btnCheckID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (inputId.length() == 0) {
                    //아이디를 입력하지 않고 중복체크 버튼을 눌렀을 경우
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
//                    //진행하는 다이얼로그 보여주기
//                    mProgressDialog.show();
                    Call<IdCheckResult> requestRegister = service.getIdCheck(inputId.getText().toString());
                    requestRegister.enqueue(new Callback<IdCheckResult>() {
                        @Override
                        public void onResponse(Call<IdCheckResult> call, Response<IdCheckResult> response) {
                            if (response.body().result.equals("SUCCESS")) {
                                Toast.makeText(getApplicationContext(), "사용가능한 아이디 입니다. ", Toast.LENGTH_SHORT).show();
                                checkID = true;
                            }
                            if (response.body().result.equals("FAIL")) {
                                Toast.makeText(getApplicationContext(), "이미 사용중인 아이디가 존재합니다. 다른 아이디로 시도 해주세요. ", Toast.LENGTH_SHORT).show();
                                checkID = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<IdCheckResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "아이디 중복체크 완전 실패", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });

                }
            }
        });


//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//
//            Intent intent = new Intent(JoinActivity.this , LoginActivity.class);
//            startActivity(intent);
//
//
//    });

        btnCompleteJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkID == false) {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요. ", Toast.LENGTH_SHORT).show();
                } else if (inputId.length() < 6 || inputId.length() > 14 || (inputId.getText().toString()).matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {

                    Toast.makeText(getApplicationContext(), "아이디는 영어 숫자만 사용할수 있으며, 6~14자 입니다. ", Toast.LENGTH_SHORT).show();
                } else if (!(inputPasswd.getText().toString()).equals(inputRePasswd.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 확인이 일치하지 않습니다. ", Toast.LENGTH_SHORT).show();
                } else if (inputName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름은 필수 입력 사항입니다. ", Toast.LENGTH_SHORT).show();
                } else if (inputPhone.length() == 0 || inputPhone.length() != 11) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호는 필수 입력 사항입니다. ", Toast.LENGTH_SHORT).show();
                } else {

                    // 먼저, 게시판의 제목과 내용을 body에 담아서 보내기위해 작업.
                    RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), inputId.getText().toString());
                    RequestBody pw = RequestBody.create(MediaType.parse("multipart/form-data"), inputPasswd.getText().toString());
                    RequestBody ph = RequestBody.create(MediaType.parse("multipart/form-data"), inputPhone.getText().toString());
                    RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), inputName.getText().toString());
                    RequestBody home;
                    RequestBody work;

                    if (inputHome.length() == 0) {
                        home = null;
                    } else {
                        home = RequestBody.create(MediaType.parse("multipart/form-data"), inputHome.getText().toString());
                    }
                    if (inputWork.length() == 0) {
                        work = null;
                    } else {
                        work = RequestBody.create(MediaType.parse("multipart/form-data"), inputWork.getText().toString());
                    }


                    MultipartBody.Part profile;

                    if (imgUrl == "") {
                        profile = null; //실질적으로 담을 부분(?)
                    } else { //사용자가 이미지를 선택했다면?

                        /**
                         * 비트맵 관련한 자료는 아래의 링크에서 참고
                         * http://mainia.tistory.com/468
                         */
                        /**
                         * 아래의 부분은 이미지 리사이징하는 부분입니다
                         * 왜?? 이미지를 리사이징해서 보낼까요?
                         * 안드로이드는 기본적으로 JVM Heap Memory가 얼마되지 않습니다.
                         * 구글에서는 최소 16MByte로 정하고 있으나, 제조사 별로 디바이스별로 Heap영역의 크기는 다르게 정하여 사용하고 있습니다.
                         * 또한, 이미지를 서버에 업로드할 때 이미지크기가 크면 그만큼 시간이 걸리고 데이터 소모가 되겠죠!
                         */
                        BitmapFactory.Options options = new BitmapFactory.Options(); //사용자가 보기에 불편하지 않을 정도로 resizing 해준다
//                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                        InputStream in = null; // here, you need to get your context.
                        try {
                            in = getContentResolver().openInputStream(data);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        //사용자가 보기에 불편하지 않을 정도로 resizing 해준다(효율을위해!) 카카오톡으로 사진보내면 깨지는 이유!
                        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),


                        RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                        File photo = new File(imgUrl); // 그저 블러온 파일의 이름을 알아내려고 사용.

                        // MultipartBody.Part is used to send also the actual file name
                        //이미지 이름을 서버로 보낼 때에에는 아무렇게나 보내줘도된다! 서버에서 자동변환된다 (보안의문제)
                        profile = MultipartBody.Part.createFormData("image", photo.getName(), photoBody);
                        //"필드명"

                    }


                    // 회원가입 요청
                    Call<JoinResult> requestJoin = service.requestJoin(id, pw, ph, name, home, work, profile);

                    requestJoin.enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {

                            if (response.body().result.equals("SUCCESS")) {
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                finish();
//                                HelpDialog help = new HelpDialog();
//                                help.show(getFragmentManager(), "help_dialog");

                            } else if (response.body().result.equals("FAIL")) {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "엘스문", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "회원가입 완전 실패", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }


        });
    }


    // 선택된 이미지 데이터 받아오기

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            //이미지를 성공적으로 가져왔을 경우
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    // String name_Str = getImageNameToUri(data.getData());
                    //  imgNameTextView.setText(name_Str);
                    this.data = data.getData();
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    ImageView image = (ImageView) findViewById(R.id.profile);

                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imgUrl = "";
                //  imgNameTextView.setText("");
            }
        }
    }

    // 선택된 이미지 파일명 가져오기 나중에 코드를 재활용 해서 사용하 면 된다
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;

        return imgName;
    }
}