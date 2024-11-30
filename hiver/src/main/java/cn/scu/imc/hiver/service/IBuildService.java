package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.BuildHistoryVo;

import java.io.IOException;
import java.util.List;

public interface IBuildService {

    void build(Integer projectId) throws IOException;

    List<BuildHistoryVo> getHistoryBuild(Integer projectId);

}
