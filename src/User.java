public class User {
    private Credentials credentials;

    public User(){}

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "User{" +
                "credentials=" + credentials +
                '}';
    }
}

