package cn.scu.imc.hiver.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Paging<T>{

    private List<T> data;

    private long pageSize;

    private long pageIndex;

    private long total;

}
