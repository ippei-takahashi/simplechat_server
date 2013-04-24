package sample.chat.controller;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import sample.chat.model.User;
import sample.chat.service.UserService;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LoginController extends Controller {
    private static String SUCCESS = "success";
    private static String FAIL = "fail";

    @Override
    public Navigation run() throws Exception {
        String email = asString("email");
        String password = asString("password");

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (email == null || password == null) {
                map.put("result",FAIL);
            } else {
                User user =
                    UserService.getUserByEmailAndPassword(email, password);
                if (user == null) {
                    map.put("result",FAIL);
                } else {
                    map.put("result",SUCCESS);
                    map.put("userId",user.getUserId());
                    map.put("userName", user.getUserName());
                }
            }
        } catch (Exception e) {
            map.put("result", FAIL);
        }
        JSONObject json = new JSONObject(map);
        response.setHeader("cache-control", "no-cache");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json.toString());
        return null;
    }
}
