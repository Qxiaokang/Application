package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.QQInfo;
import com.example.aozun.testapplication.bean.QQUserInfo;
import com.example.aozun.testapplication.db.TestOpenHelp;
import com.example.aozun.testapplication.utils.ComUtil;
import com.example.aozun.testapplication.utils.InitContent;
import com.example.aozun.testapplication.utils.LogUtils;
import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebActivity extends BaseActivity implements View.OnClickListener{
    private WebView webView;
    private WebSettings webSettings;
    private ProgressBar progressBar = null;
    private ImageView backImg = null, shareImg = null, QQLoginImg = null, WechartLoginImg = null, WeiboLoginImg = null;
    private TextView tv_title = null;
    private Tencent mTencent = null;
    private BaseUiListener baseUiListener;
    private ApiListener apiListener;
    private static final String GRAPH_SIMPLE_USER_INFO = "get_user_info";
    private QQInfo qqInfo = null;
    private UserInfo userInfo = null;
    private static final String APP_ID = "1106166855";
    private boolean isnull=false;
    private static final String WX_APPID="";
    private IWXAPI iwxapi=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView(){
        QQLoginImg = (ImageView) findViewById(R.id.iv_login_qq);
        WechartLoginImg = (ImageView) findViewById(R.id.iv_login_wechat);
        WeiboLoginImg = (ImageView) findViewById(R.id.iv_login_weibo);
        shareImg = (ImageView) findViewById(R.id.iv_share);
        tv_title = (TextView) findViewById(R.id.tv_web_title);
        backImg = (ImageView) findViewById(R.id.iv_back);
        progressBar = (ProgressBar) findViewById(R.id.pb_web);
        progressBar.setMax(100);
        webView = (WebView) findViewById(R.id.wv_id);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//allow  js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//
        webView.addJavascriptInterface(WebActivity.this, "android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new CustomViewClient());
        webView.loadUrl("file:///android_asset/html/TestHtml.html");
        backImg.setOnClickListener(this);
        shareImg.setOnClickListener(this);
        QQLoginImg.setOnClickListener(this);
        WechartLoginImg.setOnClickListener(this);
        WeiboLoginImg.setOnClickListener(this);
        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
        baseUiListener = new BaseUiListener();
        apiListener = new ApiListener();
        iwxapi= WXAPIFactory.createWXAPI(this,WX_APPID,true);
        iwxapi.registerApp(WX_APPID);
    }

    WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onReceivedTitle(WebView view, String title){
            LogUtils.i("---web---title:" + title);
            if(tv_title != null){
                tv_title.setText(view.getTitle());
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress){
            LogUtils.i("web   progress:" + newProgress);
            if(progressBar != null){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onHideCustomView(){
            super.onHideCustomView();
        }
    };

    class CustomViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url){
            LogUtils.i("---pageFinished");
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url){
            super.onPageCommitVisible(view, url);
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShare();
                break;
            case R.id.iv_login_qq:
                mTencent.login(this, "all", baseUiListener);
                break;
            case R.id.iv_login_wechat:
                break;
            case R.id.iv_login_weibo:
                break;
            default:
                break;
        }
    }
    private void showShare(){
        OnekeyShare oks=new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
       // oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
       // oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("Text Share---");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
       // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
       // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
       // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
       // oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Constants.REQUEST_LOGIN){
            mTencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object o){
            LogUtils.e("tencent--object:" + o.toString() + "  accredit  success");
            qqInfo = new Gson().fromJson(o.toString(), QQInfo.class);
            mTencent.setOpenId(qqInfo.getOpenid());
            mTencent.setAccessToken(qqInfo.getAccess_token(), qqInfo.getExpires_in() + "");
            QQToken qqToken = mTencent.getQQToken();
            userInfo = new UserInfo(getApplicationContext(), qqToken);
            userInfo.getUserInfo(new IUiListener(){
                @Override
                public void onComplete(Object o){
                    LogUtils.d("qq---login---success" + "\n" + o.toString());
                    QQUserInfo uer=new Gson().fromJson(o.toString(),QQUserInfo.class);
                    Intent intent=new Intent(WebActivity.this,PersonageActivity.class);
                    intent.putExtra("nickname",uer.getNickname());
                    intent.putExtra("picurl",uer.getFigureurl_1());
                    intent.putExtra("city",uer.getCity());
                    intent.putExtra("province",uer.getProvince());
                    intent.putExtra("sex",uer.getGender());
                    WebActivity.this.startActivity(intent);
                }

                @Override
                public void onError(UiError uiError){
                    LogUtils.e("qq---login---faild");
                }

                @Override
                public void onCancel(){
                    LogUtils.e("qq---login---cancle");
                }
            });
        }

        @Override
        public void onError(UiError uiError){
            LogUtils.e("qq----accredit----faild");
        }

        @Override
        public void onCancel(){
            LogUtils.d("11---accredit----cancel");
        }
    }

    class ApiListener implements IRequestListener{
        @Override
        public void onComplete(JSONObject jsonObject){
            LogUtils.i("  login info:" + jsonObject.toString());
        }

        @Override
        public void onIOException(IOException e){
        }

        @Override
        public void onMalformedURLException(MalformedURLException e){
        }

        @Override
        public void onJSONException(JSONException e){
        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e){
        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e){
        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e){
        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e){
        }

        @Override
        public void onUnknowException(Exception e){
        }
    }

    @JavascriptInterface
    public void LoginFunction(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                webView.loadUrl("javascript:formSubmit()");
            }
        });
    }

    private void startNextPage(){
        Intent intent = new Intent(WebActivity.this, PersonageActivity.class);
        WebActivity.this.startActivity(intent);
    }

    @JavascriptInterface
    public void getResult(final String result){
        LogUtils.e("result:" + result + result.split("-and-").toString());
        final boolean b=checkUser(result);
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                LogUtils.d("---checkOk:"+b+"  name or pwd isnull:"+isnull);
                if(b){
                    ComUtil.getInstance(WebActivity.this).showToast("Login  Success");
                    InitContent.getInstance().setUserId(result.split("-and-")[0]);
                    startNextPage();
                }else {
                    if(isnull){
                        ComUtil.getInstance(WebActivity.this).showToast("UserName or PassWord is null");
                    }
                    else{
                        ComUtil.getInstance(WebActivity.this).showToast("UserName or PassWord error");
                    }
                }
            }
        });
    }
    // check user name and password
    private boolean checkUser(String str){
        boolean b = false;
        Map<String, String> user_map = new HashMap<>();
        List<Map<String, Object>> mapList = TestOpenHelp.getInstance(WebActivity.this).query("select * from t_user ");
        if(mapList != null && mapList.size() > 0){
            for(int i = 0; i < mapList.size(); i++){
                user_map.put(mapList.get(i).get("user_name").toString(), mapList.get(i).get("user_pwd").toString());
            }
        }else{
            return b;
        }
        if(str != null && !"".equals(str)){
            String[] split = str.split("-and-");
            if(split.length < 2){
                b = false;
                isnull=true;
            }else{
                isnull=false;
                String name=split[0];String pwd=split[1];
                LogUtils.e("name: "+name+"pwd  : "+pwd);
                if(name!=null&&!"".equals(name)&&pwd!=null&&!"".equals(pwd)){
                    LogUtils.e("usermap："+user_map.get(name));
                    if(pwd.equals(user_map.get(name))){
                        b = true;
                    }else {
                        b=false;
                    }
                }else{
                    isnull= true;
                    b = false;
                }
            }
        }else{
            b = false;
        }
        return b;
    }
    private void loginWechat(){
        SendAuth.Req req= new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
    }
}
