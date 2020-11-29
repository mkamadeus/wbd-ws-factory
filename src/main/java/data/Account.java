package data;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Account {
    private final int id;
    private String username;
    private String password;

    private static String hashPassword(String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder hash = new StringBuilder(no.toString(16));
        while (hash.length() < 32) {
            hash.insert(0, "0");
        }
        return hash.toString();
    }

    public Account(int id, String username, String password, boolean hash) {
        this.id = id;
        this.username = username;
        if(hash){
            try{
                this.password = hashPassword(password);
            }
            catch (Exception e){
                this.password = "";
            }
        }
        else{
            this.password = password;
        }

    }

    public int getId() {
        return id;
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
}
