package com.iusofts.blades.sys.dao;

import com.iusofts.blades.sys.model.AccountExample;
import com.iusofts.blades.sys.model.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    int countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(String id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    /**
     *
     * 描述: 根据id修改有效性
     * @param enabled
     * @param id
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午2:41:57
     */
    int updateEnabledById(@Param("enabled")String enabled,@Param("id")String id);

    /**
     *
     * 描述: 批量修改有效性
     * @param enabled
     * @param ids
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午2:40:07
     */
    int updateEnabledByIds(@Param("enabled")String enabled, @Param("ids")List<String> ids);

    /**
     *
     * 描述:批量删除
     * @param ids
     * @return
     * @author Asker_lve
     * @date 2016年3月10日 下午2:41:18
     */
    int deleteByIds(@Param("ids")List<String> ids);

    /**
     *
     * 描述: 用户名是否存在
     * @param userName
     * @return
     * @author Asker_lve
     * @date 2016年3月11日 下午6:15:31
     */
    int isUserNameExist(@Param("userName")String userName);
}