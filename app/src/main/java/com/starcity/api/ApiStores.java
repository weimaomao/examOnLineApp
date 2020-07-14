package com.starcity.api;


import com.starcity.entity.ForeExamEmployee;
import com.starcity.entity.ForeExamHistory;
import com.starcity.entity.ImageCode;
import com.starcity.entity.LoginUserDTO;
import com.starcity.entity.LoginUserForm;
import com.starcity.entity.QbPaper;
import com.starcity.entity.RResult;
import com.starcity.entity.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiStores {

    //baseUrl
    String API_SERVER_URL = "http://192.168.1.210:8000/api/";
    /**
     * @value DEFAULT_TIMEOUT 超时时间
     */
    long DEFAULT_TIMEOUT = 100;

    /**
     * 验证码
     */
    @GET("auth/code")
    Call<RResult<ImageCode>> getImageCode();

    /**
     * 退出
     */
    @DELETE("auth/logout")
    Call<RResult> logout();

    /**
     * 登录
     */
    @POST("auth/login")
    Call<RResult<LoginUserDTO>> login(@Body LoginUserForm login);

    /**
     * 待考试
     */
    @GET("examInfos/unexam")
    Call<RResult<List<ForeExamEmployee>>> unexam();

    /**
     * 待考试
     */
    @GET("examInfos/examHistory")
    Call<RResult<List<ForeExamHistory>>> history();

    /**
     * 查询考试剩余时间接口和状态
     */
    @GET("examInfos/papers/{paperId}/uptime")
    Call<RResult<Integer>> getUptimePaper(@Path("paperId") String paperId);

    /**
     * 获取试卷
     */
    @GET("examInfos/papers/{paperId}")
    Call<RResult<QbPaper>> getPapers(@Path("paperId") String paperId);



    /**
     * 提交试卷
     */
    @PUT("examInfos/papers/{paperId}")
    Call<RResult> submitPapers(@Path("paperId") String paperId,@Body QbPaper qbPaper);

}
