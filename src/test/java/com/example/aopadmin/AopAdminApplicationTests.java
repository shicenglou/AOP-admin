package com.example.aopadmin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.service.EnvironmentRecordService;
import com.example.aopadmin.util.NumEx;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
class AopAdminApplicationTests {

    @Autowired
    private EnvironmentRecordService environmentRecordService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testDate() throws ParseException {
        String dataStr = "2022-06-17 14:23:33";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] s = dataStr.split(" ");
        System.out.println(s[s.length-1]);
        String day = s[0]+ " 21:00:00";
        Date dayP = dateFormat.parse(day);
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(dayP);
        endTime.setTime(dayP);

        for (int i = 1;i<60;i++){
            EnvironmentRecord environmentRecord = new EnvironmentRecord();
            environmentRecord.setId(UUID.randomUUID().toString().replace("-",""));
            Date startDate = addMinute(dayP, i - 1);
            Date endDate = addMinute(dayP, i);
            environmentRecord.setTime(startDate);
            environmentRecord.setUpdateTime(startDate);
            List<EnvironmentRecord> list = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                    .ge(EnvironmentRecord::getUpdateTime, startDate)
                    .lt(EnvironmentRecord::getUpdateTime, endDate)
                    .orderByAsc(EnvironmentRecord::getUpdateTime));
            System.out.println("当前时间："+startDate);
            if (!list.isEmpty()){
                List<Double> humList = list.stream().filter(item -> StringUtils.isNotBlank(item.getHum())).map(item->Double.parseDouble(item.getHum())).collect(Collectors.toList());
                List<Double> lightList = list.stream().filter(item -> StringUtils.isNotBlank(item.getLight())).map(item->Double.parseDouble(item.getLight())).collect(Collectors.toList());
                List<Double> tempList = list.stream().filter(item -> StringUtils.isNotBlank(item.getTemp())).map(item->Double.parseDouble(item.getTemp())).collect(Collectors.toList());
                List<Double> co2List = list.stream().filter(item -> StringUtils.isNotBlank(item.getCo2())).map(item->Double.parseDouble(item.getCo2())).collect(Collectors.toList());
                environmentRecord.setBeep(StringUtils.isNotBlank(list.get(0).getBeep()) ? list.get(0).getBeep() : "-1");
                environmentRecord.setHum(String.format("%.2f",avgDouble(humList)));
                environmentRecord.setCo2(String.format("%.2f",avgDouble(co2List)));
                environmentRecord.setLight(String.format("%.2f",avgDouble(lightList)));
                environmentRecord.setTemp(String.format("%.2f",avgDouble(tempList)));
                environmentRecord.setLed(StringUtils.isNotBlank(list.get(0).getLed()) ? list.get(0).getLed() : "-1");
            }else {
                environmentRecord.setBeep("-1");
                environmentRecord.setCo2("-1");
                environmentRecord.setLight("-1");
                environmentRecord.setTemp("-1");
                environmentRecord.setHum("-1");
                environmentRecord.setLed("-1");
            }
            System.out.println("当前时间获取的环境参数是："+environmentRecord);
            System.out.println("结束时间："+endDate);

        }
    }

    public Double avgDouble(List<Double> list){
        if (list.isEmpty()){
            return -1.0;
        }else {
            Double len = Double.valueOf(list.size());
            Double num = 0.0;
            for (Double item:list){
                num = num + item;
            }
            return num/len;
        }
    }


    /**
     * 将日期加指定分钟
     * @param date          传入日期
     * @param min           增加多少分钟
     * @return              返回一个指定日期
     * @throws ParseException
     */
    public Date addMinute(Date date,int min) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        String substring = format.substring(14, 16);
        int i = Integer.parseInt(substring);
        String newMin = Integer.toString(i + min);
        if (newMin.length()<2){
            newMin = "0"+newMin;
        }
        StringBuilder stringBuilder = new StringBuilder(format);
        stringBuilder.replace(14,16,newMin);
        String s = stringBuilder.toString();
        return dateFormat.parse(s);
    }
    @Test
    public void test22() throws ParseException {
        String datestr = "2021-03-22 14:56:22";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = dateFormat.parse(datestr);
        for (int i = 0;i<100;i++){
            System.out.println(addMinute(parse,i));
        }

    }

    @Test
    public void test23(){
        System.out.println(NumEx.isNumber("asd"));
        System.out.println(NumEx.isNumber("1.2"));
        System.out.println(NumEx.isNumber("2"));
        System.out.println(NumEx.isNumber("3"));
        System.out.println(NumEx.isNumber("3.0"));
        System.out.println(NumEx.isNumber("-1"));
        System.out.println(NumEx.isNumber("-1.0"));
        System.out.println(NumEx.isNumber("0"));
        System.out.println(NumEx.isNumber("0.0"));
    }


}
