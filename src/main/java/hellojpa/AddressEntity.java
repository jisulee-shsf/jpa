package hellojpa;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    private Address address;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String number) {
        this.address = new Address(city, street, number);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
