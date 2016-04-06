package com.brilliantreform.sc.utils;

import com.brilliantreform.sc.phoneOrder.po.Button;
import com.brilliantreform.sc.phoneOrder.po.ClickButton;
import com.brilliantreform.sc.phoneOrder.po.Menu;
import com.brilliantreform.sc.phoneOrder.po.ViewButton;
import com.brilliantreform.sc.weixin.po.AccessToken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Lm on 2015/12/21.
 */
public class WeiXinUtil {
    private static final String APPID = "wx2d1ceefba62d640d";
    private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
    private static final String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String CREATE_MENU_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private static final String SHOUQUAN_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    private static final String CODE_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /**
     * GET ��ȡ
     *
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * POST ����
     *
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "utf-8"));
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String temp = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.fromObject(temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * ͨ��code��ȡ��ҳ��Ȩaccess_token
     *
     * @return Code_Access_Token
     */
    /*public static Code_Access_Token getCode_Access_Token(String code) {
        Code_Access_Token code_access_token = new Code_Access_Token();
        String url = CODE_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
        JSONObject jsonObject = doGetStr(url);
        if (null != jsonObject) {
            code_access_token.setOpenid(jsonObject.getString("openid"));
        }
        return code_access_token;
    }*/

    /**
     * ��ȡAccess_Token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken accessToken = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (null != jsonObject) {
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }

    /**
     * �����˵�
     *
     * @return
     */
    public static Menu initMenu() {
        Menu menu = new Menu();
        ClickButton clickButton = new ClickButton();
        clickButton.setName("click_menu");
        clickButton.setType("click");
        clickButton.setKey("11");

        ViewButton viewButton = new ViewButton();
        viewButton.setName("工具");
        viewButton.setType("view");
        String url = null;
        try {
            String tempUrl = URLEncoder.encode("http://115.159.93.15/qx_mgr/qxxCtrl.do?method=loginNewWeb", "utf-8");
            url = SHOUQUAN_URL.replace("APPID", APPID).replace("REDIRECT_URI", tempUrl).replace("SCOPE", "snsapi_base");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewButton.setUrl(url);

        ClickButton clickButton2 = new ClickButton();
        clickButton2.setName("scancode_push");
        clickButton2.setType("scancode_push");
        clickButton2.setKey("12");

        ClickButton clickButton3 = new ClickButton();
        clickButton3.setName("location_select");
        clickButton3.setType("location_select");
        clickButton3.setKey("13");

        Button button = new Button();
        button.setName("menu");
        button.setSub_button(new Button[]{clickButton2, clickButton3});

        menu.setButton(new Button[]{clickButton, viewButton, button});
        return menu;
    }

    public static int createMenu(String token, String menu) {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, menu);
        if (null != jsonObject) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * ����
     */
    public static void main(String[] args) {
        AccessToken accessToken = WeiXinUtil.getAccessToken();
        System.out.println(accessToken.getToken());
        System.out.println(accessToken.getExpiresIn());
        String menu = JSONObject.fromObject(initMenu()).toString();
        int num = createMenu(accessToken.getToken(), menu);
        if (num == 0)
            System.out.println("success");
        System.out.print(num);

    }
}
