package com.techstore.techstore_backend.modules.message.repository;

import com.techstore.techstore_backend.modules.message.entity.Message;
import com.techstore.techstore_backend.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MessageRepository extends JpaRepository<Message,Integer>{
    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender.id = :user1 AND m.receiver.id = :user2) " +
            "   OR (m.sender.id = :user2 AND m.receiver.id = :user1) " +
            "ORDER BY m.createdAt ASC")
    List<Message> getConversation(@Param("user1") int senderId, @Param("user2") int receiverId);

    @Query("""
            SELECT m FROM Message m
            WHERE m.receiver.id = :userId
            AND m.createdAt = (SELECT MAX(m2.createdAt) FROM Message m2 
            WHERE m2.sender = m.sender 
            AND m2.receiver.id = :userId)
            ORDER BY m.createdAt DESC
            """)
    List<Message> getAllLastestMessagesForUser(@Param("userId") int receiverId);

    @Query(value = """
                SELECT u.* FROM users u
                JOIN message m ON m.sender_id = u.id
                WHERE m.receiver_id = :userId
                AND (:keyword IS NULL OR LOWER(COALESCE(u.name, u.username)) LIKE LOWER(CONCAT('%', :keyword, '%')))
                GROUP BY u.id
                ORDER BY MAX(m.created_at) DESC
                LIMIT 20
            """, nativeQuery = true)
    List<User> getLastestSenders(@Param("userId") int userId,
                              @Param("keyword") String keyword);


}
