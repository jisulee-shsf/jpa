package jpql;

import jakarta.persistence.*;

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

            //TypedQuery & Query
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            Query query2 = em.createQuery("select m.username, m.age from Member m");

            //결과 조회 API
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.age = 100", Member.class);

            List<Member> result1 = query.getResultList();
            System.out.println("result1 = " + result1); //[]
            Member result2 = query.getSingleResult();
            System.out.println("result2 = " + result2); //NoResultException

            //파라미터 바인딩
            Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "memberA")
                    .getSingleResult();
            System.out.println("findMember = " + findMember.getUsername()); //memberA

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
