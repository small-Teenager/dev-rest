package com.dev.rest.controller.apply;

import com.dev.rest.common.utils.DateUtil8;
import com.dev.rest.common.utils.GeneratePhoneNumber;
import com.dev.rest.common.utils.IdWorker;
import com.dev.rest.dto.AddBlackDTO;
import com.dev.rest.entity.Black;
import com.dev.rest.utils.excel.bean.BlackUserBean;
import com.dev.rest.utils.excel.writer.ExportModule;
import com.dev.rest.utils.excel.writer.module.PoiELModule;
import com.dev.rest.vo.BlackUserVO;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.BlackService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 初始化黑名单
     * @return
     */
    @PostMapping("/init")
    public ApiResponse<Boolean> init() {
        Boolean result = blackService.init();
        return ApiResponse.success(result);
    }

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

    @GetMapping("/detail/{mobile}")
    public ApiResponse<Black> detail(@Valid @PathVariable(value = "mobile") @NotNull String mobile) {
        Black result = blackService.selectByMobile(mobile);
        return ApiResponse.success(result);
    }

    @GetMapping("/all")
    public ApiResponse<List<String>> all() {
        List<String> result = blackService.selectAll();
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


    @GetMapping("/list")
    public ApiResponse<PageInfo<Black>> selectPage(Black black, @RequestParam(value = "page" ,defaultValue = "1")  int page, @RequestParam(value = "size",defaultValue = "10") int size) {
        PageInfo<Black> result = blackService.selectPage(black, page, size);
        return ApiResponse.success(result);
    }

    @GetMapping("/batchInsert")
    public ApiResponse<Integer> batchInsert() {

        List<Black> blackList = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            Black black = new Black();
            black.setId(idWorker.nextId());
            black.setMobile(GeneratePhoneNumber.generatePhoneNumber());
            blackList.add(black);
        }
        long start = System.currentTimeMillis();
        int result = blackService.batchInsert(blackList);
        long end = System.currentTimeMillis();
        System.err.println("time consuming1:" + (end - start));
        // time consuming1:4635

        // Batch 模式插入 BATCH 会批量执行所有更新语句，不需要对同样的SQL进行多次预编译

        //按每10000 个一组分割
        Integer MAX_SEND = 10000;
        //计算切分次数
        int limit = (blackList.size() + MAX_SEND - 1) / MAX_SEND;
        // 将blackList 拆分成多个
        List<List<Black>> mglist = new ArrayList<>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            mglist.add(blackList.stream().skip(i * MAX_SEND).limit(MAX_SEND).collect(Collectors.toList()));
        });

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        start = System.currentTimeMillis();
        try {
            for (int i = 0; i < mglist.size(); i++) {
                blackService.batchInsert(mglist.get(i));
            }
            session.commit();
        } catch (Exception e) {
            log.error("message:",e);
            session.rollback();
        } finally {
            session.close();
        }
        end = System.currentTimeMillis();
        System.err.println("time consuming2:" + (end - start));
        // time consuming2:744
        return ApiResponse.success(result);
    }

}
