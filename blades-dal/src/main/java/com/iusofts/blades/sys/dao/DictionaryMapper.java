package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.Dictionary;
import com.iusofts.blades.sys.model.DictionaryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper {
    int countByExample(DictionaryExample example);

    int deleteByExample(DictionaryExample example);

    int deleteByPrimaryKey(String id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    List<Dictionary> selectByExample(DictionaryExample example);

    Dictionary selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    /**
     * 根据父级ID删除所有子项（含多层）
     * @param pid
     * @return
     * @author：Ivan
     * @date：2016年2月26日 上午11:31:25
     */
    int deleteByPid(String pid);

    /**
     * 根据父级ID获取所有子项（含多层）
     * @param pid
     * @return
     * @author：Ivan
     * @date：2016年2月26日 下午12:18:04
     */
    List<Dictionary> selectByPid(String pid);

    /**
     * 根据父级ID和字典代码统计总数（检查代码是否存在）
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
     * 描述: 获取当前节点子节点个数
     * @param id
     * @return
     * @author Asker_lve
     * @date 2016年3月9日 下午6:00:56
     */
    int countByPid(String id);
}