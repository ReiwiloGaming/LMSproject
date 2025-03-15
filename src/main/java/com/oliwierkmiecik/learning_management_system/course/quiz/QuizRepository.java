package com.oliwierkmiecik.learning_management_system.course.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByCourseId(Integer courseId);
}
