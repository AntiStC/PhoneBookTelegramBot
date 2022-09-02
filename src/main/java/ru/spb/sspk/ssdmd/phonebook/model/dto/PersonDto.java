package ru.spb.sspk.ssdmd.phonebook.model.dto;

public class PersonDto {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String department;
    private Integer phone;

    public PersonDto() {
    }

    public static class Builder{
        private Long id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String department;
        private Integer phone;

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

        public PersonDto build(){
            return new PersonDto(this);
        }
    }

    public PersonDto(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.department = builder.department;
        this.phone = builder.phone;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return firstName + " \n" +
                middleName + " \n" +
                lastName + " \n" +
                department + " \n" +
                "#" + phone;
    }
}
