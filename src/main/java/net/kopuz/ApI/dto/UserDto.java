package net.kopuz.ApI.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import net.kopuz.ApI.entity.Gender;
import net.kopuz.ApI.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDto(Long id,
                      String username,
                      String password,
                      @Enumerated(EnumType.STRING)
                      Gender gender,
                      Integer age,
                      String birthPlace,
                      List<String> roles) {
    public static UserDto convert(User from){
        return new UserDto(from.getId(),
                from.getUsername(),
                from.getPassword(),
                from.getGender(),
                from.getAge(),
                from.getBirthPlace(),
                from.getRoles().stream().map(r -> r.getRolename()).collect(Collectors.toList()));
    }
}
