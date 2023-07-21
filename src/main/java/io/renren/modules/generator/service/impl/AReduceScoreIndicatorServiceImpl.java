package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.AReduceScoreIndicatorDao;
import io.renren.modules.generator.entity.AReduceScoreIndicatorEntity;
import io.renren.modules.generator.service.AReduceScoreIndicatorService;


@Service("aReduceScoreIndicatorService")
public class AReduceScoreIndicatorServiceImpl extends ServiceImpl<AReduceScoreIndicatorDao, AReduceScoreIndicatorEntity> implements AReduceScoreIndicatorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AReduceScoreIndicatorEntity> page = this.page(
                new Query<AReduceScoreIndicatorEntity>().getPage(params),
                new QueryWrapper<AReduceScoreIndicatorEntity>()
        );

        return new PageUtils(page);
    }

}