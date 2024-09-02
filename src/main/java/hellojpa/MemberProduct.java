package hellojpa;

import jakarta.persistence.*;

@Entity
public class MemberProduct {

    @Id @GeneratedValue
    @Column(name = "MEMBER_PRODUCT_ID")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
