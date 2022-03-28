package com.chxt.fantasticmonkey.service.kanban;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chxt.fantasticmonkey.bean.kanban.Mission;
import com.chxt.fantasticmonkey.bean.kanban.Project;
import com.chxt.fantasticmonkey.bean.kanban.ProjectDTO;
import com.chxt.fantasticmonkey.bean.kanban.ProjectQuery;
import com.chxt.fantasticmonkey.context.KanbanEnums;
import com.chxt.fantasticmonkey.dao.kanban.MissionMapper;
import com.chxt.fantasticmonkey.dao.kanban.ProjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private MissionMapper missionMapper;

    public List<Project> getProjectList(ProjectQuery query) {
        LambdaQueryWrapper<Project> queryWrapper = new QueryWrapper<Project>().lambda();
        if (!CollectionUtils.isEmpty(query.getType())) {
            queryWrapper.in(Project::getType, query.getType());
        }
        return this.projectMapper.selectList(queryWrapper);
    }


    public ProjectDTO getProject(Integer id) {
        Project project = this.projectMapper.selectById(id);
        ProjectDTO res = JSON.parseObject(JSON.toJSONString(project), ProjectDTO.class);

        LambdaQueryWrapper<Mission> query = new QueryWrapper<Mission>().lambda()
                .eq(Mission::getProjectId, id);
        List<Mission> mission = this.missionMapper.selectList(query);

        List<Mission> todo = mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.TODO.getCode())).collect(Collectors.toList());
        List<Mission> doing = mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.DOING.getCode())).collect(Collectors.toList());
        List<Mission> done = mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.DONE.getCode())).collect(Collectors.toList());
        res.setTodoList(todo);
        res.setDoingList(doing);
        res.setDoneList(done);
        return res;
    }

    public void updateProject(Project project) {
        this.projectMapper.updateById(project);
    }

    public void createProject(Project project) {
        this.projectMapper.insert(project);
    }

    public void deleteProject(Integer id) {
        this.projectMapper.deleteById(id);
    }


}
