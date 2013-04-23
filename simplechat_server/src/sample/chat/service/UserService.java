package sample.chat.service;

import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import sample.chat.meta.UserMeta;
import sample.chat.model.User;
import sample.chat.utils.Encrypter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class UserService {
    private static UserMeta meta = UserMeta.get();

    public static User createUser(Map<String, Object> input) {
        User user = new User();
        Key key = Datastore.allocateId(User.class);
        BeanUtil.copy(input, user);
        user.setKey(key);
        user.setUserId(String.valueOf(key.getId()));
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(user);
        tx.commit();
        return user;
    }

    public static List<User> getUserByEmail(String email) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.email.equal(email))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static User getUserByEmailAndPassword(String email, String password) {
        try {
            return Datastore
                .query(meta)
                .filter(
                    meta.email.equal(email),
                    meta.password.equal(Encrypter.getHash(password)))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
