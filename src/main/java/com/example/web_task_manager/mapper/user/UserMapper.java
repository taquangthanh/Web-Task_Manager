package com.example.web_task_manager.mapper.user;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.web_task_manager.entity.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    void insertAll(User user);

    User findByUsername(@Param("username") String username);
}
