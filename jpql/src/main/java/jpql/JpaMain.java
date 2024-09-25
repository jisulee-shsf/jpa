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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("memberA");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("==========");

            //상태 필드(탐색 불가)
            String query1 = "select m.username from Member m where m.id = :id";
            String result1 = em.createQuery(query1, String.class)
                    .setParameter("id", member1.getId())
                    .getSingleResult();

            //단일 값 연관 경로 - 묵시적 내부 조인 발생(탐색 가능)
            String query2 = "select m.team from Member m"; // String query2 = "select t from Member m join m.team t";
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();

            //단일 값 연관 경로 - 묵시적 내부 조인 발생(탐색 불가)
            String query3 = "select t.members from Team t"; //String query3 = "select m from Team t join t.members m";
            List<Member> result3 = em.createQuery(query3, Member.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
