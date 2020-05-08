package com.project.mapper;

import com.project.model.OutNode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutNodeMapper {
    List<OutNode> selectAllOutNode();
}