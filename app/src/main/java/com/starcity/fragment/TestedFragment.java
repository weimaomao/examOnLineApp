package com.starcity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.starcity.adapter.TestedListAdapter;
import com.starcity.R;
import com.starcity.api.ApiStores;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.entity.ForeExamEmployee;
import com.starcity.entity.ForeExamHistory;
import com.starcity.entity.RResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 考试历史
 */
public class TestedFragment extends BaseFragment implements Callback<RResult<List<ForeExamHistory>>> {

    private ListView list;
    TestedListAdapter adapter;
    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();
    Call<RResult<List<ForeExamHistory>>> prepareTest ;


    @Override
    protected void initData() {
        prepareTest = retrofitService.history();
        prepareTest.enqueue(this);
    }

    @Override
    protected void initID() {
        list = (ListView) view.findViewById(R.id.testedList);
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                return;//生产版
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_tested;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResponse(Call<RResult<List<ForeExamHistory>>> call, Response<RResult<List<ForeExamHistory>>> response) {
        RResult<List<ForeExamHistory>> body = response.body();
        if (body == null || body.getCode() != 200) {
            Toast.makeText(getActivity(), body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        List<ForeExamHistory> result = body.getResult();
        if(result!=null){
            adapter = new TestedListAdapter(getActivity(), body.getResult());
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<RResult<List<ForeExamHistory>>> call, Throwable t) {
        System.out.println(t.getMessage());
        Toast.makeText(getActivity(), "接口调用异常！", Toast.LENGTH_SHORT).show();
    }
}
