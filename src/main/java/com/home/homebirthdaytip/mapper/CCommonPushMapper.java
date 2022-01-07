package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.CCommonPush;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Entity com.home.homebirthdaytip.domain.CCommonPush
 */
public interface CCommonPushMapper extends BaseMapper<CCommonPush> {

    List<CCommonPush> selectAllByPushTypeAndPushStatus(@Param("pushType") Integer pushType, @Param("pushStatus") Integer pushStatus);

    List<CCommonPush> selectAllByPushTypeAndPushStatusOrderByIdDesc(@Param("pushType") Integer pushType, @Param("pushStatus") Integer pushStatus);

    int countByPushTypeAndPushStatus(@Param("pushType") Integer pushType, @Param("pushStatus") Integer pushStatus);

    @Select("select push_status name,COUNT(1) value from c_common_push WHERE push_type=#{type} GROUP BY push_status")
    List<Map<String, Object>> getEmailTaskResults(Integer type);

    @Select("select a.click_date,ifnull(b.count,0) as count\n" +
            "from (\n" +
            "    SELECT curdate() as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 1 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 2 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 3 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 4 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 5 day) as click_date\n" +
            "    union all\n" +
            "    SELECT date_sub(curdate(), interval 6 day) as click_date\n" +
            ") a left join (\n" +
            "  select DATE_FORMAT(push_end_time, '%Y-%m-%d') as datetime, count(*) as count\n" +
            "  from c_common_push where push_status=1 and push_type=#{type}\n" +
            "  group by DATE_FORMAT(push_end_time, '%Y-%m-%d')\n" +
            ") b on a.click_date = b.datetime;")
    List<Map<String, Object>> getWeekPushCondition(int type);


    List<CCommonPush> selectPushStatusAndPushTypeAndPushThemeAndPushAccountNameByPushType(@Param("pushType") Integer pushType);



    @Select(" select push_status name,COUNT(1) value  from c_common_push where  push_end_time >= #{pushEndTime} AND push_type = #{pushType,jdbcType=NUMERIC} GROUP BY push_status")
    List<Map<String, Object>> getPushStatusCountByType(@Param("pushEndTime") Date pushEndTime, @Param("pushType") Integer pushType);
}




