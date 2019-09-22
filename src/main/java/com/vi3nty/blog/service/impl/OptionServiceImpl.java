package com.vi3nty.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vi3nty.blog.entity.Option;
import com.vi3nty.blog.mapper.OptionMapper;
import com.vi3nty.blog.service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements IOptionService {
    @Autowired
    private OptionMapper optionMapper;
    @Override
    public int updateAdminNotice(String notice) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",1);
        Option option=new Option(notice);
        return optionMapper.update(option,wrapper);
    }

    @Override
    public int updatePortalNotice(String notice) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",2);
        Option option=new Option(notice);
        return optionMapper.update(option,wrapper);
    }

    @Override
    public int updateSiteTitle(String title) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",3);
        Option option=new Option(title);
        return optionMapper.update(option,wrapper);
    }

    @Override
    public int updateSiteDesc(String desc) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",4);
        Option option=new Option(desc);
        return optionMapper.update(option,wrapper);
    }

    @Override
    public List<Option> getAllOptions() {
        return optionMapper.selectList(null);
    }
}
