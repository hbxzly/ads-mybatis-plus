package com.hbx.ads.controller;


import com.hbx.ads.domain.Account;
import com.hbx.ads.domain.ActiveUser;
import com.hbx.ads.domain.AdOperation;
import com.hbx.ads.util.EUDataGridResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("adOperation")
public class AdOperationController {

    @Autowired


    /**
     * 跳转到代投记录列表页面
     * @return
     */
    @RequestMapping("/list")
    public String goAdOperationList(){
        return "ad_operation_list";
    }


    /**
     * 跳转到代投编辑页面
     * @return
     */
    @RequestMapping("/edit")
    public String editAdOperation(){
        return "ad_operation_edit";
    }

    /**
     * 跳转到添加代投记录页面
     * @return
     */
    @RequestMapping("/add")
    public String addAdOperation(){
        return "ad_operation_add";
    }

    /**
     * 根据用户身份、查询条件查询代投记录(单个查询）
     * @param page
     * @param rows
     * @param adOperation
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult getAdOperationList(Integer page, Integer rows, AdOperation adOperation) {


        return null;
    }

    /**
     * 批量查询代投记录
     * @param page
     * @param rows
     * @param idList
     * @return
     */
    @RequestMapping("findByIdList")
    @ResponseBody
    public EUDataGridResult getAdOperationListByIdList(Integer page, Integer rows, String[] idList){



        return null;
    }


    /**
     * 添加代投记录
     * @param adOperation
     * @return
     */
    @RequestMapping("/toAdd")
    @ResponseBody
    public String toAddOperation(@RequestBody AdOperation adOperation){
        return "OK";
    }

    /**
     * 编辑代投记录
     * @param adOperation
     * @return
     */
    @RequestMapping("/editAdOperation")
    @ResponseBody
    public String updateAccount(@RequestBody AdOperation adOperation){


        return "OK";
    }


}
