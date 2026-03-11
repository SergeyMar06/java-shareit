package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDto findById(Long id) {
        return UserMapper.toDto(userRepository.findById(id));
    }

    public UserDto create(UserDto userDto) {
        return UserMapper.toDto(userRepository.create(UserMapper.fromDto(userDto)));
    }

    public UserDto update(UserDto newUserDto, Long id) {
        return UserMapper.toDto(userRepository.update(UserMapper.fromDto(newUserDto), id));
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}
