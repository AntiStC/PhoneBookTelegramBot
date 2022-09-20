package ru.spb.sspk.ssdmd.phonebook.model.dto;

import ru.spb.sspk.ssdmd.phonebook.model.entity.User;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class UserDto {

    private Long id;
    private Long userId;
    private String userFirstName;
    private String userLastName;

    public UserDto() {
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private String userFirstName;
        private String userLastName;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
            return this;
        }

        public Builder setUserLastName(String userLastName) {
            this.userLastName = userLastName;
            return this;
        }

        public UserDto build(){
            return new UserDto(this);
        }
    }

    public UserDto(Builder builder){
        this.id= builder.id;
        this.userId= builder.userId;
        this.userFirstName= builder.userFirstName;
        this.userLastName= builder.userLastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                '}';
    }
}
