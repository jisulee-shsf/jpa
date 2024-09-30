package jpql;

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
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("memberA");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("memberC");
            member3.setTeam(team2);
            em.persist(member3);

            System.out.println("==========");

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
