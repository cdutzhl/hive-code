package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUserName(String userName);

}
