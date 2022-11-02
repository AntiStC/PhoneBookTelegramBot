package ru.spb.sspk.ssdmd.phonebook_test.model.dto;

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
