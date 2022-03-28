package com.chxt.fantasticmonkey.controller.kanban;

import com.chxt.fantasticmonkey.bean.kanban.Project;
import com.chxt.fantasticmonkey.bean.kanban.ProjectDTO;
import com.chxt.fantasticmonkey.bean.kanban.ProjectQuery;
import com.chxt.fantasticmonkey.service.kanban.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/kanban")
@RestController
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @GetMapping("/project")
    public List<Project> listProject(ProjectQuery query) {
        return this.projectService.getProjectList(query);
    }


    @GetMapping("project/{id}")
    public ProjectDTO getProject(@PathVariable Integer id) {
        return this.projectService.getProject(id);
    }

    @PutMapping("/project")
    public void updateProject(@RequestBody ProjectDTO projectDTO) {
        this.projectService.updateProject(projectDTO);
    }

    @PostMapping("/project")
    public void createProject(@RequestBody ProjectDTO projectDTO) {
        this.projectService.createProject(projectDTO);
    }

    @DeleteMapping("/project/{id}")
    public void deleteProject(@PathVariable Integer id) {
        this.projectService.deleteProject(id);
    }

}
