package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.UserOrgExample;
import com.iusofts.blades.sys.model.UserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserOrgMapper {
    int countByExample(UserOrgExample example);

    int deleteByExample(UserOrgExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    List<UserOrg> selectByExample(UserOrgExample example);

    UserOrg selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserOrg record, @Param("example") UserOrgExample example);

    int updateByExample(@Param("record") UserOrg record, @Param("example") UserOrgExample example);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);

    /**
     * 根据用户ID获取用户岗位信息
     *
     * @param uid
     * @return
     * @author：Ivan
     * @date：2016年3月7日 下午5:30:19
     */
    List<Map<String, String>> selectByUid(String uid);

    /**
     *
     * 描述:根据用户id批量删除
     *
     * @param userIds
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午2:40:59
     */
    int deleteByUserIds(@Param("userIds")List<String> userIds);

    /**
     *
     * 描述: 根据用户id删除
     * @param userId
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午2:58:22
     */
    int deleteByUserId(@Param("userId") String userId);
}