package com.vi3nty.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤
 * @author : vi3nty
 * @date : 17:29 2019/9/28
 */
@Component
public class SensitiveFilter {
    private static final Logger LOGGER=LoggerFactory.getLogger(SensitiveFilter.class);
    //敏感词替换
    private static final String REPLACEELE="***";
    //根节点
    private TrieNode rootNode=new TrieNode();
    @PostConstruct
    public void init(){
        try(
                InputStream in=this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                ) {
            String keyword;
            while ((keyword=reader.readLine())!=null){
                //添加到前缀树
                addKeyword(keyword);
            }
        }
        catch (Exception e){
            LOGGER.error("敏感词文件加载失败");
        }
    }
    //讲一个敏感词添加到前缀树中
    private void addKeyword(String keyword){
        TrieNode tempNode=rootNode;
        for(int i=0;i<keyword.length();i++){
            char c=keyword.charAt(i);
            TrieNode subNode=tempNode.getSubNode(c);
            if(subNode==null){
                //初始化子节点
                subNode=new TrieNode();
                //将子节点加到该节点上
                tempNode.addSubNode(c,subNode);
            }

            //将指针指向子节点，指向下一轮循环
            tempNode=subNode;

            //设置结束标识
            if(i==keyword.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text))
            return null;
        //根节点
        TrieNode tempNode=rootNode;
        //指向字符串的首部
        int begin=0;
        //指向敏感词的检索的尾部
        int pos=0;
        //结果
        StringBuilder sb=new StringBuilder();
        while (pos<text.length()){
            char c=text.charAt(pos);
            tempNode=tempNode.getSubNode(c);
            //当前节点不在敏感词范围
            if(tempNode==null){
                sb.append(text.charAt(begin));
                pos=++begin;
                //指向根节点
                tempNode=rootNode;
            }
            else if(tempNode.isKeyWordEnd()){
                //发现敏感词 begin~pos
                sb.append(REPLACEELE);
                begin=++pos;
                //指向根节点
                tempNode=rootNode;
            }else {
                pos++;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();
    }
    //定义前缀树
    private class TrieNode{
        //关键词结束标志
        private boolean isKeyWordEnd;

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        //定义节点的子节点，因为是前缀树，子节点可能有多个
        private Map<Character,TrieNode> subNodes=new HashMap<>();

        //添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
