package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, Long>
{
    Project findByIdProject(int idProject);
}
