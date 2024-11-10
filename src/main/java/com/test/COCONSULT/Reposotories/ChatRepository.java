package com.test.COCONSULT.Reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.COCONSULT.Entity.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findAllBySenderId(Long idsender);
    List<Chat>  findAllByGroupChatId(Long groupid);
}
