package com.brilliantreform.sc.utils;

import com.brilliantreform.sc.common.Column;
import com.brilliantreform.sc.common.Condition;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.Sort;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dtGrid插件工具类
 * Created by shangwq on 2015/11/2.
 */
public class GridUtil {

    /**
     * 将JSON对象映射为Pager对象
     * @param JSONObject 原JSON对象
     * @throws Exception
     */
    public static Pager toPager(JSONObject object) {
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("parameters", Map.class);
        classMap.put("fastQueryParameters", Map.class);
        classMap.put("advanceQueryConditions", Condition.class);
        classMap.put("advanceQuerySorts", Sort.class);
        classMap.put("exhibitDatas", Map.class);
        classMap.put("exportColumns", Column.class);
        classMap.put("exportDatas", Map.class);
        Pager pager = (Pager)JSONObject.toBean(object, Pager.class, classMap);
        return pager;
    }

    /**
     * 解析处理dtGird请求参数
     * */
    public static Map parseGridPager(Pager pager) throws Exception {
        Map param = new HashMap();
        if(pager != null) {
            Map<String,Object> m = pager.getParameters();	//查询参数
            param.putAll(m);
            param.put("begin", pager.getStartRecord());
            param.put("size", pager.getPageSize());
        }
        return param;
    }

    /**
     * 设置Pager的返回参数
     * */
    public static Pager setPagerResult(Pager pager, List list, int count, boolean isSuccess) {
        if(pager != null) {
            pager.setExhibitDatas(list);
            pager.setRecordCount(count);
            pager.setPageCount(CommonUtil.getPageCount(count, pager.getPageSize()));
            pager.setIsSuccess(isSuccess);
        }
        return pager;
    }

    public static Pager setPagerResult(Pager pager, List list, int count) {
        return setPagerResult(pager, list, count, true);
    }

    /**
     * 格式化日期
     * @param column
     * @param content
     * @return
     * @throws Exception
     */
    public static String formatContent(Column column, String content){
        try{
//			处理码表
            if(column.getCodeTable()!=null){
                if(column.getCodeTable().containsKey(content)){
                    return MapUtils.getString(column.getCodeTable(), content);
                }
            }
//			处理日期、数字的默认情况
            if("date".equalsIgnoreCase(column.getType())&&column.getFormat()!=null&&!"".equals(column.getFormat())){
                if(column.getOtype()!=null&&!"".equals(column.getOtype())){
                    if("time_stamp_s".equals(column.getOtype())){
                        SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                        Date date = new Date(Integer.parseInt(content)*1000);
                        return sdf.format(date);
                    }else if("time_stamp_ms".equals(column.getOtype())){
                        SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                        Date date = new Date(Integer.parseInt(content));
                        return sdf.format(date);
                    }else if("string".equals(column.getOtype())){
                        if(column.getOformat()!=null&&!"".equals(column.getOformat())){
                            SimpleDateFormat osdf = new SimpleDateFormat(column.getOformat());
                            SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
                            Date date = osdf.parse(content);
                            return sdf.format(date);
                        }
                    }
                }
            }else if("number".equalsIgnoreCase(column.getType())&&!"".equals(column.getFormat())){
                DecimalFormat df = new DecimalFormat(column.getFormat());
                content = df.format(Double.parseDouble(content));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return content;
    }
}
