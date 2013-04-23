package sample.chat.api;

import java.util.logging.Logger;

import javax.inject.Named;

import sample.chat.api.dto.LoginResultV1Dto;
import sample.chat.model.User;
import sample.chat.service.UserService;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "loginEndpoint", version = "v1")
public class LoginV1Endpoint {
    private final static Logger logger = Logger.getLogger(LoginV1Endpoint.class
        .getName());

    private static String SUCCESS = "success";
    private static String FAIL = "fail";

    public LoginResultV1Dto login(@Named("email") String email,
            @Named("password") String password) {

        LoginResultV1Dto result = new LoginResultV1Dto();
        try {
            if (email == null || password == null) {
                result.setResult(FAIL);
            } else {
                User user =
                    UserService.getUserByEmailAndPassword(email, password);
                if (user == null) {
                    result.setResult(FAIL);
                } else {
                    result.setResult(SUCCESS);
                    result.setUserId(user.getUserId());
                    result.setUserName(user.getUserName());
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.setResult(FAIL);
        }

        return result;
    }
}
