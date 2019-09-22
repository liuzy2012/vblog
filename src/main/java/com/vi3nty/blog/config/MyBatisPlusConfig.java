package com.vi3nty.blog.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @author : vi3nty
 * @date : 20:40 2019/9/18
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.vi3nty.tmall_springboot.mapper.*")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor=new PaginationInterceptor();

        return paginationInterceptor;
    }
}
