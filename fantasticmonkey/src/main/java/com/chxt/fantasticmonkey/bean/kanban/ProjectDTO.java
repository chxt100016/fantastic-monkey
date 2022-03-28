package com.chxt.fantasticmonkey.bean.kanban;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO extends Project{

    private List<Mission> todoList;

    private List<Mission> doingList;

    private List<Mission> doneList;
}
