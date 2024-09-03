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
            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setName("user");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass()); //class hellojpa.Team$HibernateProxy$I4NGCADD
            System.out.println("isLoaded(findMember.getTeam()) = " + emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())); //false
            System.out.println("findMember.getTeam() = " + findMember.getTeam()); //hellojpa.Team@794f11cd
            System.out.println("team = " + team); //hellojpa.Team@31973858

            findMember.getTeam().getName(); //프록시 객체 초기화
            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass()); //class hellojpa.Team$HibernateProxy$I4NGCADD
            System.out.println("isLoaded(findMember.getTeam()) = " + emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())); //true
            System.out.println("findMember.getTeam() = " + findMember.getTeam()); //hellojpa.Team@794f11cd
            System.out.println("team = " + team); //hellojpa.Team@31973858

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
