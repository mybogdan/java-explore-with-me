package ru.practicum.user.mapper;

import org.springframework.data.domain.Page;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserShortDto userToUserShortDto(User user) {

        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public static User UserDtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public static List<UserDto> usersToUsersDto(Page<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(userToUserDto(user));
        }
        return usersDto;
    }

    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
