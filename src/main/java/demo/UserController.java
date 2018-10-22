package demo;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestParam Map<String, String> body) {
        String id = body.get("id");
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        User user = new User(id, firstName, lastName);
        try{
            UserDAO.addUser(user);
        } catch(Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>(user.toString(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<String> getUsers() {
        try {
            List<User> users = UserDAO.getUsers();
            String res = JSON.toJSONString(users);
            return new ResponseEntity<String>(res, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}