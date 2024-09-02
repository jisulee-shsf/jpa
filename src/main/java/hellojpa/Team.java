//package hellojpa;
//
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Team {
//
//    @Id @GeneratedValue
//    @Column(name = "TEAM_ID")
//    private Long id;
//    private String name;
//
//    @OneToMany
//    @JoinColumn(name = "TEAM_ID")
//    private List<Member> members = new ArrayList<>();
//}
