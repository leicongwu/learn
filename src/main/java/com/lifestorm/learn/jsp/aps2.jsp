<%@ page language="java"
         import="com.eciq.common.context.CommContext,com.eciq.common.dao.DaoFactory,com.eciq.common.decl.show.entity.DeclIoSearchVo,com.eciq.common.decleval.MockService,com.eciq.query.decl.dao.DeclInfoQueryDao,com.eciq.query.decl.logic.DeclInfoQueryLogic,com.itown.rcp.core.jpa.EntityManagerUtils"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.persistence.EntityManager" %>
<%
    Map<String,String> params = new HashMap<String, String>();
    params.put("orgCode","000000");
    params.put("declType","1");
    params.put("declNo","118000000001131");
//    params.put("declNo","116000000000126");
    params.put("queryType","1");
    params.put("orgCodes","0");
    EntityManager em = EntityManagerUtils.getInstance().getEntityManagerFactory("ECIQ_ARCHIVE", CommContext.PERSISTENCE_UNIT_NAME).createEntityManager();
    MockService ms = new MockService();
    ms.setEntityManager(em);
    DeclInfoQueryDao dao = DaoFactory.create(DeclInfoQueryDao.class, ms);
    List<DeclIoSearchVo> vindicList = new ArrayList<DeclIoSearchVo>();
    //2018-05-28  李云龙  优化查询效率
    vindicList = dao.getIoDeclListA(params);
//    DeclInfoQueryService declInfoQueryService = ServiceFactory.getInstance().getService(DeclInfoQueryService.class);
//    List<DeclIoSearchVo> vindicList = declInfoQueryService.getHIoDeclListWithOutGoods(params);
    if(vindicList == null){
        out.println("查询数据为空");
    }else{
        out.println("查询的数据不为空"+ vindicList.size());
    }
    String y2= null;
    for (int i = 0; i < vindicList.size(); i++) {
        y2+="declGetNo"+vindicList.get(i).getDeclNo();
        out.println("查询的数据不为空"+y2);
    }
//MockService ms = new MockService();
//ms.setEntityManager(em);
//String x = "X";
//try{
//DeclEvaluationFactory.getInstance().getDeclEvaluation(ms)
//						.autoEvaluation("116000000042823");
//ts.commit();
//x = "Y";
//}finally{
//em.close();
//}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>

<body>
<H2><%="----"%><%=y2%>
</H2>
</body>
</html>
