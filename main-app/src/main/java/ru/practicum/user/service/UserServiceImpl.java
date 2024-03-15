package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.error.NotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getList(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id"));
        Page<User> users;

        if (ids == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findAllByIdIn(ids, pageable);

        }
        return UserMapper.usersToUsersDto(users);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = UserMapper.UserDtoToUser(userDto);
        return UserMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(
                                "User with id=" + id + " was not found",
                                "The required object was not found."
                        ));

        userRepository.deleteById(id);
    }
}
