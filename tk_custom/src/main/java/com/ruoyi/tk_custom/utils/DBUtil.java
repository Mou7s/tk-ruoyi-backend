package com.ruoyi.tk_custom.utils;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DBUtil {
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getconnection(String str){
        Connection conn =null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.95:1433;DatabaseName="+str+"","sa","qwe123.com");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static Connection getconnection38(String str){
        Connection conn =null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.38:1433;DatabaseName="+str+"","sa","qwe123.com");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static Connection getconnection(){
        return getconnection("AIS20240930182655");
    }
    public static void close(ResultSet rs, Statement stat, Connection conn){
        try{
            if(rs!=null)
                rs.close();
            if(stat!=null)
                stat.close();
            if(conn!=null)
                conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static List convertList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
                list.add(rowData);

            }
        }
        return list;
    }
    //更新所有的结案标识
    public static void updateJieAnFlag(String id){
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql="UPDATE  T_PRD_MOENTRY set F_ISEND ='1' from  T_PRD_MOENTRY\n" +
                    "left join T_PRD_MO on T_PRD_MO.FID =T_PRD_MOENTRY.FID \n" +
                    "where T_PRD_MOENTRY.F_ISEND ='' and \n" +
                    "exists (select 1 from  T_SFC_OPTRPTENTRY left join T_SFC_OPERPLANNING on T_SFC_OPERPLANNING.FID=T_SFC_OPTRPTENTRY.FID  where F_isend='1' \n" +
                    "and T_SFC_OPTRPTENTRY.FMONUMBER=T_PRD_MO.FBILLNO and T_SFC_OPTRPTENTRY.FMOROWNUMBER=T_PRD_MOENTRY.FSEQ)";
           statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //新物料，物料清单中子项ID查询框架名称
    public static HashMap getKuangJiaInfo(String id){
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql="select T_BD_MATERIAL.FNUMBER,T_BD_MATERIAL_L.FNAME from T_ENG_BOMCHILD\n" +
                    "left join T_BD_MATERIAL on T_BD_MATERIAL.FMATERIALID =T_ENG_BOMCHILD.FMATERIALID \n" +
                    "left join T_BD_MATERIAL_L on T_BD_MATERIAL.FMATERIALID =T_BD_MATERIAL_L.FMATERIALID \n" +
                    "where T_ENG_BOMCHILD.FID ='" + id + "' and T_BD_MATERIAL.FNUMBER like 'N105%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String fnumber = resultSet.getString("FNUMBER");
                String name = resultSet.getString("FNAME");
                hashMap.put("fnumber",fnumber);
                hashMap.put("name",name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
    //查询生产进度
    public static ArrayList<JSONObject> getMOData(JSONObject queryParams){
        Set<Map.Entry<String, Object>> entries1 = queryParams.entrySet();
        Iterator<Map.Entry<String, Object>> iterator1 = entries1.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator1.hasNext()){
            Map.Entry<String, Object> next = iterator1.next();
            Object value = next.getValue();
            String key = next.getKey();
            if(value!=null&&!"".equals(value)){
                if("FNAME".equals(key))
                    sb.append(" and T_BD_MATERIAL_L.FNAME like '%"+value+"%'");
                else
                    sb.append(" and "+key+"='"+value+"'");
            }

        }

        // where FBillNo='20241021012' or FBillNo='OP000471'
        String condition=sb.toString();
        String sql="select top 1000\n" +
                "T_BD_DEPARTMENT_L.FFullName,----生产车间\n" +
                "T_SFC_OPERPLANNING.FMONumber,--生产订单编号\n" +
                "T_SFC_OPERPLANNING.FMOEntrySeq,--生产订单行号\n" +
                "T_SFC_OPERPLANNING.FBillNo,\n" +
                "T_BD_MATERIAL.FNUMBER ,\n" +
                "T_BD_MATERIAL_L.FNAME ,\n" +
                "T_BD_MATERIAL_L.FSPECIFICATION ,\n" +
                "T_ENG_ROUTE_L.FNAME gongyiluxian,--工艺路线\n" +
                "T_SFC_OPERPLANNINGDETAIL.FOperNumber,--工序号\n" +
                "T_ENG_PROCESS_L.FNAME zuoye,--作业\n" +
                "T_SFC_OPERPLANNINGDETAIL.FOptCtrlCodeId,--工序控制码\n" +
                "T_SFC_OPERPLANNINGDETAIL.FOperQty,--工序数量\n" +
                "dept_oper.FNAME jiagongchejian ,--加工车间\n" +
                "T_SFC_OPERPLANNINGDETAIL_B.FQualifiedQty,--工序合格数量\n" +
                "T_SFC_OPERPLANNINGDETAIL_B.FUnqualifiedQty,--不合格数量\n" +
                "(select top 1 T_SFC_OPTRPT.FDATE from T_SFC_OPTRPTENTRY \n" +
                "left join T_SFC_OPTRPT on T_SFC_OPTRPTENTRY.FID=T_SFC_OPTRPT.FID\n" +
                "where T_SFC_OPTRPTENTRY.FOPERNUMBER=T_SFC_OPERPLANNINGDETAIL.FOperNumber order by T_SFC_OPTRPT.FDATE desc) reportDate\n" +
                "from T_SFC_OPERPLANNING\n" +
                "left join T_SFC_OPERPLANNINGSEQ on T_SFC_OPERPLANNINGSEQ.FID =T_SFC_OPERPLANNING.FID \n" +
                "left join T_SFC_OPERPLANNINGDETAIL on T_SFC_OPERPLANNINGDETAIL.FENTRYID =T_SFC_OPERPLANNINGSEQ.FENTRYID \n" +
                "left join T_SFC_OPERPLANNINGDETAIL_B on T_SFC_OPERPLANNINGDETAIL_B.FDETAILID =T_SFC_OPERPLANNINGDETAIL.FDETAILID \n" +
                "left join T_BD_MATERIAL_L on T_BD_MATERIAL_L.FMATERIALID =T_SFC_OPERPLANNING.FProductId\n" +
                "left join T_BD_MATERIAL on T_BD_MATERIAL.FMATERIALID =T_SFC_OPERPLANNING.FProductId\n" +
                "left join T_BD_DEPARTMENT_L on T_BD_DEPARTMENT_L.FDEPTID =T_SFC_OPERPLANNING.FProDepartmentId\n" +
                "left join T_ENG_PROCESS_L on T_ENG_PROCESS_L.FID =T_SFC_OPERPLANNINGDETAIL.FProcessId\n" +
                "left join T_ENG_ROUTE_L on T_ENG_ROUTE_L.FID =T_SFC_OPERPLANNING.FRouteId\n" +
                "left join T_BD_DEPARTMENT_L dept_oper on dept_oper.FDEPTID =T_SFC_OPERPLANNINGDETAIL.FDepartmentId\n" +
                " where T_SFC_OPERPLANNINGDETAIL.FOptCtrlCodeId<>226304 "+condition+
                " order by T_SFC_OPERPLANNING.FMONumber DESC ,T_SFC_OPERPLANNING.FMOEntrySeq ASC ,T_SFC_OPERPLANNINGDETAIL.FOperNumber\n";
        Connection connection = getconnection();
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ArrayList<JSONObject> arr = new ArrayList<>();
        HashMap<String,JSONObject> jbMain = new LinkedHashMap<String,JSONObject>();
        JSONObject jbdetails = new JSONObject();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String fBillNo = resultSet.getString("FBillNo");
                if(!jbMain.containsKey(fBillNo)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("FFullName",resultSet.getString("FFullName"));
                    jsonObject.put("FMONumber",resultSet.getString("FMONumber"));
                    jsonObject.put("FMOEntrySeq",resultSet.getString("FMOEntrySeq"));
                    jsonObject.put("FBillNo",fBillNo);
                    jsonObject.put("FNUMBER",resultSet.getString("FNUMBER"));
                    jsonObject.put("FNAME",resultSet.getString("FNAME"));
                    jsonObject.put("FSPECIFICATION",resultSet.getString("FSPECIFICATION"));
                    jsonObject.put("gongyiluxian",resultSet.getString("gongyiluxian"));
                    jsonObject.put("FOperQty",resultSet.getFloat("FOperQty"));
                    jsonObject.put("reportDate",resultSet.getString("reportDate"));
                    jsonObject.put("details",new ArrayList<JSONObject>());
                    jbMain.put(fBillNo,jsonObject);
                }
                JSONObject jsonMain = (JSONObject) jbMain.get(fBillNo);
                ArrayList<JSONObject> details = (ArrayList<JSONObject>) jsonMain.get("details");
                JSONObject jsondetail = new JSONObject();
                jsondetail.put("FOperNumber",resultSet.getString("FOperNumber"));
                jsondetail.put("zuoye",resultSet.getString("zuoye"));
                jsondetail.put("jiagongchejian",resultSet.getString("jiagongchejian"));
                jsondetail.put("FQualifiedQty",resultSet.getFloat("FQualifiedQty"));
                jsondetail.put("FUnqualifiedQty",resultSet.getFloat("FUnqualifiedQty"));
                details.add(jsondetail);
                jsonMain.put("details",details);
            }
            Set<Map.Entry<String, JSONObject>> entries = jbMain.entrySet();
            Iterator<Map.Entry<String, JSONObject>> iterator = entries.iterator();
            while (iterator.hasNext()){
                Map.Entry<String, JSONObject> next = iterator.next();
                arr.add(next.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (JSONObject jsonObject : arr) {
            System.out.println(jsonObject);
        }
        return arr;
    }
    //更新是否领料
    public static int executeUpdateGXLL(String date){
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        int success=1;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        String sql1="update T_SFC_OPERPLANNINGDETAIL set T_SFC_OPERPLANNINGDETAIL.REPORT_DATE=CONVERT(varchar(100),A.FDATE,120)\n" +
                "from T_SFC_OPERPLANNINGDETAIL join (select FDATE,T_SFC_OPTRPTENTRY.FSOURCEBILLNO,T_SFC_OPTRPTENTRY.FOPERNUMBER  from T_SFC_OPTRPTENTRY \n" +
                "left join T_SFC_OPTRPT on T_SFC_OPTRPT.FID= T_SFC_OPTRPTENTRY.FID ) as A ON T_SFC_OPERPLANNINGDETAIL.FBARCODE=A.FSOURCEBILLNO+'-0-'+CONVERT(varchar,A.FOPERNUMBER)\n" +
                "where  CONVERT(varchar(100),A.FDATE,120) like '"+date+"%'";
        String sql2="UPDATE  T_SFC_OPERPLANNINGDETAIL set F_LL='' where T_SFC_OPERPLANNINGDETAIL.REPORT_DATE like '"+date+"%'";
        String sql3="UPDATE  T_SFC_OPERPLANNINGDETAIL set F_LL='未领料'\n" +
                "where FDETAILID in (\n" +
                "select FDETAILID from \n" +
                "(\n" +
                "select \t\n" +
                "A.*,T_SFC_OPERPLANNING.FBillNo,T_SFC_OPERPLANNING.FMONumber,T_SFC_OPERPLANNING.FMOEntrySeq qwe,\tT_SFC_OPERPLANNINGDETAIL.FOperNumber,\n" +
                "T_SFC_OPERPLANNINGDETAIL.FProcessId,T_SFC_OPERPLANNINGDETAIL.REPORT_DATE ,T_SFC_OPERPLANNINGDETAIL.FDETAILID \n" +
                "from T_SFC_OPERPLANNING \n" +
                "left join T_SFC_OPERPLANNINGSEQ on T_SFC_OPERPLANNING.FID =T_SFC_OPERPLANNINGSEQ.FID \n" +
                "left join T_SFC_OPERPLANNINGDETAIL on T_SFC_OPERPLANNINGSEQ.FENTRYID =T_SFC_OPERPLANNINGDETAIL.FENTRYID \n" +
                "left join (\n" +
                "select \n" +
                "T_PRD_PPBOM.FMOBILLNO,--生产订单号\n" +
                "T_PRD_PPBOM.FMOENTRYSEQ,--生产订单行号\n" +
                "T_PRD_PPBOMENTRY.FOperID, --工序号\n" +
                "T_PRD_PPBOMENTRY_Q.FNOPICKEDQTY --未领数量\n" +
                "from T_PRD_PPBOM --用料清单\n" +
                "left join T_PRD_PPBOMENTRY on T_PRD_PPBOM.FID =T_PRD_PPBOMENTRY.FID\n" +
                "left join T_PRD_PICKMTRLDATA on T_PRD_PICKMTRLDATA.FPPBOMENTRYID=T_PRD_PPBOMENTRY.FENTRYID \n" +
                "left join T_PRD_PPBOMENTRY_Q on T_PRD_PPBOMENTRY_Q.FENTRYID =T_PRD_PPBOMENTRY.FENTRYID \n" +
                "where  T_PRD_PPBOMENTRY_Q.FNOPICKEDQTY>0 ) A on T_SFC_OPERPLANNING.FMONumber=A.FMOBILLNO and T_SFC_OPERPLANNING.FMOEntrySeq=A.FMOENTRYSEQ and T_SFC_OPERPLANNINGDETAIL.FOperNumber=A.FOperID\n" +
                "\n" +
                "where  A.FMOBILLNO is not null \n" +
                "and T_SFC_OPERPLANNINGDETAIL.REPORT_DATE like '"+date+"%' --时间按需修改\n" +
                ") B\n" +
                ")";
        try {
            statement.addBatch(sql1);
            statement.addBatch(sql2);
            statement.addBatch(sql3);
            results = statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        for (int result : results) {
            System.out.println(result);
        }
        System.out.println("执行结束");
        return success;
    }
    //重建索引
    public static void buildIndexOfKingdee(){
        Date date = new Date();
        String dateStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", date);
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        int success=1;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        String sql="DECLARE @sql NVARCHAR(1000) = 'DBCC DBREINDEX(@TABLE_NAME) WITH NO_INFOMSGS';\n" +
                "\n" +
                "DECLARE @tbl NVARCHAR(1000) = ''\n" +
                "\n" +
                "DECLARE tblcur CURSOR FOR SELECT [NAME] FROM sys.tables WHERE [NAME] NOT LIKE 'TMP%' AND [NAME] NOT LIKE 'Z[_]%' ORDER BY 1\n" +
                "\n" +
                "FOR READ ONLY OPEN tblcur\n" +
                "\n" +
                "FETCH NEXT FROM tblcur INTO @tbl\n" +
                "\n" +
                "WHILE @@FETCH_STATUS=0\n" +
                "\n" +
                "BEGIN\n" +
                "\n" +
                "    EXEC SP_EXECUTESQL @sql, N'@table_name NVARCHAR(1000)', @tbl\n" +
                "\n" +
                "    PRINT '表重建索引成功：' + @tbl\n" +
                "\n" +
                "    FETCH NEXT FROM tblcur INTO @tbl\n" +
                "\n" +
                "END\n" +
                "\n" +
                "CLOSE tblcur \n" +
                "\n" +
                "DEALLOCATE tblcur\n" +
                "\n" +
                "PRINT '全部执行完成！'";
        try {
            statement.addBatch(sql);
            results = statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long time=(new Date().getTime()-date.getTime())/ 1000;
        if(success==0){
            WXUtil.sendMsg("itGongChengShi-WenFengSheng|BingTangHuLu","索引重建【失败】，请重试！\n执行时间："+dateStr+"\n总耗时："+time+"秒");
        }else {
            WXUtil.sendMsg("itGongChengShi-WenFengSheng|BingTangHuLu","索引重建成功！\n执行时间："+dateStr+"\n总耗时："+time+"秒");
        }
    }
    //优化成本数据
    public static void handleCBData(){
        Date date = new Date();
        String dateStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", date);
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        int success=1;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        String sql1="declare @SqlStr nvarchar(max) \n" +
                "set @SqlStr=''\n" +
                "select @SqlStr= @SqlStr+ + 'alter index all on '+name+ ' rebuild;' from sysobjects where xtype='U' and (name in(\n" +
                "'T_HS_OUTACCTG','T_HS_CALDINENSIONS','T_FA_ACCTPOLICY','T_CB_COSTCENTER','T_CB_COSTCALEXPENSEDETAIL','T_CB_COSTCALEXPENSE','T_CB_COSTCALEXPENSEDETAIL_H','T_CB_COSTCALEXPENSE_H','T_CB_PROORDERTYPE',\n" +
                "'T_CB_PROORDERINFO','T_CB_PROORDERINFO_H','T_CB_PRORDERDIME','T_BD_MATERIAL','T_BD_MATERIALBASE','T_BD_ACCOUNTCALENDAR','T_BD_ACCOUNTPERIOD','T_HS_OUTINSTOCKSEQ','T_HS_OUTINSTOCKSEQ_H','T_CB_COSTALLORESULTSEND_H','T_CB_COSTALLORESULTREC_H'\n" +
                ")) and (name not like 'TMP%' )\n" +
                "exec (@SqlStr)";
        String sql2="declare @SqlStr1 nvarchar(max)\n" +
                "set @SqlStr1=''\n" +
                "select @SqlStr1=@SqlStr1+ + 'UPDATE STATISTICS '+name+' ;' from sysobjects where xtype='U' and (name in(\n" +
                "'T_HS_OUTACCTG','T_HS_CALDINENSIONS','T_FA_ACCTPOLICY','T_CB_COSTCENTER','T_CB_COSTCALEXPENSEDETAIL','T_CB_COSTCALEXPENSE','T_CB_COSTCALEXPENSEDETAIL_H','T_CB_COSTCALEXPENSE_H','T_CB_PROORDERTYPE',\n" +
                "'T_CB_PROORDERINFO','T_CB_PROORDERINFO_H','T_CB_PRORDERDIME','T_BD_MATERIAL','T_BD_MATERIALBASE','T_BD_ACCOUNTCALENDAR','T_BD_ACCOUNTPERIOD','T_HS_OUTINSTOCKSEQ','T_HS_OUTINSTOCKSEQ_H','T_CB_COSTALLORESULTSEND_H','T_CB_COSTALLORESULTREC_H'\n" +
                ")) and (name not like 'TMP%' )\n" +
                "exec (@SqlStr1)";
        try {
            statement.addBatch(sql1);
            statement.addBatch(sql2);
            results = statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long time=(new Date().getTime()-date.getTime())/ 1000;
        if(success==0){
            WXUtil.sendMsg("itGongChengShi-WenFengSheng|BingTangHuLu","优化成本数据【失败】，请重试！\n执行时间："+dateStr+"\n总耗时："+time+"秒");
        }else {
            WXUtil.sendMsg("itGongChengShi-WenFengSheng|BingTangHuLu","优化成本数据成功！\n执行时间："+dateStr+"\n总耗时："+time+"秒");
        }
    }
    //反写工序计划单号到生成订单 todo
    public static void writeToMO(){
        Connection connection = getconnection();
        Statement statement = null;
        int[] results=null;
        int success=1;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        String sql="update T_PRD_MOENTRY set T_PRD_MOENTRY.F_GONGXUHAO =isnull(T_SFC_OPERPLANNING.FBILLNO ,'')\n" +
                "from T_PRD_MOENTRY left join T_PRD_MO on T_PRD_MOENTRY.FID =T_PRD_MO.FID \n" +
                "left join T_SFC_OPERPLANNING on T_SFC_OPERPLANNING.FMONUMBER=T_PRD_MO.FBILLNO and T_SFC_OPERPLANNING.FMOENTRYSEQ=T_PRD_MOENTRY.FSEQ \n" +
                "where T_PRD_MOENTRY.F_GONGXUHAO is not NULL";
        try {
            statement.addBatch(sql);
            results = statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            success=0;
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getCarIds(){
        Connection tkdb = getconnection38("tkdb");
        String sql="SELECT car_number FROM record_visitor where CAST(v_date AS DATE) = CAST(GETDATE() AS DATE)  ";
        PreparedStatement preparedStatement = null;
        ArrayList<String> arr = new ArrayList<>();
        try {
            preparedStatement = tkdb.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String car_number = resultSet.getString("car_number");
                if(!"".equals(car_number.trim())&&!arr.contains(car_number))
                    arr.add(car_number);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return arr;
    }
    public static void main(String[] args) {
        System.out.println(getCarIds());
    }
}
