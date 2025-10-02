package com.ecom.ecom_app.Services;

import com.ecom.ecom_app.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public boolean updateUser(Long id, User updatedUser) {
        return userList.stream()
                .filter(user->user.getId().equals(id))
                .findFirst()
                .map(existingUser->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }


    public List<User> fetchAllUsers(){
        return userList;
    }
    public void addUser(User user){
        user.setId(nextId++);
        userList.add(user);
    }

    public Optional<User> fetchUser(Long id) {
//        for(User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;
        return userList.stream()
                .filter(user-> user.getId().equals(id))
                .findFirst();
    }



}
