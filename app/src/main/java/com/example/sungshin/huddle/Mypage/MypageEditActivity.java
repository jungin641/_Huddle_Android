package com.example.sungshin.huddle.Mypage;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.MainActivity;
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

public class MypageEditActivity extends AppCompatActivity {

    TextView txtmypageId , mypageImageTextView;
    Button btnmypageDialogprofileedit, btneditmypageecomplete;
    EditText mypagemodifyname, mypagemodifyphonnum, mypagemodifydong, mypagemodifyschool;
    ImageView mypageImage ,mypageprofile1, mypageprofile2, mypageprofile3, mypageprofile4;

    String imgUrl = "";
    Uri data;
    NetworkService service;

    final int REQ_CODE_SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_edit);

        //mypageImageTextView = (TextView) findViewById(R.id.mypageImageTextView);
        mypageImage = (ImageView) findViewById(R.id.mypageImage);

        mypagemodifyname = (EditText) findViewById(R.id.mypagemodifyname);
        mypagemodifyphonnum = (EditText) findViewById(R.id.mypagemodifyphonnum);
        mypagemodifydong = (EditText) findViewById(R.id.mypagemodifydong);
        mypagemodifyschool = (EditText) findViewById(R.id.mypagemodifyschool);
        btneditmypageecomplete = (Button) findViewById(R.id.btneditmypageecomplete);
        btnmypageDialogprofileedit = (Button) findViewById(R.id.btnmypageDialogprofileedit);
        mypageprofile1 = (ImageView) findViewById(R.id.mypageprofile1);
        mypageprofile2 = (ImageView) findViewById(R.id.mypageprofile2);
        mypageprofile3 = (ImageView) findViewById(R.id.mypageprofile3);
        mypageprofile4 = (ImageView) findViewById(R.id.mypageprofile4);

        service = ApplicationController.getInstance().getNetworkService();

        Intent intent = getIntent();
       //  String mpeditId = intent.getExtras().getString("editId");
       final String mpfixId = intent.getExtras().getString("user_id");
        String mpeditName = intent.getExtras().getString("editName");
        String mpeditPnum = intent.getExtras().getString("editPnum");
        String mpeditDong = intent.getExtras().getString("editHome");
        String mpmodifySchool =  intent.getExtras().getString("editWork");

       // txtmypageId.setText(mpfixId);
        // mypagemodifyname.setText(mpeditId);
        mypagemodifyname.setText(mpeditName);
        mypagemodifyphonnum.setText(mpeditPnum);
        mypagemodifydong.setText(mpeditDong);
        mypagemodifyschool.setText(mpmodifySchool);

        btnmypageDialogprofileedit.setOnClickListener(new View.OnClickListener() {
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

        btneditmypageecomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), mpfixId);
                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), mypagemodifyname.getText().toString());
                RequestBody ph = RequestBody.create(MediaType.parse("multipart/form-data"), mypagemodifyphonnum.getText().toString());
                RequestBody home = RequestBody.create(MediaType.parse("multipart/form-data"), mypagemodifydong.getText().toString());
                RequestBody work = RequestBody.create(MediaType.parse("multipart/form-data"), mypagemodifyschool.getText().toString());

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
                    profile = MultipartBody.Part.createFormData("image", photo.getName().toString(), photoBody);
                    //"필드명"

                }

                // 정보수정요청
                Call<MyPageEditResult> requestMyPageEdit = service.requestMyPageEdit(id ,name, ph, home, work, profile);

                requestMyPageEdit.enqueue(new Callback<MyPageEditResult>() {
                    @Override
                    public void onResponse(Call<MyPageEditResult> call, Response<MyPageEditResult> response) {

                        if (response.body().result.equals("SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "내정보수정완료", Toast.LENGTH_SHORT).show();
                            finish();

                        } else if (response.body().result.equals("FAIL")) {
                            Toast.makeText(getApplicationContext(), "내정보수정실패", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "엘스문", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPageEditResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "내정보수정 완전 실패", Toast.LENGTH_SHORT).show();

                    }
                });



                Intent intent = new Intent(MypageEditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 선택된 이미지 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            //이미지를 성공적으로 가져왔을 경우
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    //mypageImageTextView.setText(name_Str);
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
                mypageImageTextView.setText("");
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