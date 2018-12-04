package com.anxin.applicants.controller;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.anxin.applicants.domain.model.Applicants;
import com.anxin.common.annotation.Log;
import com.anxin.common.controller.BaseController;
import com.anxin.common.utils.*;
import com.anxin.common.webcrawler.Edu;
import com.anxin.common.webcrawler.WebCrawler;
import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.record.ContinueRecord;
import org.apache.poi.ss.formula.functions.Today;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anxin.applicants.domain.ApplicantsDO;
import com.anxin.applicants.service.ApplicantsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.anxin.common.utils.EasyPoiUtil.exportExcel;
import static com.anxin.common.utils.EasyPoiUtil.importExcel;

/**
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-15 17:09:07
 */

@Controller
@RequestMapping("/applicants/applicants")
public class ApplicantsController extends BaseController {
    @Autowired
    private ApplicantsService applicantsService;
    @Autowired
    private PostService postService;

    public static final String DOCTOR_NAME = "博士";
    public static final String YOUCAI_TYPE = "优才";
    //用于后台页面颜色区分标记
    public static final String COLOR_FLAG = "0";
    //学士
    public static final String EDU_XUESHI = "学士";

    public static final String EDU_BENKE = "本科";
    //硕士
    public static final String EDU_SUOSHI = "硕士";
    //博士
    public static final String EDU_BOSHI = "博士";

    /**
     * 动态导出Excel
     *
     * @param ids
     * @param texts
     * @param rowids
     * @param response
     */
    @RequestMapping("dtexport")
    public void dtexport(String ids, String texts, String rowids, HttpServletResponse response) {
        if (StringUtils.isEmpty(ids) || StringUtils.isEmpty(texts)) {
            logger.error("导出的列（IDS,TEXTS）为空");
            return;
        }
        //对行选择id筛选
        Map m = new HashMap<>(16);
        if (StringUtils.isNotBlank(rowids)) {
            m.put("rowids", Arrays.asList(rowids.split(",")));
        }
        //导出全部
        if (ids.contains("-1")) {
            //对行选择id筛选
            List<ApplicantsDO> listdo = applicantsService.exportlist(m);
            List<Applicants> list = new ArrayList<>();
            BeanCopier copier = BeanCopier.create(ApplicantsDO.class, Applicants.class, false);
            listdo.forEach(a -> {
                Applicants d = new Applicants();
                copier.copy(a, d, null);
                list.add(d);
            });
            exportExcel(list, "信息列表", "信息列表", Applicants.class, "信息列表.xls", true, response);
            return;
        }
        String[] id = ids.split(",");
        String[] text = texts.split(",");
        List<ExcelExportEntity> list = new ArrayList<>();
        addAttr(ids, id, text, list);
        List<Map<String, Object>> maplist = new ArrayList<>();
        Map<String, Object> mapSql = new HashMap<>(16);
        if (ids.contains("bachelorschool")) {
            ids = ids.replace("bachelorschool", "CONCAT(bachelorschool,'-',bachelormajor) as bachelorschool ");
        }
        if (ids.contains("masterschool")) {
            ids = ids.replace("masterschool", "CONCAT(masterschool,'-',mastermajor) as masterschool ");
        }
        if (ids.contains("doctorschool")) {
            ids = ids.replace("doctorschool", "CONCAT(doctorschool,'-',doctormajor) as doctorschool ");
        }
        if (ids.contains("interviewdate")) {
            ids = ids.replace("interviewdate", "(SELECT CONCAT(DATE_FORMAT(b.starttime,'%Y/%m/%d'),' - ',DATE_FORMAT(b.endtime,'%Y/%m/%d'))FROM ax_plan b WHERE b.id = a.interviewdate) as interviewdate ");
        }
        mapSql.put("collist", ids);
        putAttr(ids, maplist, mapSql, m);
        EasyPoiUtil.dtExport(list, "信息列表", "信息列表", "信息列表.xls", true, maplist, response);
    }


