package com.rare_earth_track.mgb.mapper;

import com.rare_earth_track.mgb.model.RetApplyFactory;
import com.rare_earth_track.mgb.model.RetApplyFactoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RetApplyFactoryMapper {
    /**
     * countByExample
     * @param example example
     * @return long
     */
    long countByExample(RetApplyFactoryExample example);

    /**
     * deleteByExample
     * @param example example
     * @return int
     */
    int deleteByExample(RetApplyFactoryExample example);

    /**
     * deleteByPrimaryKey
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param row row
     * @return int
     */
    int insert(RetApplyFactory row);

    /**
     * insertSelective
     * @param row row
     * @return int
     */
    int insertSelective(RetApplyFactory row);

    /**
     * selectByExample
     * @param example example
     * @return java.util.List<com.rare_earth_track.mgb.model.RetApplyFactory>
     */
    List<RetApplyFactory> selectByExample(RetApplyFactoryExample example);

    /**
     * selectByPrimaryKey
     * @param id id
     * @return com.rare_earth_track.mgb.model.RetApplyFactory
     */
    RetApplyFactory selectByPrimaryKey(Long id);

    /**
     * updateByExampleSelective
     * @param row row
     * @param example example
     * @return int
     */
    int updateByExampleSelective(@Param("row") RetApplyFactory row, @Param("example") RetApplyFactoryExample example);

    /**
     * updateByExample
     * @param row row
     * @param example example
     * @return int
     */
    int updateByExample(@Param("row") RetApplyFactory row, @Param("example") RetApplyFactoryExample example);

    /**
     * updateByPrimaryKeySelective
     * @param row row
     * @return int
     */
    int updateByPrimaryKeySelective(RetApplyFactory row);

    /**
     * updateByPrimaryKey
     * @param row row
     * @return int
     */
    int updateByPrimaryKey(RetApplyFactory row);
}