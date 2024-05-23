package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Integer> {

    List<GroupUser> findByGroupIdAndUserId(Integer groupId, Integer userId);

    @Query("delete from GroupUser where groupId=:groupId and userId in (:userIds)")
    @Modifying
    void deleteByGroupIdAndUserId(@Param(value = "groupId") Integer groupId, @Param(value = "userIds")List<Integer> userIds);


}
