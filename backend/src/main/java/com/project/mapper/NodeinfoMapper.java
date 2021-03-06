package com.project.mapper;

import com.project.model.Nodeinfo;
import com.project.model.NodeinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NodeinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int countByExample(NodeinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int deleteByExample(NodeinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    @Delete({
        "delete from nodeinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    @Insert({
        "insert into nodeinfo (id, PI_MAC, ",
        "state, username, ",
        "longitude, latitude, ",
        "ip)",
        "values (#{id,jdbcType=INTEGER}, #{piMac,jdbcType=VARCHAR}, ",
        "#{state,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{longitude,jdbcType=DOUBLE}, #{latitude,jdbcType=DOUBLE}, ",
        "#{ip,jdbcType=VARCHAR})"
    })
    int insert(Nodeinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int insertSelective(Nodeinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    List<Nodeinfo> selectByExample(NodeinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, PI_MAC, state, username, longitude, latitude, ip",
        "from nodeinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Nodeinfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Nodeinfo record, @Param("example") NodeinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Nodeinfo record, @Param("example") NodeinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Nodeinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nodeinfo
     *
     * @mbggenerated
     */
    @Update({
        "update nodeinfo",
        "set PI_MAC = #{piMac,jdbcType=VARCHAR},",
          "state = #{state,jdbcType=INTEGER},",
          "username = #{username,jdbcType=VARCHAR},",
          "longitude = #{longitude,jdbcType=DOUBLE},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "ip = #{ip,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Nodeinfo record);
}