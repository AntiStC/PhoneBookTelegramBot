package ru.spb.sspk.ssdmd.phonebook_test.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_bot", catalog = "public", schema = "sspk_ssdmd_pb")
public class User {

    @Id
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userId=" + userId +
                '}';
    }
}
