package com.ecom.ecom_app.Controller;

import com.ecom.ecom_app.Services.UserService;
import com.ecom.ecom_app.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers()
                                    ,HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<User> getUser(@PathVariable Long id){
//        User user = userService.fetchUser(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping()
    public ResponseEntity< String> createUsers(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>( "User added Successfully"
                                    ,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatedUser (@PathVariable Long id,@RequestBody User updatedUser){
        boolean updated = userService.updateUser(id,updatedUser);
        if(updated)
            return ResponseEntity.ok("User Updated Successfully");
        return ResponseEntity.notFound().build();
    }


}
