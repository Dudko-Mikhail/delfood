package by.dudko.webproject.model.entity;

public class User extends RootEntity {
    private Role role;
    private String email;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public User() { // todo think about constructors and explore other people implementations
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static class UserBuilder {
        private final User user;

        UserBuilder() {
            user = new User();
        }

        public UserBuilder id(long id) {
            user.setId(id);
            return this;
        }

        public UserBuilder role(Role role) {
            user.setRole(role);
            return this;

        }

        public UserBuilder email(String email) {
            user.setEmail(email);
            return this;

        }

        public UserBuilder login(String login) {
            user.setLogin(login);
            return this;

        }

        public UserBuilder password(String password) {
            user.setPassword(password);
            return this;

        }

        public UserBuilder firstName(String firstName) {
            user.setFirstName(firstName);
            return this;

        }

        public UserBuilder lastName(String lastName) {
            user.setLastName(lastName);
            return this;

        }

        public UserBuilder phoneNumber(String phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }

        public User buildUser() {
            return user;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("UserBuilder{");
            sb.append("user=").append(user);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (role != user.role) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return phoneNumber != null ? phoneNumber.equals(user.phoneNumber) : user.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("role=").append(role);
        sb.append(", email='").append(email).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(lastName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
