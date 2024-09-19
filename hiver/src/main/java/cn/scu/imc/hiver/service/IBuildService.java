package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.entity.Build;

import java.io.IOException;

public interface IBuildService {

    void build(Integer projectId) throws IOException;

    Build createNew(Integer projectId) ;

}
