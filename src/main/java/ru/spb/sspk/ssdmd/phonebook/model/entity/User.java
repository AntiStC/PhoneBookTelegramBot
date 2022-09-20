package ru.spb.sspk.ssdmd.phonebook.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_bot", catalog = "public", schema = "sspk_ssdmd_pb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "user_id")
    private Long userId;
    @NotNull
    @Column(name = "user_first_name")
    private String userFirstName;
    @Column(name = "user_last_name")
    private String userLastName;

    public User() {
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

        public User build(){
            return new User(this);
        }
    }

    public User(Builder builder){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!userId.equals(user.userId)) return false;
        return userFirstName != null ? userFirstName.equals(user.userFirstName) : user.userFirstName == null;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + (userFirstName != null ? userFirstName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                '}';
    }
}
