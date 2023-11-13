package cn.scu.imc.hiver.service.impl;

import cn.scu.imc.api.vo.Pipeline;
import cn.scu.imc.hiver.repository.PipelineRepository;
import cn.scu.imc.hiver.service.IPipelineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class PipelineServiceImpl implements IPipelineService {


    @Resource
    private PipelineRepository pipelineRepository;


    @Override
    public Pipeline findById(Integer id) {
        Optional<Pipeline> pipelineOpt = pipelineRepository.findById(id);
        if (pipelineOpt.isPresent()) {
            return pipelineOpt.get();
        }
        throw new RuntimeException("Pipeline不存在");
    }

    @Override
    public boolean save(Pipeline pipeline) {
        pipelineRepository.save(pipeline);
        return true;
    }

    @Override
    public boolean update(Pipeline pipeline) {
        pipelineRepository.save(pipeline);
        return true;
    }
}
