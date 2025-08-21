package net.kopuz.ApI.service;

import net.kopuz.ApI.dto.UserDto;
import net.kopuz.ApI.entity.Role;
import net.kopuz.ApI.entity.User;
import net.kopuz.ApI.repository.RoleRepository;
import net.kopuz.ApI.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public UserDto saveUser(UserDto userDto){

        if(userDto.username().isEmpty()
                || userDto.password().isEmpty()
                || userDto.age() <= 0
                ||userDto.gender().equals(null)
                ||userDto.birthPlace().isEmpty()){
            throw new RuntimeException("User fields cannot be empty");
        }

        if(userRepository.existsByUsername(userDto.username())){
            throw new RuntimeException("Username is already taken");
        }

        String encodedPassword = encoder.encode(userDto.password());

        Role role = roleRepository.findByRolename("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found: " + "ROLE_USER"));

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(encodedPassword);
        user.setAge(userDto.age());
        user.setGender(userDto.gender());
        user.setBirthPlace(userDto.birthPlace());
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

        return userDto;
    }

    public String saveRole(Role role) {
        if(role.getRolename().isEmpty()){
            throw new RuntimeException("Role must not be empty");
        }
        if(roleRepository.existsByRolename(role.getRolename())){
            throw new RuntimeException("Rolename is already taken");
        }

        roleRepository.save(role);

        return "Role added successfully";
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(UserDto::convert)
                .collect(Collectors.toList());

        return userDtos;
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        return UserDto.convert(user);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));

        return UserDto.convert(user);
    }

    public List<String> getCitiesOfUsers(){
        return getAllUsers().stream().map(UserDto::birthPlace).collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        user.setUsername(userDto.username());
        user.setPassword(encoder.encode(userDto.password()));
        user.setAge(userDto.age());
        user.setGender(userDto.gender());
        user.setBirthPlace(userDto.birthPlace());

        User updatedUser = userRepository.save(user);

        return UserDto.convert(updatedUser);
    }

    public String deleteUser(Long userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return "User has deleted with id: " + userId;
        }
        else{
            return "User not found with id: " + userId;
        }
    }
}
