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

            em.flush();
            em.clear();

            System.out.println("==========");

            String query1 = "select m from Member m";
            List<Member> result1 = em.createQuery(query1, Member.class)
                    .getResultList();

            //다대일 페치 조인 사용
            String query2 = "select m from Member m join fetch m.team";
            List<Member> result2 = em.createQuery(query2, Member.class)
                    .getResultList();

            for (Member member : result2) {
                System.out.println("member = " + member.getUsername() + " / " + member.getTeam().getName());
            }

            //일대다 페치 조인 사용
            String query3 = "select t from Team t join fetch t.members";
            List<Team> result3 = em.createQuery(query3, Team.class)
                    .getResultList();

            for (Team team : result3) {
                System.out.println("team = " + team.getName() + " / " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println(" -> member = " + member);
                }
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
