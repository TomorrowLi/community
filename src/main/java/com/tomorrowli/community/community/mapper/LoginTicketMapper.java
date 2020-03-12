package com.tomorrowli.community.community.mapper;

import com.tomorrowli.community.community.dao.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @Author: LiMing
 * @Date: 2020/3/8 22:55
 * @Description:
 **/
@Mapper
public interface LoginTicketMapper {

    int updateStatusByUserId(@Param("ticket") String ticket,@Param("status") int status);

    int insertTicket(LoginTicket ticket);

    LoginTicket findLoginTicket(@Param("ticket") String ticket);

}
