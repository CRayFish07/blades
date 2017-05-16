package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.User;
import com.iusofts.blades.sys.model.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询用户列表
     * @param map
     * @return
     * @author：Ivan
     * @date：2016年3月7日 上午9:13:01
     */
    List<Map<String,String>> selectByMap(Map<String,String> map);

    /**
     * 统计用户数量
     * @param map
     * @return
     * @author：Ivan
     * @date：2016年3月7日 上午9:13:14
     */
    int countByMap(Map<String,String> map);

    /**
     *
     * 描述:批量删除
     * @param ids
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午1:59:36
     */
    int delsteByIds(@Param("ids")List<String> ids);

    /**
     *
     * 描述: 身份证是否存在
     * @param idNo
     * @return
     * @author Asker_lve
     * @date 2016年3月11日 下午6:11:57
     */
    int isIdNoExist(@Param("idNo")String idNo);
}