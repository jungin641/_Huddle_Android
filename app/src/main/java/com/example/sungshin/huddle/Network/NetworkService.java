package com.example.sungshin.huddle.Network;

import com.example.sungshin.huddle.DetailPage.DetailResult;
import com.example.sungshin.huddle.DetailPage.RoomImgResult;
import com.example.sungshin.huddle.DetailPage.roomDeleteResult;
import com.example.sungshin.huddle.EditActivity.EditMyOpinionResult;
import com.example.sungshin.huddle.EditActivity.voteMyOpinionDataList;
import com.example.sungshin.huddle.EditActivity.voteMyOpinionResult;
import com.example.sungshin.huddle.LoginJoin.IdCheckResult;
import com.example.sungshin.huddle.LoginJoin.JoinResult;
import com.example.sungshin.huddle.LoginJoin.LoginListData;
import com.example.sungshin.huddle.LoginJoin.LoginResult;
import com.example.sungshin.huddle.MainList.MainListResult;
import com.example.sungshin.huddle.MainList.MainRequestData;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.FriendListRequest;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.FriendListResult;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.RealFriendResult;
import com.example.sungshin.huddle.MakingAppointment.roomCreateDataList;
import com.example.sungshin.huddle.MakingAppointment.roomCreateResult;
import com.example.sungshin.huddle.Mypage.MyPageEditResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by LG on 2016-12-29.
 */

public interface NetworkService {

    // 아이디 중복체크
    @GET("/Join/{id}")
    Call<IdCheckResult> getIdCheck(@Path("id") String id);

    // 로그인
    @POST("/login")
    Call<LoginResult> requestLogin(@Body LoginListData data);

    //회원가입
    @POST("/Join")
    @Multipart
    Call<JoinResult> requestJoin(@Part("id") RequestBody id,
                                 @Part("pw") RequestBody pw,
                                 @Part("ph") RequestBody ph,
                                 @Part("name") RequestBody name,
                                 @Part("home") RequestBody home,
                                 @Part("work") RequestBody work,
                                 @Part MultipartBody.Part profile
    );

    //메인리스트에 내약속 가져오기
    @POST("/main")
    Call<MainListResult> requestMainList(@Body MainRequestData data);

    //메인리스트에 내약속 가져오기
    @GET("/room/detail/{my_id}/{meeting_id}")
    Call<DetailResult> requestDetailList(@Path("my_id") String my_id, @Path("meeting_id") String meeting_id);

    //내정보수정
    //마이페이지 정보수정
    @PUT("/main/edit")
    @Multipart
    Call<MyPageEditResult> requestMyPageEdit(@Part("id") RequestBody id,
                                             @Part("name") RequestBody name,
                                             @Part("ph") RequestBody ph,
                                             @Part("home") RequestBody home,
                                             @Part("work") RequestBody work,
                                             @Part MultipartBody.Part profile);

    //5번 통신 방만들기 확인창에서 완료!
    @POST("room/create")
    Call<roomCreateResult> requestRoomCreate(@Body roomCreateDataList data);

    //11번 통신 내방_상세보기에서 입력 버튼 눌렀을 때 기존에 입력했던 정보 제공 API
    @GET("room/vote_my_opinion/{my_meeting_id}")
    Call<EditMyOpinionResult> requestEditMyOpinion(@Path("my_meeting_id") String my_meeting_id);

    //전화번호부 동기화하기 -영호
    @POST("/main/sync")
    Call<FriendListResult> requestFriendList(@Body FriendListRequest data);

    //회원목록 받아오기 -영호
    @GET("/room/create/{my_id}")
    Call<RealFriendResult> requestReallist(@Path("my_id") String my_id);

    // 9번 통신:
    @PUT("/room/profile_edit")
    @Multipart
    Call<RoomImgResult> requesRoomImg (@Part("my_meeting_id") RequestBody my_meeting_id ,
                                       @Part MultipartBody.Part room_profile);

    //10번 통신
    @DELETE("/room/exit/{my_meeting_id}")
    Call<roomDeleteResult> requestDeleteRoom(@Path("my_meeting_id") int my_meeting_id);
    //12번 통신
    @PUT("/room/vote_my_opinion")
    Call<voteMyOpinionResult> requestVoteOpinion(@Body voteMyOpinionDataList data);



}
