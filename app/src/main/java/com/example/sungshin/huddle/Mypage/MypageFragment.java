package com.example.sungshin.huddle.Mypage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.LoginJoin.InfoDataList;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.FriendListRequest;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.FriendListResult;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.friends_list;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungshin on 2016-12-27.
 */
public class MypageFragment extends Fragment {
    Button btnmypageEdit;
    String modify;

    TextView txtmypageId;
    TextView txtmypageName;
    TextView txtmypagePnum;
    TextView txtmypagedong;
    TextView txtmypageSchool;
    ImageView mypageImage;

    //영호 변수부분
    Button btnSync;
    ArrayList<friends_list> mDatas;
    Cursor contactCursor;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ProgressDialog mProgressDialog;
    NetworkService service;

    InfoDataList userInfo;

    public MypageFragment() {
        this.userInfo = ApplicationController.getInstance().myInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mypage, container, false);

        btnmypageEdit = (Button) view.findViewById(R.id.btnmypageEdit);
        btnSync = (Button) view.findViewById(R.id.btnSynchronize);


        txtmypageName = (TextView) view.findViewById(R.id.txtmypageName);
        txtmypagePnum = (TextView) view.findViewById(R.id.txtmypagePnum);
        txtmypagedong = (TextView) view.findViewById(R.id.txtmypagedong);
        txtmypageSchool = (TextView) view.findViewById(R.id.txtmypageSchool);
        mypageImage = (ImageView) view.findViewById(R.id.mypageImage);

        //txtmypageId.setText(userInfo.id);
        txtmypageName.setText(userInfo.name);
        txtmypagePnum.setText(userInfo.ph);
        txtmypagedong.setText(userInfo.home);
        txtmypageSchool.setText(userInfo.work);

        //동기화 다이얼로그 - 영호
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("동기화중....");
        mProgressDialog.setIndeterminate(true);
        service = ApplicationController.getInstance().getNetworkService();


        //마이페이지에서 이미지 가져오기
        if (userInfo.profile != "") {
            Glide.with(getActivity())
                    .load(userInfo.profile)
                    .error(R.drawable.minihuman)
                    .into(mypageImage);
        }

        //전화번호부 동기화 버튼 - 영호
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();//주소록 불러오기
                if(mDatas.size() == 0){
                    Toast.makeText(getContext(), "동기화 할게 없습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    //동기화중이라는 다이얼로그 띄운것
                    mProgressDialog.show();
                    //서버로 전송 하는 부분
                    Call<FriendListResult> requestFriend = service.requestFriendList(new FriendListRequest(userInfo.id, mDatas));
                    // System.out.println(object.toString());
                    requestFriend.enqueue(new Callback<FriendListResult>() {
                        @Override
                        public void onResponse(Call<FriendListResult> call, Response<FriendListResult> response) {
                            if(response.isSuccessful()){
                                if(response.body().result.equals("SUCCESS")){
                                    Log.i("myTag", response.body().result);
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getContext(), "동기화 성공", Toast.LENGTH_SHORT).show();

                                }else if(response.body().result.equals("FAIL")){
                                    Log.i("myTag", response.body().result);
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getContext(), "동기화 실패", Toast.LENGTH_SHORT).show();
                                }else {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getContext(), "예상치 못한 오류 발생", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<FriendListResult> call, Throwable t) {
                            Log.i("myTag",t.toString());
//                            Log.i("myTag",t.getMessage());

                            Toast.makeText(getContext(), "동기화 완전 실패", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });


                }
            }
        });

        btnmypageEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , MypageEditActivity.class);
                intent.putExtra("user_id" , userInfo.id);
                intent.putExtra("editName" , txtmypageName.getText().toString());
                intent.putExtra("editPnum" , txtmypagePnum.getText().toString());
                intent.putExtra("editHome" , txtmypagedong.getText().toString());
                intent.putExtra("editWork" , txtmypageSchool.getText().toString());
                startActivity(intent);
                     }
                 });

        return view;

    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };

        String[] selectionArgs = null;

        //정렬
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        //조회해서 가져온다
        contactCursor = getActivity().managedQuery(uri, projection, null, selectionArgs, sortOrder);

        //정보를 담을 array 설정

        mDatas = new ArrayList<friends_list>();
        if (contactCursor.moveToFirst()) {
            do {
                mDatas.add(new friends_list(contactCursor.getString(0), contactCursor.getString(1)));
//                Log.i("myTag", "전화번호 === " + mDatas.get(0).ph);
//                Log.i("myTag", "전화번호 === " + mDatas.get(3).ph);
//                Log.i("myTag", "전화번호 === " + mDatas.get(5).ph);
            } while (contactCursor.moveToNext());
        }

    }

}

