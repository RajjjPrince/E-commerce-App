package com.ecom.ecom_app.Services;

import com.ecom.ecom_app.Repository.UserRepository;
import com.ecom.ecom_app.dto.AddressDTO;
import com.ecom.ecom_app.dto.UserRequest;
import com.ecom.ecom_app.dto.UserResponse;
import com.ecom.ecom_app.model.Address;
import com.ecom.ecom_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    //private List<User> userList = new ArrayList<>();
    private final UserRepository userRepository ;
    private Long nextId = 1L;

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
//        return userList.stream()
//                .filter(user->user.getId().equals(id))
//                .findFirst()
//                .map(existingUser->{
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    return true;
//                }).orElse(false);
        return userRepository.findById(id)
                .map(existingUser->{
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());

                    updateUserFromRequest(existingUser,updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }


    public List<UserResponse> fetchAllUsers(){
       // return userList;
       // return userRepository.findAll();
        return userRepository.findAll().stream()
                .map(this :: mapToUserResponse)
                .collect(Collectors.toList());
    }
    public void addUser(UserRequest userRequest){
//        user.setId(nextId++);
//        userList.add(user);
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
    }



    public Optional<UserResponse> fetchUser(Long id) {
//        for(User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;
//        return userList.stream()
//                .filter(user-> user.getId().equals(id))
//                .findFirst();

        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setPincode(user.getAddress().getPincode());
            response.setAddress(addressDTO);

        }
        return response;
    }
    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = user.getAddress();
            if (address == null) {
                address = new Address(); // create if not exist
            }
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setPincode(userRequest.getAddress().getPincode());
            user.setAddress(address);

        }

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
