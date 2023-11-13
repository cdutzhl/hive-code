package cn.scu.imc.hiver.service;

import cn.scu.imc.api.vo.Pipeline;

public interface IPipelineService {

     boolean save(Pipeline pipeline);

     boolean update(Pipeline pipeline);

     Pipeline findById(Integer id);



}
