package ru.spb.sspk.ssdmd.phonebook.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person", catalog = "public", schema = "sspk_ssdmd_pb")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "department")
    private String department;

    @NotNull
    @Column(name = "phone")
    private Integer phone;

    @NotNull
    @Column(name = "mobil_phone")
    private Long mobilPhone;

    public Person() {
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String department;
        private Integer phone;
        private Long mobilPhone;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }

        public Builder setPhone(Integer phone) {
            this.phone = phone;
            return this;
        }

        public Builder setMobilPhone(Long mobilPhone) {
            this.mobilPhone = mobilPhone;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public Person(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.department = builder.department;
        this.phone = builder.phone;
        this.mobilPhone = builder.mobilPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getMobilPhone() {
        return mobilPhone;
    }

    public void setMobilPhone(Long mobilPhone) {
        this.mobilPhone = mobilPhone;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!firstName.equals(person.firstName)) return false;
        if (!middleName.equals(person.middleName)) return false;
        if (!lastName.equals(person.lastName)) return false;
        if (!department.equals(person.department)) return false;
        if (!phone.equals(person.phone)) return false;
        return mobilPhone.equals(person.mobilPhone);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + department.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + mobilPhone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", phone=" + phone +
                '}';
    }
}
