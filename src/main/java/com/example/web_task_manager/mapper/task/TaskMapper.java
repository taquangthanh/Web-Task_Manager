package com.example.web_task_manager.mapper.task;

import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.entity.TaskStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    List<TaskEntity> findAll();

    void insertAll(TaskEntity taskEntity);

    List<TaskEntity> findByTitle(@Param("title") String title);

    List<TaskEntity> findByStatus(@Param("status") String status);

    List<TaskEntity> findByDeleteFlag();

    TaskEntity findById(@Param("id") Long id);

    void delete(@Param("task") TaskEntity taskEntity);

     void update(@Param("task") TaskEntity taskEntity);

    List<TaskEntity> findByTitleContaining(@Param("title") String title, @Param("page") Pageable pageable);

    int countByTitle(@Param("title") String title);

    int countTasksFilterTitle(@Param("title") String title);

    List<TaskEntity> findAllPage(@Param("page") Pageable pageable);

    Integer countAllTasks();

    Integer countTasksFilterTitleAndStatus(@Param("title") String title, @Param("status") TaskStatus status);

    List<TaskEntity> findByTitleContainingAndTaskStatus(@Param("title") String title,
                                                  @Param("status") TaskStatus status,
                                                  @Param("page") Pageable pageable);

    List<TaskEntity> findByTaskStatus(@Param("status") TaskStatus status, @Param("page") Pageable pageable);

    Integer countTasksFilterStatus(@Param("status") TaskStatus status);

}
