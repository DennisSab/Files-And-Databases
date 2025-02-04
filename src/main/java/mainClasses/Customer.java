package mainClasses;

public class Customer {
    int customer_id;
    String username , password , firstname , lastname , email;
    int card_details;

    public int getId() {
        return customer_id;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCardDetails() {
        return card_details;
    }

    public void setCardDetails(int card_details) {
        this.card_details = card_details;
    }
}
