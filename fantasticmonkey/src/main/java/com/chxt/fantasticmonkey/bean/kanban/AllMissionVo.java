package com.chxt.fantasticmonkey.bean.kanban;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllMissionVo {

    private List<Mission> todoList;

    private List<Mission> doingList;

    private List<Mission> doneList;
}
