package com.example.dell.mylife;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dell.util.Base64Utils;
import com.example.dell.util.SharedPreferencesUtils;

public class LoginActivity extends Activity
        implements View.OnClickListener {

    private EditText et_name;
    private EditText et_password;
    private Button mLoginBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        final VideoView videoView = (VideoView)findViewById(R.id.voidview);
        final String videopath = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bk_vedio).toString();
        videoView.setVideoPath(videopath);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.setVideoPath(videopath);
                videoView.start();
            }
        });
        initViews();
        setupEvents();
        initData();

    }
    private  void  initData(){
        //If the first time?
        if (firstLogin()){
            checkBox_password.setChecked(false);
            checkBox_login.setChecked(false);
        }
        //If rember password
        if(rememberPassword()){
            checkBox_password.setChecked(true);
            setTextNameAndPassword();
        }else{
            setTextName();
        }

        //If autoLogin
        if(autoLogin()){
            checkBox_login.setChecked(true);
            login();
        }
    }

    public void setTextNameAndPassword(){
        et_name.setText(""+getLocalName());
        et_password.setText(""+getLocalPassword());
    }

    public void setTextName(){
        et_name.setText(""+getLocalName());
    }

    public String getLocalName(){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this,"setting");
        String name = helper.getString("name");
        return name;
    }
    public String getLocalPassword(){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return password;
    }

    private boolean autoLogin(){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("autoLogin", false);
        return autoLogin;
    }

    private boolean rememberPassword(){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean rememberPassword = helper.getBoolean("rememberPassword", false);
        return  rememberPassword;
    }

    private void initViews(){
        mLoginBtn = (Button)findViewById(R.id.button_login);
        et_name = (EditText)findViewById(R.id.et_account);
        et_password = (EditText)findViewById(R.id.et_password);
        checkBox_login = (CheckBox)findViewById(R.id.checkBox_autologin);
        checkBox_password = (CheckBox)findViewById(R.id.checkBox_rember);
    }

    private void setupEvents(){
        mLoginBtn.setOnClickListener(this);
//        checkBox_password.setOnCheckedChangeListener(this);
//        checkBox_login.setOnCheckedChangeListener(this);
    }

    private boolean firstLogin(){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean first = helper.getBoolean("first", true);
        if(first){
            helper.putValues(new SharedPreferencesUtils.ContentVaule("first", false),
                             new SharedPreferencesUtils.ContentVaule("rememberPassword", false),
                             new SharedPreferencesUtils.ContentVaule("autoLogin", false),
                             new SharedPreferencesUtils.ContentVaule("name", ""),
                             new SharedPreferencesUtils.ContentVaule("password", ""));
            return  true;
        }
        return  false;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_login:
                loadUserName();
                login();
                break;
//            case R.id.iv_see_passwprd:
//                setPasswordVisibility();
//                break;
        }
    }

    private void login(){
        if(getAccount().isEmpty()){
            showToast("null");
            return;
        }

        if (getPassword().isEmpty()){
            showToast("null");
            return;
        }

        showLoading();
        Thread loginRunnable = new Thread(){
            @Override
            public void run(){
                super.run();
                setLoginBtnClickable(false);

                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(getAccount().equals("dzzyc") && getPassword().equals("zyc218")){
                    showToast("success");
                    loadCheckBoxState(checkBox_password, checkBox_login);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    //showToast("false");
                    //test
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                setLoginBtnClickable(true);
//                hideLoading();
            }
        };
        loginRunnable.start();
    }

    public void loadUserName(){
        if(!getAccount().equals("")|| !getAccount().equals("input")){
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this,"setting");
            helper.putValues(new SharedPreferencesUtils.ContentVaule("name", getAccount()));
        }
    }

    public String getAccount(){
        return et_name.getText().toString().trim();
    }

    public String getPassword(){
        return et_password.getText().toString().trim();
    }

    public void loadCheckBoxState(){
        loadCheckBoxState(checkBox_password, checkBox_login);
    }

    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login){
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        if(checkBox_login.isChecked()){
            helper.putValues(
                    new SharedPreferencesUtils.ContentVaule("rememberPassword", true),
                    new SharedPreferencesUtils.ContentVaule("autoLogin", true),
                    new SharedPreferencesUtils.ContentVaule("password", Base64Utils.encryptBASE64(getPassword()))
            );
        }else if(!checkBox_password.isChecked()){
            helper.putValues(
                    new SharedPreferencesUtils.ContentVaule("rememberPassword", false),
                    new SharedPreferencesUtils.ContentVaule("autoLogin", false),
                    new SharedPreferencesUtils.ContentVaule("password", "")
            );
        }else if(checkBox_password.isChecked()){
            helper.putValues(
                    new SharedPreferencesUtils.ContentVaule("rememberPassword", true),
                    new SharedPreferencesUtils.ContentVaule("autoLogin", false),
                    new SharedPreferencesUtils.ContentVaule("password", Base64Utils.encryptBASE64(getPassword()))
            );
        }
    }

    public void setLoginBtnClickable(boolean clickable){
        mLoginBtn.setClickable(clickable);
    }

    /**
     * @param
     */
    public void showLoading(){
//        if(mLoadingDialog == null){
//            mLoadingDialog = new LoadingDialog(this,getString(R.string.loading), false);
//        }
//        mLoadingDialog.show();
    }

    public void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
