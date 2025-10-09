package com.ecom.ecom_app.Controller;

import com.ecom.ecom_app.Services.UserService;
import com.ecom.ecom_app.dto.UserRequest;
import com.ecom.ecom_app.dto.UserResponse;
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
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers()
                                    ,HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserResponse> getUser(@PathVariable Long id){
//        User user = userService.fetchUser(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping()
    public ResponseEntity< String> createUsers(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return new ResponseEntity<>( "User added Successfully"
                                    ,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatedUser (@PathVariable Long id,
                                               @RequestBody UserRequest updatedUserRequest){
        boolean updated = userService.updateUser(id,updatedUserRequest);
        if(updated)
            return ResponseEntity.ok("User Updated Successfully");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
