package cn.scu.imc.hiver.config;


import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.utils.HiveUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        User user = HiveUtil.getCurrentUser();
        return Optional.ofNullable(user.getId());
    }
}
