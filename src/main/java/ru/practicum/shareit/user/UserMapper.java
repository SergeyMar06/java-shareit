package ru.practicum.shareit.user;

public class UserMapper {
    public static UserDto toDto(User user) { // преобразуем в dto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public static User fromDto(UserDto userDto) { // преобразуем из dto
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
