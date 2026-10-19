package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, Long>
{
    List<Project> findAll();
    Project findByIdProject(int IdProject);
}
