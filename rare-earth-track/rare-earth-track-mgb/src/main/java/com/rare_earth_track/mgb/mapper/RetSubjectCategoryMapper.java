package com.rare_earth_track.mgb.mapper;

import com.rare_earth_track.mgb.model.RetSubjectCategory;
import com.rare_earth_track.mgb.model.RetSubjectCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RetSubjectCategoryMapper {
    /**
     * countByExample
     * @param example example
     * @return long
     */
    long countByExample(RetSubjectCategoryExample example);

    /**
     * deleteByExample
     * @param example example
     * @return int
     */
    int deleteByExample(RetSubjectCategoryExample example);

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
    int insert(RetSubjectCategory row);

    /**
     * insertSelective
     * @param row row
     * @return int
     */
    int insertSelective(RetSubjectCategory row);

    /**
     * selectByExample
     * @param example example
     * @return java.util.List<com.rare_earth_track.mgb.model.RetSubjectCategory>
     */
    List<RetSubjectCategory> selectByExample(RetSubjectCategoryExample example);

    /**
     * selectByPrimaryKey
     * @param id id
     * @return com.rare_earth_track.mgb.model.RetSubjectCategory
     */
    RetSubjectCategory selectByPrimaryKey(Long id);

    /**
     * updateByExampleSelective
     * @param row row
     * @param example example
     * @return int
     */
    int updateByExampleSelective(@Param("row") RetSubjectCategory row, @Param("example") RetSubjectCategoryExample example);

    /**
     * updateByExample
     * @param row row
     * @param example example
     * @return int
     */
    int updateByExample(@Param("row") RetSubjectCategory row, @Param("example") RetSubjectCategoryExample example);

    /**
     * updateByPrimaryKeySelective
     * @param row row
     * @return int
     */
    int updateByPrimaryKeySelective(RetSubjectCategory row);

    /**
     * updateByPrimaryKey
     * @param row row
     * @return int
     */
    int updateByPrimaryKey(RetSubjectCategory row);
}