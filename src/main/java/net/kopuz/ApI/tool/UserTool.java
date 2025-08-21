package net.kopuz.ApI.tool;

import net.kopuz.ApI.dto.UserDto;
import net.kopuz.ApI.service.UserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTool {
    private final UserService userService;
    public UserTool(UserService userService) {
        this.userService = userService;
    }

    @Tool(description = "It returns the user of specified name from client. You must answer when client wanted a user" +
            "from the system.")
    public UserDto getUser(String username) {
        return userService.getUserByUsername(username);
    }

    @Tool(description = "It returns the birthplaces of users. You must answer when client wanted a lived city info" +
            "from the system.")
    public List<String> getCitiesOfUsers() {
        return userService.getCitiesOfUsers();
    }
}
