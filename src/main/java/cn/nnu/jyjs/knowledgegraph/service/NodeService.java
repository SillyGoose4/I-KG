package cn.nnu.jyjs.knowledgegraph.service;

import cn.nnu.jyjs.knowledgegraph.domain.Node;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RELATION;
import cn.nnu.jyjs.knowledgegraph.domain.roles.Role;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleDirect;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleIn;
import cn.nnu.jyjs.knowledgegraph.domain.roles.RoleOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Entity常用操作
 * Created by wangj
 * Created in 5/12/2019
 */
@Service
public class NodeService {

    @Autowired
    private NodeRepository nodeRepo;

    /*
    public NodeService(NodeRepository repo){
        this.nodeRepo = repo;
    }*/

    public Node findByName(String name){
        return nodeRepo.findByNatureStr(name);
    }

    /**
     * 保存
     * @param node
     * @return
     */
    @Transactional
    public boolean saved(Node node){
        if(findByName(node.getNatureStr()) == null){
            nodeRepo.save(node);
            return true;
        }
        return false;
    }

    @Transactional
    public void addRela(Node start, Node end, String role){

    }

    /**
     * 更新，先删了在存
     * @param node
     * @return
     */
    public boolean update(Node node){
        Node t = null;
        if((t=findByName(node.getNatureStr())) != null){
            nodeRepo.delete(t);
            nodeRepo.save(node);
            return true;
        }else
            nodeRepo.save(node);
        return false;
    }

    /**
     * 添加关系，有可能出现没有实体，先添加实体在做
     * @param start
     * @param end
     * @param relationship
     * @return
     */
    public boolean addLinked(Node start, Node end, String relationship){
        Role roleA = null;  //给start添加关系
        String reA = "";

        RELATION relation = RELATION.fromName(relationship);
        switch (relation){
            case anto://反义
            case simi://相似
            case syno://同义
            case other://其他
            case appo://同位
                roleA = new RoleDirect(start,end,relationship);
                reA = "DIRECT";
                break;
            case attr://属性 start->end
            case belong://属于 start->end
            case suffcient://充分条件
            case rely://依赖 start->end
                roleA = new RoleIn(start,end,relationship);
                reA = "IN";
                break;
            case b_attr:
            case b_rely:
            case b_belong:
            case essential:
                roleA = new RoleOut(start,end,relationship);
                reA = "OUT";
                break;
                default:
                    return false;
        }
        roleA.TYPE = relationship;
        start.addRelationship(end,reA,roleA);
        nodeRepo.save(start);
        return true;
    }



}
