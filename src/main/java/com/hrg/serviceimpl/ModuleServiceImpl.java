package com.hrg.serviceimpl;

import com.hrg.javamapper.read.ModuleReadMapper;
import com.hrg.model.Module;
import com.hrg.model.ModuleCriteria;
import com.hrg.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 82705 on 2017/6/15.
 */
@Service("moduleServiceImpl")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleReadMapper moduleReadMapper;

    @Override
    public List<Module> selectList(ModuleCriteria example) throws Exception {

        return moduleReadMapper.selectByExample(example);
    }
}
