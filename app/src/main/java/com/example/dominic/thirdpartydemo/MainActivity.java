package com.example.dominic.thirdpartydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Tencent mTencent;
    private BaseUiListener mBaseUiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1105945265", this.getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        // 初始化视图
    }

    /*--------------- 登陆 ---------------*/
    public void click(View view) {     
        mBaseUiListener = new BaseUiListener();
        
        if (!mTencent.isSessionValid()) {
            /*--------------- //发起授权请求 ---------------*/
            mTencent.login(this, "get_user_info", mBaseUiListener);//第二个参数是需要获得的权限
        }
    }
    /*--------------- QQ注销接口 ---------------*/
    public void cancle(View view){
        mTencent.logout(this);
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            //获取授权信息
            JSONObject jsonObject = (JSONObject) o;
            //获取授权信息
            String s = jsonObject.toString(); //
            Toast.makeText(getApplicationContext(), "授权成功" + s.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mBaseUiListener);
    }


}
