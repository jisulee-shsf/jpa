package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //0. 예시
            Member member1 = new Member();
            member1.setName("A");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("B");
            em.persist(member2);

            em.flush();
            em.clear();

            //1. 프록시 객체 초기화
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //false
//
//            refMember.getName();
//            System.out.println("isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //true

            //2-1. 원본 엔티티를 상속받은 프록시 객체의 타입을 체크할 경우 -> instanceof 사용
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember.getClass() = " + findMember.getClass()); //class hellojpa.Member
//            Member refMember = em.getReference(Member.class, member2.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass()); //class hellojpa.Member$HibernateProxy$pQMyLFJd
//
//            System.out.println("== 비교 = " + (findMember.getClass() == refMember.getClass())); //false
//            System.out.println("instance of 사용1 = " + (findMember instanceof Member)); //true
//            System.out.println("instance of 사용2 = " + (refMember instanceof Member)); //true

            //2-2. 영속성 컨텍스트에 엔티티가 있을 때 em.getReference()를 호출할 경우 -> 실제 엔티티 반환
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember.getClass() = " + findMember.getClass()); //class hellojpa.Member
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass()); //class hellojpa.Member
//
//            System.out.println("== 비교 = " + (findMember.getClass() == refMember.getClass())); //true

            //2-3. 준영속 상태에서 프록시 객체를 초기화할 경우 -> LazyInitializationException 발생
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); //class hellojpa.Member$HibernateProxy$42ZMGajW
            System.out.println("isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //false

//            em.detach(refMember);
//            refMember.getName(); //org.hibernate.LazyInitializationException: could not initialize proxy [hellojpa.Member#1] - no Session

            Hibernate.initialize(refMember);
            System.out.println("isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //true

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
