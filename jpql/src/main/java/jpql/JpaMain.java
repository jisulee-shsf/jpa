package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //엔티티 프로젝션 -> 영속 상태 확인
            List<Member> result1 = em.createQuery("select m from Member m", Member.class)
            .getResultList();

            Member findMember = result1.get(0);
            findMember.setAge(100);

            //엔티티 프로젝션
            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class)
            .getResultList();
            List<Team> result3 = em.createQuery("select t from Member m join m.team t", Team.class)
            .getResultList();

            //임베디드 타입 프로젝션
            List<Address> result4 = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            //스칼라 타입 프로젝션
            List<MemberDTO> result5 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result5.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername()); //memberA
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge()); //10

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
