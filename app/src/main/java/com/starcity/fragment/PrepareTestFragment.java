package com.starcity.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.starcity.activity.ConfirmTestActivity;
import com.starcity.adapter.PrepareTestListAdapter;
import com.starcity.R;
import com.starcity.api.ApiStores;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.entity.ForeExamEmployee;
import com.starcity.entity.RResult;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 待考试
 */
public class PrepareTestFragment extends BaseFragment implements Callback<RResult<List<ForeExamEmployee>>> {

    private ListView list;
    PrepareTestListAdapter adapter;
    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();
    Call<RResult<List<ForeExamEmployee>>> prepareTest ;
    private List<ForeExamEmployee> foreExamEmployeeList;

    @Override
    protected void initData() {
        prepareTest = retrofitService.unexam();
        prepareTest.enqueue(this);
    }

    @Override
    protected void initID() {
        list = (ListView) view.findViewById(R.id.prepareTestList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ForeExamEmployee foreExamEmployee = foreExamEmployeeList.get(position);
                Intent intent=new Intent(getActivity(), ConfirmTestActivity.class);
                intent.putExtra("foreExamEmployee", foreExamEmployee);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public int setContentView() {
        return R.layout.fragment_prepare_test;
    }

    @Override
    public void onResponse(Call<RResult<List<ForeExamEmployee>>> call, Response<RResult<List<ForeExamEmployee>>> response) {
        RResult<List<ForeExamEmployee>> body = response.body();
        if (body == null || body.getCode() != 200) {
            Toast.makeText(getActivity(), body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        foreExamEmployeeList = body.getResult();
        if(foreExamEmployeeList !=null){
            List<ForeExamEmployee> foreExamEmployeeList = body.getResult();
            adapter = new PrepareTestListAdapter(getActivity(),foreExamEmployeeList);
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<RResult<List<ForeExamEmployee>>> call, Throwable t) {
        System.out.println(t.getMessage());
        Toast.makeText(getActivity(), "接口调用异常！", Toast.LENGTH_SHORT).show();
    }
}
