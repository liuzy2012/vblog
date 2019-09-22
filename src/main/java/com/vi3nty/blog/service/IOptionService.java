package com.vi3nty.blog.service;

import com.vi3nty.blog.entity.Option;

import java.util.List;

public interface IOptionService {
    int updateAdminNotice(String notice);
    int updatePortalNotice(String notice);
    int updateSiteTitle(String title);
    int updateSiteDesc(String desc);
    List<Option> getAllOptions();
}