    /**
     * 给属性赋值
     *
     * @param ids
     * @param maplist
     * @param mapSql
     * @param m
     */
    private void putAttr(String ids, List<Map<String, Object>> maplist, Map<String, Object> mapSql, Map<String, Object> m) {
        List<ApplicantsDO> expotList;
        if (ids.contains("sumscore") || ids.contains("counttempuser")) {
            expotList = applicantsService.preadmissionLimit(m);
        } else {
            mapSql.put("rowids", m.get("rowids"));
            expotList = applicantsService.getExpotList(mapSql);
        }
        for (int i = 0; i < expotList.size(); i++) {
            ApplicantsDO a = expotList.get(i);
            if (expotList.get(i) == null) {
                continue;
            }
            //对mlist 循环添加动态列数据
            Map<String, Object> map = new HashMap<>(16);
            map.put("username", a.getUsername());
            map.put("sex", a.getSex());
            map.put("job", a.getJob());
            map.put("birthday", a.getBirthday());
            map.put("idnumber", a.getIdnumber());
//            map.put("type", a.getType());
            map.put("doctorschool", a.getDoctorschool());
            map.put("masterschool", a.getMasterschool());
            map.put("bachelorschool", a.getBachelorschool());
            map.put("graduationdate", a.getGraduationdate());
            map.put("languagelevel", a.getLanguagelevel());
            map.put("status", a.getStatus());
            map.put("phone", a.getPhone());
            map.put("mail", a.getMail());
            map.put("politics", a.getPolitics());
            map.put("education", a.getEducation());
            map.put("computerlevel", a.getComputerlevel());
            map.put("exemptionreason", a.getExemptionreason());
            map.put("englishscore", a.getEnglishscore());
            map.put("interviewresult", a.getInterviewresult());
            map.put("interviewdate", a.getInterviewdate());
            map.put("jobone", a.getJobone());
            map.put("jobtwo", a.getJobtwo());
            map.put("jobthree", a.getJobthree());
            map.put("resumeurl", a.getResumeurl());
            map.put("resumenum", a.getResumenum());
            map.put("remarks", a.getRemarks());
            map.put("sumscore", a.getSumscore());
            map.put("counttempuser", a.getCounttempuser());
            map.put("updateDate", a.getUpdateDate());
            maplist.add(map);
        }
    }

    private void addAttr(String ids, String[] id, String[] text, List<ExcelExportEntity> list) {
        if (ids.contains("username")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "username")], "username"));
        }
        if (ids.contains("sex")) {
            ExcelExportEntity secExcel = new ExcelExportEntity();
            secExcel.setReplace(new String[]{"男_1", "女_2"});
            secExcel.setName(text[ArrayUtils.indexOf(id, "sex")]);
            secExcel.setKey("sex");
            list.add(secExcel);
        }
        if (ids.contains("job")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "job")], "job"));
        }
        if (ids.contains("birthday")) {
            ExcelExportEntity birExcel = new ExcelExportEntity();
            birExcel.setFormat("yyyy/MM/dd");
            birExcel.setName(text[ArrayUtils.indexOf(id, "birthday")]);
            birExcel.setKey("birthday");
            list.add(birExcel);
        }
        if (ids.contains("idnumber")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "idnumber")], "idnumber"));
        }
