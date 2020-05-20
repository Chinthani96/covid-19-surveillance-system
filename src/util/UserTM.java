package util;

public class UserTM {
    private String username;
    private String password;
    private String name;
    private String contact;
    private String email;
    private String role;

    public UserTM() {
    }

    public UserTM(String username, String password, String name, String contact, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
//        return ""+this.name +":         "+this.username+"";
        return this.name;
    }
}
