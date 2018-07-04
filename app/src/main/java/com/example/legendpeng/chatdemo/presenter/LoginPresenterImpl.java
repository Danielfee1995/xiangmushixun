package com.example.legendpeng.chatdemo.presenter;

import android.util.Log;

import com.example.legendpeng.chatdemo.model.myokhttp.MyOkHttp;
import com.example.legendpeng.chatdemo.model.myokhttp.response.JsonResponseHandler;
import com.example.legendpeng.chatdemo.ui.iView.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenterImpl implements ILoginPresenter {

    ILoginView iLoginView;

    public LoginPresenterImpl(ILoginView iLoginView){
        this.iLoginView = iLoginView;

    }

    public void login(String phone, String pwd){
        Log.d("phone", phone);
        Log.d("pwd", pwd);

        MyOkHttp myOkHttp = new MyOkHttp();
        String url = "http://119.29.105.37:8000/login";
        Map<String, String> params = new HashMap<>();
//        params.put("phone", "12345678901");
//        params.put("pwd", "123456");
        params.put("phone", phone);
        params.put("pwd", pwd);

        myOkHttp.post().url(url)
                .params(params)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        //Log.d("Get成功：", "doPost onSuccess:" + response);
                        try{
                            String res = response.getString("result");
                            Log.d("res=", res);
                            if(res.equals("ture")){
                                iLoginView.loginSuccess();
                            }else{
                                String description = response.getString("description");
                                iLoginView.showMessage(description);
                            }

                        }catch (JSONException e){
                            Log.d("JSONException：", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        //Log.d("Get失败：", Integer.toString(statusCode));
                        iLoginView.showMessage(error_msg);
                    }
                });
    }

}
