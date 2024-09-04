package cn.scu.imc.hiver.service;


import java.io.IOException;

public interface IBuildService {

    void build(Integer projectId) throws IOException;

}
