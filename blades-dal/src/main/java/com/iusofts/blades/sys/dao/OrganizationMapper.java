package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.OrganizationExample;
import com.iusofts.blades.sys.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganizationMapper {
    int countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int deleteByPrimaryKey(String id);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationExample example);

    Organization selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    /**
     * 根据父级ID删除所有下级单位（含多层）
     * @param pid
     * @return
     * @author：Ivan
     * @date：2016年2月26日 上午11:31:25
     */
    int deleteByPid(String pid);

    /**
     * 根据父级ID获取所有下级单位（含多层）
     * @param pid
     * @return
     * @author：Ivan
     * @date：2016年2月26日 下午12:18:04
     */
    List<Organization> selectByPid(String pid);

    /**
     * 根据父级ID和组织机构代码统计总数（检查代码是否存在）
     * @param pid
     * @param code
     * @return
     * @author：Ivan
     * @date：2016年2月26日 下午12:44:37
     */
    int countByCode(@Param("code")String code,@Param("pid")String pid);

    /**
     * 获取根结点ID
     * @param id
     * @return
     * @author：Ivan
     * @date：2016年2月26日 下午1:51:54
     */
    String selectRootId(String id);

    /**
     *
     * 描述: 根据pid获取字节点数目
     * @param id
     * @return
     * @author Asker_lve
     * @date 2016年3月9日 下午5:01:51
     */
    int countByPid(String id);
}