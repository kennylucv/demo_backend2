package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;;
import java.util.ArrayList;




@RestController
public class GreetingController {

    // @Autowired
    // private final UserRepository userRepository;

    // GreetingController(UserRepository userRepository){
    //     //this.userRepository = userRepository;
    // }

    // @RequestMapping("/greeting")
    // public ResponseEntity greeting(@RequestParam(value="name", defaultValue="World") String name) {
    //     // return new Greeting(counter.incrementAndGet(),
    //     //                     String.format(template, name));
    //      return new ResponseEntity(HttpStatus.OK);
    // }

    @RequestMapping(value = "/api/validate", method=RequestMethod.POST)
    public ResponseEntity validate(@RequestBody UserModel login){//, @RequestBody String password) {
        
        if (login.validateUser()){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(login.getUsername(),HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/{userId}", method=RequestMethod.GET)
    public ResponseEntity readBookmarks(@PathVariable String userId){//, @RequestBody String password) {
        UserModel user = new UserModel(userId);
        user.getUserAccounts();
        ArrayList<String> userAccountTypes = user.getAccountTypes();
        ArrayList<String> userAccountAmounts = user.getAccountAmounts();
        ArrayList<JSONObject> accounts = new ArrayList<JSONObject>();
        for (int i=0; i<userAccountTypes.size(); i++){
            JSONObject account = new JSONObject();
            account.put("type", userAccountTypes.get(i));
            account.put("amount", userAccountAmounts.get(i));
            accounts.add(account);
        }
        return new ResponseEntity(accounts,HttpStatus.OK);
    }

}
