package com.chxt.fantasticmonkey.controller.kanban;

import com.chxt.fantasticmonkey.bean.kanban.AllMissionVo;
import com.chxt.fantasticmonkey.bean.kanban.Mission;
import com.chxt.fantasticmonkey.service.kanban.MissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kanban")
public class MissionController {

    @Resource
    private MissionService missionService;

    @GetMapping("/mission/{id}")
    public Mission getMission(@PathVariable Integer id){
        return this.missionService.getMission(id);
    }

    @PostMapping("/mission")
    public void createMission(@RequestBody Mission mission) {
        this.missionService.createMission(mission);
    }

    @DeleteMapping("/mission/{id}")
    public void deleteMission(@PathVariable Integer id) {
        this.missionService.deleteMission(id);
    }

    @PutMapping("/mission")
    public void updateMission(@RequestBody Mission mission) {
        this.missionService.updateMission(mission);
    }

    @PutMapping("/mission/status")
    public void updateMissionStatus(@RequestBody Mission mission) {
        this.missionService.updateMissionStatus(mission);
    }

    @GetMapping("/mission/all")
    public AllMissionVo getAll() {
        return this.missionService.getAll();
    }

}
