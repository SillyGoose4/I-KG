package cn.nnu.jyjs.knowledgegraph.controller;

import cn.nnu.jyjs.knowledgegraph.domain.Graph;
import cn.nnu.jyjs.knowledgegraph.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangj
 * Created in 5/12/2019
 * email    wangjiaruo22@hotmail.com
 */
@RestController
@RequestMapping("/entity")
public class GraphController {

    @Autowired
    NodeService nodeService;

    /**
     * 根据项查询关联内容
     * @param entity 起始点
     * @param deep  深度，默认深度为1
     * @param method dfs/bfs
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getItems(@RequestParam(value = "item") String entity,
                           @RequestParam(value = "deep",required = false)Integer deep,
                           @RequestParam(value = "method",required = false) String method){

        return null;
    }

}
