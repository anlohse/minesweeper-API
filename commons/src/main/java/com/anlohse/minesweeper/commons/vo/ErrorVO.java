package com.anlohse.minesweeper.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVO {

    private String message;

    private List<ErrorFieldVO> fields = new ArrayList<>();

}