//        if (ids.contains("type")) {
//            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "type")], "type"));
//        }
        if (ids.contains("doctorschool")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "doctorschool")], "doctorschool"));
        }
        if (ids.contains("masterschool")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "masterschool")], "masterschool"));
        }
        if (ids.contains("bachelorschool")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "bachelorschool")], "bachelorschool"));
        }
        if (ids.contains("graduationdate")) {
            ExcelExportEntity graExcel = new ExcelExportEntity();
            graExcel.setFormat("yyyy/MM/dd");
            graExcel.setName(text[ArrayUtils.indexOf(id, "graduationdate")]);
            graExcel.setKey("graduationdate");
            list.add(graExcel);
        }
        if (ids.contains("languagelevel")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "languagelevel")], "languagelevel"));
        }
        if (ids.contains("status")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "status")], "status"));
        }
        if (ids.contains("phone")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "phone")], "phone"));
        }
        if (ids.contains("mail")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "mail")], "mail"));
        }
        if (ids.contains("politics")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "politics")], "politics"));
        }
        if (ids.contains("education")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "education")], "education"));
        }
        if (ids.contains("computerlevel")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "computerlevel")], "computerlevel"));
        }
        if (ids.contains("exemptionreason")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "exemptionreason")], "exemptionreason"));
        }
        if (ids.contains("englishscore")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "englishscore")], "englishscore"));
        }
        if (ids.contains("interviewresult")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "interviewresult")], "interviewresult"));
        }
        if (ids.contains("interviewdate")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "interviewdate")], "interviewdate"));
        }
        if (ids.contains("jobone")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "jobone")], "jobone"));
        }
        if (ids.contains("jobtwo")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "jobtwo")], "jobtwo"));
        }
        if (ids.contains("jobthree")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "jobthree")], "jobthree"));
        }
        if (ids.contains("resumeurl")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "resumeurl")], "resumeurl"));
        }
        if (ids.contains("resumenum")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "resumenum")], "resumenum"));
        }
        if (ids.contains("remarks")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "remarks")], "remarks"));
        }
        if (ids.contains("sumscore")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "sumscore")], "sumscore"));
        }
        if (ids.contains("counttempuser")) {
            list.add(new ExcelExportEntity(text[ArrayUtils.indexOf(id, "counttempuser")], "counttempuser"));
        }
        if (ids.contains("updateDate")) {
            ExcelExportEntity dfExcel = new ExcelExportEntity();
            dfExcel.setFormat("yyyy-MM-dd HH:mm:ss");
            dfExcel.setName(text[ArrayUtils.indexOf(id, "updateDate")]);
            dfExcel.setKey("updateDate");
            list.add(dfExcel);
        }
    }


    private static final String EXCEL_NAME = "";

    /**
     * 导入英语成绩
     *
     * @param file
     * @return
     */
    @RequestMapping("importEnglish")
    @ResponseBody
    public R importEnglish(MultipartFile file) {
        if (null != file) {
            // 文件原名称
            String fname = file.getOriginalFilename();
            String sufname = fname.substring(fname.lastIndexOf(".") + 1);
            if (!("xls".equals(sufname) || "xlsx".equals(sufname) || "xlsm".equals(sufname) || "xlsb".equals(sufname))) {
                return R.error("请导入Excel工作簿文件");
            }
            List<Applicants> list = importExcel(file, 0, 1, Applicants.class);
            if (list.size() == 0) {
                return R.error("Excel信息为空");
            }
            long c = list.stream().filter(getNameAndScore()).count();
            if (c > 0) {
                return R.error("有 " + c + " 条人员必要信息为空，请补充完整后导入");
            }
            //判断人员重要信息是否为空
            //姓名重复的list
            List<Applicants> repeatList = new ArrayList<>();
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j).getUsername().equals(list.get(i).getUsername())) {
                        //把姓名重复的加入list
                        repeatList.add(list.get(j));
                        //删除重复元素
                        list.remove(j);
                    }
                }
            }
            //类拷贝
            BeanCopier copier = BeanCopier.create(Applicants.class, ApplicantsDO.class, false);
            //更新的条数
            final int[] updateCount = {0};
            list.forEach(a -> {
                ApplicantsDO d = new ApplicantsDO();
                copier.copy(a, d, null);
                d.setUpdateDate(new Date());
                int updbcount = applicantsService.updateScore(d);
                updateCount[0] += updbcount;
            });
            int count = repeatList.size();
            if (count > 0) {
                return R.error("上传【" + list.size() + "】条,共计匹配到【" + updateCount[0] + "】条人员信息,"
                        + "有【" + count + "】条重复信息失败");
            } else {
                return R.ok("上传【" + list.size() + "】条,共计匹配到【" + updateCount[0] + "】条人员信息");
            }
        }
        return R.error("Excel文件为空,请重新选择");
    }

    /**
     * 导入概要信息
     *
     * @param file
     * @return
     */
    @RequestMapping("excelImport")
    @ResponseBody
    public R excelimport(MultipartFile file) {
        if (null != file) {
            // 文件原名称
            String fname = file.getOriginalFilename();
            String sufname = fname.substring(fname.lastIndexOf(".") + 1);
            if (!("xls".equals(sufname) || "xlsx".equals(sufname) || "xlsm".equals(sufname) || "xlsb".equals(sufname))) {
                return R.error("请导入Excel工作簿文件");
            }
            List<Applicants> list = importExcel(file, 0, 1, Applicants.class);
            if (list.size() == 0) {
                return R.error("Excel信息为空");
            }
            //判断人员重要信息是否为空
            long count = list.stream().filter(getApplicantsPredicate()).count();
            if (count > 0) {
                return R.error("有 " + count + " 条人员必要信息为空，请补充完整后导入");
            } else {
                //根据（姓名和身份证号相同+岗位名称不同）
                setFlag(list);
                //删除数据库重复数据
                removeList(list);
                //类拷贝
                BeanCopier copier = BeanCopier.create(Applicants.class, ApplicantsDO.class, false);
                Vector<Thread> threads = new Vector<>();
                List<ApplicantsDO> mydblist = applicantsService.list(null);
                list.forEach(a -> {
                    //开启子线程
                    Thread t = new Thread(() -> {
                        ApplicantsDO d = new ApplicantsDO();
                        copier.copy(a, d, null);
                        //简历序号
                        d.setResumenum(a.getResumeurl().split("=")[1].toString());
                        subJobString(a, d);
                        //本科，研究生，博士信息
                        getEduInfo(a, d);
                        //设置优才(免试原因不是空并且不是博士都是优才)
                        if (StringUtils.isNotBlank(d.getExemptionreason())) {
                            if (!d.getExemptionreason().contains(DOCTOR_NAME)) {
                                if (!d.getJob().equals(YOUCAI_TYPE)) {
                                    d.setType(d.getJob());
                                }
                                d.setJob(YOUCAI_TYPE);
                            }
                        }
                        //单个人是优才的取两个集合的交集并覆盖数据库的单个人是优才的这条数据
                        for (int i = 0; i < mydblist.size(); i++) {
                            if (mydblist.contains(d) && mydblist.get(i).getInterviewresult() == null) {
                                applicantsService.remove(mydblist.get(i).getId());
                            }
                        }
                        applicantsService.save(d);
                    });
                    threads.add(t);
                    t.start();
                });
                threads.forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return R.ok("导入" + fname + "成功,共计【" + list.size() + "】行");
            }
        }
        return R.error("Excel文件为空,请重新选择");
    }

    /**
     * 根据（姓名和身份证号相同+岗位名称不同）设置标记
     *
     * @param list
     */
    private void setFlag(List<Applicants> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getUsername().equals(list.get(i).getUsername())
                        && list.get(j).getIdnumber().equals(list.get(i).getIdnumber())
                        && !list.get(j).getJob().equals(list.get(i).getJob())) {
                    list.get(i).setFlag(COLOR_FLAG);
                    list.get(j).setFlag(COLOR_FLAG);
                    //如果是优才(免试原因不是空并且不是博士都是优才),设置优才
                    if (StringUtils.isNotBlank(list.get(i).getExemptionreason()) || StringUtils.isNotBlank(list.get(j).getExemptionreason())) {
                        if (StringUtils.isNotBlank(list.get(i).getExemptionreason())) {
                            if (!list.get(i).getExemptionreason().contains(DOCTOR_NAME)) {
                                list.get(i).setType(list.get(i).getJob() + "," + list.get(j).getJob());
                                list.get(i).setJob(YOUCAI_TYPE);
                                list.get(i).setFlag("");
                                list.remove(list.get(j));
                            }
                        }
//                        if (StringUtils.isNotBlank(list.get(j).getExemptionreason())) {
//                            if (!list.get(j).getExemptionreason().contains(DOCTOR_NAME)) {
//                                list.get(j).setType(list.get(i).getJob() + "," + list.get(j).getJob());
//                                list.get(j).setJob(YOUCAI_TYPE);
//                                list.get(j).setFlag("");
//                                list.remove(list.get(i));
//                            }
//                        }
                    }


                }
            }
        }
    }

    //删除数据库重复数据
    private void removeList(List<Applicants> list) {
        //查询数据库的人员信息列表
        List<ApplicantsDO> dblist = applicantsService.list(null);
        BeanCopier dbcopier = BeanCopier.create(ApplicantsDO.class, Applicants.class, false);
        //创建空的excel人员信息集合
        List<Applicants> newapplist = new ArrayList<>();
        if (ValidateHelper.isNotEmptyCollection(dblist)) {
            dblist.forEach(a -> {
                Applicants app = new Applicants();
                dbcopier.copy(a, app, null);
                newapplist.add(app);
            });
            //取两个集合的交集并覆盖数据库的
            for (int i = 0; i < newapplist.size(); i++) {
                if (list.contains(newapplist.get(i))) {
                    applicantsService.remove(newapplist.get(i).getId());
                }
            }
        }

    }

    /**
     * 岗位名称字符串裁剪
     *
     * @param a
     * @param d
     */
    private void subJobString(Applicants a, ApplicantsDO d) {
        if (StringUtils.isNotBlank(a.getJobone())) {
            String[] j = a.getJobone().split("—");
            d.setJobone(j[0] + "—" + j[1]);
        }
        if (StringUtils.isNotBlank(a.getJobtwo())) {
            String[] j = a.getJobtwo().split("—");
            d.setJobtwo(j[0] + "—" + j[1]);
        }
        if (StringUtils.isNotBlank(a.getJobthree())) {
            String[] j = a.getJobthree().split("—");
            d.setJobthree(j[0] + "—" + j[1]);
        }
    }

    /**
     * 爬虫获取学校，专业信息
     *
     * @param a
     * @param d
     */
    private void getEduInfo(Applicants a, ApplicantsDO d) {
        List<Edu> edulist = WebCrawler.getEduList(a.getResumeurl());
        List<Edu> xueshilist = edulist.stream().filter(edu -> edu.getDegree().contains(EDU_XUESHI)||edu.getEduca().contains(EDU_BENKE)).collect(Collectors.toList());
        List<Edu> suoshilist = edulist.stream().filter(edu -> edu.getDegree().contains(EDU_SUOSHI)||edu.getEduca().contains(EDU_SUOSHI)).collect(Collectors.toList());
        List<Edu> boshilist = edulist.stream().filter(edu -> edu.getDegree().contains(EDU_BOSHI)||edu.getEduca().contains(EDU_BOSHI)).collect(Collectors.toList());
        //本科
        if (xueshilist.size() > 0) {
            String bschool = xueshilist.get(0).getSchool();
            if (xueshilist.size() > 1) {
                if (!bschool.equals(xueshilist.get(1).getSchool())) {
                    bschool += "/" + xueshilist.get(1).getSchool();
                }
            }
            d.setBachelorschool(bschool);
            String bprofess = xueshilist.get(0).getProfession().split("：")[1].toString();
            if (xueshilist.size() > 1) {
                bprofess += "/" + xueshilist.get(1).getProfession().split("：")[1].toString();
            }
            d.setBachelormajor(bprofess);
        }


        //研究生
        if (suoshilist.size() > 0) {
            String sschool = suoshilist.get(0).getSchool();
            if (suoshilist.size() > 1) {
                if (!sschool.equals(suoshilist.get(1).getSchool())) {
                    sschool += "/" + suoshilist.get(1).getSchool();
                }
            }
            d.setMasterschool(sschool);
            String sprofess = suoshilist.get(0).getProfession().split("：")[1].toString();
            if (suoshilist.size() > 1) {
                sprofess += "/" + suoshilist.get(1).getProfession().split("：")[1].toString();
            }
            d.setMastermajor(sprofess);
        }

        //博士
        if (boshilist.size() > 0) {
            String dschool = boshilist.get(0).getSchool();
            if (boshilist.size() > 1) {
                if (!dschool.equals(boshilist.get(1).getSchool())) {
                    dschool += "/" + boshilist.get(1).getSchool();
                }
            }
            d.setDoctorschool(dschool);
            String dprofess = boshilist.get(0).getProfession().split("：")[1].toString();
            if (boshilist.size() > 1) {
                dprofess += "/" + boshilist.get(1).getProfession().split("：")[1].toString();
            }
            d.setDoctormajor(dprofess);
        }

    }

    /**
     * 判断指定属性是否为空
     *
     * @return
     */
    private Predicate<Applicants> getApplicantsPredicate() {
        return a -> a.getUsername() == null || a.getSex() == null
                || a.getJob() == null || a.getBirthday() == null || a.getIdnumber() == null
                || a.getPhone() == null || a.getResumeurl() == null;
    }

    /**
     * 判断指定属性是否为空
     * （只判断姓名和英语成绩）
     *
     * @return
     */
    private Predicate<Applicants> getNameAndScore() {
        return a -> StringUtils.isBlank(a.getUsername())
                || StringUtils.isBlank(a.getEnglishscore());
    }


    @GetMapping()
    @RequiresPermissions("applicants:applicants:applicants")
    String Applicants() {
        return "applicants/applicants/applicants";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("applicants:applicants:applicants")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ApplicantsDO> applicantsList = applicantsService.list(query);
        int total = applicantsService.count(query);
        PageUtils pageUtils = new PageUtils(applicantsList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("applicants:applicants:add")
    String add(Model model) {
        Map<String, Object> param = new HashMap();
        param.put("status", "1");
        List<PostDO> jobs = postService.list(param);
        model.addAttribute("jobs", jobs);
        return "applicants/applicants/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("applicants:applicants:edit")
    String edit(@PathVariable("id") String id, Model model) {
        Map<String, Object> param = new HashMap();
        param.put("status", "1");
        List<PostDO> jobs = postService.list(param);
        model.addAttribute("jobs", jobs);
        ApplicantsDO applicants = applicantsService.get(id);
        model.addAttribute("applicants", applicants);
        return "applicants/applicants/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("applicants:applicants:add")
    public R save(ApplicantsDO applicants) {
        if (applicantsService.save(applicants) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("applicants:applicants:edit")
    public R update(ApplicantsDO applicants) {
        applicantsService.update(applicants);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("applicants:applicants:remove")
    public R remove(String id) {
        if (applicantsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("applicants:applicants:batchRemove")
    public R remove(@RequestParam("ids[]") String[] ids) {
        applicantsService.batchRemove(ids);
        return R.ok();
    }

}
