package com.chxt.fantasticmonkey.service.kanban;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chxt.fantasticmonkey.bean.kanban.AllMissionVo;
import com.chxt.fantasticmonkey.bean.kanban.Mission;
import com.chxt.fantasticmonkey.context.KanbanEnums;
import com.chxt.fantasticmonkey.dao.kanban.MissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionService {
    @Resource
    private MissionMapper missionMapper;

    public void createMission(Mission mission) {
        mission.setStatus(KanbanEnums.MissionStatusEnum.TODO.getCode());
        mission.setCreateTime(new Date());
        this.missionMapper.insert(mission);
    }

    public void deleteMission(Integer id) {
        this.missionMapper.deleteById(id);
    }

    public void updateMission(Mission mission) {
        this.missionMapper.updateById(mission);
    }

    public void updateMissionStatus(Mission mission) {
        Mission original = this.missionMapper.selectById(mission.getId());

        LambdaUpdateWrapper<Mission> updateWrapper = new UpdateWrapper<Mission>().lambda()
                .set(Mission::getStatus, mission.getStatus())
                .eq(Mission::getId, mission.getId());
        if (KanbanEnums.MissionStatusEnum.TODO.getCode().equals(mission.getStatus())) {
            updateWrapper.set(Mission::getStartTime, null);
            updateWrapper.set(Mission::getEndTime, null);
            updateWrapper.set(Mission::getActualCost, null);
        }
        if (KanbanEnums.MissionStatusEnum.DOING.getCode().equals(mission.getStatus())) {
            if (original.getStartTime() == null) {
                updateWrapper.set(Mission::getStartTime, mission.getStartTime());
            }
            updateWrapper.set(Mission::getEndTime, null);
            updateWrapper.set(Mission::getActualCost, null);
        }
        if (KanbanEnums.MissionStatusEnum.DONE.getCode().equals(mission.getStatus())) {
            if (original.getStartTime() == null) {
                updateWrapper.set(Mission::getStartTime, mission.getStartTime());
            }
            updateWrapper.set(Mission::getEndTime, mission.getEndTime());
            updateWrapper.set(Mission::getActualCost, (mission.getEndTime().getTime() - mission.getStartTime().getTime())/1000);
        }
        this.missionMapper.update(null, updateWrapper);
    }

    public Mission getMission(Integer id) {
        return this.missionMapper.selectById(id);
    }

    public AllMissionVo getAll() {
        List<Mission> mission = this.missionMapper.selectList(null);
        return AllMissionVo.builder()
                .todoList(mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.TODO.getCode())).collect(Collectors.toList()))
                .doingList(mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.DOING.getCode())).collect(Collectors.toList()))
                .doneList(mission.stream().filter(item -> item.getStatus().equals(KanbanEnums.MissionStatusEnum.DONE.getCode())).collect(Collectors.toList()))
                .build();
    }
}
