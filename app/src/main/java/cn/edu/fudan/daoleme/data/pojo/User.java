package cn.edu.fudan.daoleme.data.pojo;

/**
 * Created by rinnko on 2015/11/24.
 */
public class User {
    private long id;
    private String name;
    private String email;
    // a string for user authenticate, like cookie
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
