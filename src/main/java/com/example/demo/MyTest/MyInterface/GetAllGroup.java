package com.example.demo.MyTest.MyInterface;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Entity.Group;
import com.example.demo.MyData.Entity.Member;
import com.example.demo.utils.MyPrint;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static sun.misc.Version.print;
@Transactional
public class GetAllGroup {
    static DaoFactory daoFactory;
    public static  void main(String[] s) throws IOException {
        daoFactory=new DaoFactory();
        String a="#gag"+"1";
//        List<Member>  groups=daoFactory.getMemberDao().getAllMember(1);

        Group group=daoFactory.getGroupDao().getGroupById(daoFactory.getGroupDao().getLastId());
        Member member=new Member();
        member.setGroupName("group7");
        daoFactory.getGroupDao().addGroup(member.getGroupName());
        member.setGroupId(daoFactory.getGroupDao().getLastId());
        member.setUserId(1);
        member.setUmsg(0);
        member.setUserName("xiaoming");
        daoFactory.getMemberDao().addGroup(member);
        daoFactory.getGroupDao().updateMembership(member.getGroupId());
        System.out.println(daoFactory.getGroupDao().getGroupById(daoFactory.getGroupDao().getLastId()).getGroupName());
        daoFactory.getSqlSession().commit();
    }
}
