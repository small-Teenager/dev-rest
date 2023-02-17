package com.redis.rest.controller.apply;

import com.redis.rest.utils.excel.bean.BlackUserBean;
import com.redis.rest.dto.AddBlackDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.BlackService;
import com.redis.rest.utils.DateUtil8;
import com.redis.rest.utils.excel.writer.ExportModule;
import com.redis.rest.utils.excel.writer.module.PoiELModule;
import com.redis.rest.vo.BlackUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单 set
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@RestController
@RequestMapping("/apply/black")
public class BlackController {

    private static final Logger log = LoggerFactory.getLogger(BlackController.class);
    @Autowired
    private BlackService blackService;

    /**
     * 加入黑名单
     * @param record
     * @return
     */
    @PostMapping("")
    public ApiResponse<Boolean> addBlack(@Validated @RequestBody AddBlackDTO record) {
        Boolean result = blackService.addBlack(record);
        return ApiResponse.success(result);
    }

    /**
     * 是否黑名单用户
     * @param mobile
     * @return
     */
    @GetMapping("/{mobile}")
    public ApiResponse<Boolean> isBlack(@Valid @PathVariable(value = "mobile") @NotNull String mobile) {
        Boolean result = blackService.isBlack(mobile);
        return ApiResponse.success(result);
    }

    /**
     * 移出黑名单
     * @param mobile
     * @return
     */
    @DeleteMapping("/{mobile}")
    public ApiResponse<Boolean> removeBlack(@PathVariable(value = "mobile") @NotNull String mobile) {
        Boolean result = blackService.removeBlack(mobile);
        return ApiResponse.success(result);
    }

    /**
     * 导出黑名单列表
     */
    @GetMapping()
    @ResponseBody
    public void exportAgentAdvertiseOrderBlacklist(HttpServletRequest request, HttpServletResponse response){
        //导出1000条数据即可
//       造数据
        List<BlackUserVO> blackUserList=new ArrayList<>(1000);
        BlackUserVO blackUser = null;
        for (int i = 0; i < 1000; i++) {
            blackUser= new BlackUserVO();
            blackUser.setId(String.valueOf(i));
            blackUser.setOperator("张三"+i);
            blackUser.setUserInfo("李四"+i);
            blackUser.setCreateAt(LocalDateTime.now());
            blackUserList.add(blackUser);
        }

        try {
            // 开始导出
            PoiELModule module = new PoiELModule("black_user", ExportModule.FORMAT_XLSX);
            response.setContentType("application/vnd.ms-excel");

            StringBuffer fileName = new StringBuffer("导出黑名单").append(DateUtil8.format(LocalDateTime.now(),DateUtil8.YYYYMMDDHHMMSS));
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0 || request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT") > 0) {
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode((new String(fileName)), "utf-8") + ".xlsx\"");
            }
            else {
                response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.toString().getBytes("utf-8"), "ISO-8859-1") + ".xlsx\"");
            }
            BlackUserBean bean = new BlackUserBean();
            bean.setBlackUserList(blackUserList);
            module.export(bean, ExportModule.FORMAT_XLSX, response.getOutputStream());
        }
        catch (Exception e) {
            log.error("模板解析错误", e);
        }
    }

}
