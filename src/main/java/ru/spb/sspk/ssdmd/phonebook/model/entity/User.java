package ru.spb.sspk.ssdmd.phonebook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_bot", catalog = "sspk_ssdmd_pb", schema = "public")
public class User {

    @Id
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
