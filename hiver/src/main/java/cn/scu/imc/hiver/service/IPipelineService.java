package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.Pipeline;

public interface IPipelineService {

     boolean save(Pipeline pipeline);

     boolean update(Pipeline pipeline);

     Pipeline findById(Integer id);



}
