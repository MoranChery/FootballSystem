package Model;

import java.util.ArrayList;
import java.util.List;

public class Complaint {
    private String subject;
    private String text;
    private List<String> comments;
    private String email;
    private Integer id;

    public Complaint(String subject, String text, String email,Integer id) {
        this.subject = subject;
        this.text = text;
        this.email = email;
        this.id=id;
        this.comments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
