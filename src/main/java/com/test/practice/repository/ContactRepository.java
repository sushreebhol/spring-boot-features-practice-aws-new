package com.test.practice.repository;

import com.test.practice.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);

    //Page<Contact> findByStatus(String status, Pageable pageable);

    //Example of @Query with fetching some data with both JPQL and Native SQL queries.
    //@Query("SELECT c FROM Contact c WHERE c.status = :status")
    //@Query(value = "SELECT * FROM contact_msg c WHERE c.status = :status",nativeQuery = true)
    //Page<Contact> findByStatus(@Param("status") String status, Pageable pageable);

    //Example of @Query with update some data using JPQL.
    /*@Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);
*/
    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    /*@Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);*/

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);
}
//@Query("")  - It accepts both JPQL and Native Sql query.
                //in JPQL query we don't use the table name and column name directly .
                // we use the Entity class name and entity class property.
                //But in native sql query we use the real table and column name .
//@Query("select c from Contact c where c.status = :status") - Example of @Query with JPQL(JAVA persistence query language)

//@Query(value = "select * from contact_msg c where c.status = :status", nativeQuery=true)

//@Transactional
//@Modifying  -- These 2 - annotations @Transactional and @Modifying is used for insert, update and delete operation
//@Query("update Contact c set c.status = ?1 where c.contactId = ?2")

//@NamedQuery() -It takes only JPQL and @NamedNativeQuery() - takes only native sql queries

//@NamedQuery() AND @NamedNativeQuery() provide us feature to put all the queries at one place together on the top of entity class
//Go to Contact.java and check