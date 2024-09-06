package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //1. 값 타입 저장
            Member member = new Member();
            member.setName("userA");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("foodA");
            member.getFavoriteFoods().add("foodB");

            member.getAddressHistory().add(new Address("cityA", "street", "20000"));
            member.getAddressHistory().add(new Address("cityB", "street", "30000"));
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=====");

            //2. 값 타입 수정
            //2-1. private Address homeAddress;
            Member findMember = em.find(Member.class, member.getId());

//            findMember.getHomeAddress().setCity("newCity");
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));

            //2-2. private Set<String> favoriteFoods = new HashSet<>();
            findMember.getFavoriteFoods().remove("foodA");
            findMember.getFavoriteFoods().add("foodAAA");

            //2-3. private List<Address> addressHistory = new ArrayList<>();
            findMember.getAddressHistory().remove(new Address("cityA", "street", "20000"));
            findMember.getAddressHistory().add(new Address("cityAAA", "street", "20000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
