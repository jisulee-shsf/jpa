package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long Id;

    @Column(name = "USERNAME")
    private String username;

    @OneToOne
    @JoinColumn(name = "LOOKER_ID")
    private Locker locker;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
