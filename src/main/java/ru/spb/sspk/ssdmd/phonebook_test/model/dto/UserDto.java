package ru.spb.sspk.ssdmd.phonebook_test.model.dto;

import ru.spb.sspk.ssdmd.phonebook_test.model.entity.User;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class UserDto {
    private Long userId;

    public UserDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                ", userId=" + userId +
                '}';
    }
}
