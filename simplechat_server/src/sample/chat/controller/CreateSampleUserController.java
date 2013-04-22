package sample.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import sample.chat.model.User;
import sample.chat.service.UserService;

public class CreateSampleUserController extends Controller {

    private static int NUM = 3;

    @Override
    public Navigation run() throws Exception {
        for (int i = 0; i < NUM; i++) {
            String email = i + "@sample.com";
            List<User> list = UserService.getUserByEmail(email);
            if (list == null || list.size() == 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("email", email);
                map.put("firstName", "sample" + i);
                map.put("lastName", "user");
                UserService.createUser(map);
            }
        }
        return forward("CreateSampleUser.jsp");
    }
}
