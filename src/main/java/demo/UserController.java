package demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> index(@RequestParam Map<String, String> body) {
        return new ResponseEntity<String>(body.get("id"), HttpStatus.CREATED);
    }

}