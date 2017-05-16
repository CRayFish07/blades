package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.Role;
import com.iusofts.blades.sys.model.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     *
     * 描述: 校验角色名称是否存在
     *
     * @param name
     * @return
     * @author Asker_lve
     * @date 2016年3月4日 下午2:26:38
     */
    int nameExist(String name);

    /**
     *
     * 描述:校验角色代码是否存在
     *
     * @param code
     * @return
     * @author Asker_lve
     * @date 2016年3月4日 下午2:27:05
     */
    int codeExist(String code);

    /**
     *
     * 描述:批量删除
     *
     * @param ids
     * @return
     * @author Asker_lve
     * @date 2016年3月6日 下午9:29:05
     */
    int delsteByIds(@Param("ids") List<String> ids);

    /**
     *
     * 描述: 批量修改有效性
     * @param ids
     * @param enable
     * @return
     * @author Asker_lve
     * @date 2016年3月9日 上午11:21:30
     */
    public int updateEnables(@Param("enabled") String enabled,@Param("ids") List<String> ids);

    /**
     *
     * 描述:修改单条有效性
     * @param id
     * @param enable
     * @return
     * @author Asker_lve
     * @date 2016年3月9日 上午11:22:07
     */
    public int updateEnable(@Param("enabled")String enabled,@Param("id")String id);
}