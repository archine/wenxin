package com.aj.wenxin.repository;

import com.aj.wenxin.entity.ChatRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * @author colin
 * @date 2019-03-11
 **/
public interface ChatRecordDao extends BaseMapper<ChatRecord> {
    boolean updateChatRecord(List<Long> msgIdList);
}
