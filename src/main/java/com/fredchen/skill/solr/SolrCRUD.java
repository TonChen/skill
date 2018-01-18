package com.fredchen.skill.solr;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javacommon.util.SpringContextUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfth.chan.model.GoodsBrand;
import com.cfth.chan.model.GoodsSku;
import com.cfth.chan.model.GoodsSkuPrice;
import com.cfth.chan.model.RegCompanyGroupDirectGoods;
import com.cfth.chan.model.RegCompanyGroupDirectGoodsClass;
import com.cfth.chan.service.GetAutowiredPropertyManager;
import com.cfth.core.page.Page;
import com.cfth.core.util.ObjectUtils;

public class SolrCRUD {
	private static final Logger LOG = LoggerFactory.getLogger(SolrCRUD.class);
	private final static String ZK_HOST = "121.40.148.172:2181,121.40.101.86:2181,120.26.132.15:2181";
	private final static String DEFAULT_COLLECTION = "collection1";
	private final static int ZK_CLIENT_TIMEOUT = 20000;
	private final static int ZK_CONNECT_TIMEOUT = 20000;
	
	private static final String GOODS_PREFIX = "goods_";//reg_company_group_direct_goods
	private static final String SKU_PREFIX = "sku_";//goods_sku
	private static final String PRICE_PREFIX = "price_";//goods_sku_price
	private static final String CLASS_PREFIX = "class_";//reg_company_group_direct_goods_class
	private static final String BRAND_PREFIX = "brand_";//goods_brand

	private static CloudSolrServer cloudSolrServer;

	/**
	 * 获取solr服务,同步
	 * 
	 * @param zkHost
	 * @return
	 * @throws MalformedURLException
	 */
	private static synchronized CloudSolrServer getCloudSolrServer(
			final String zkHost) throws MalformedURLException {
		if (cloudSolrServer == null) {
			try {
				cloudSolrServer = new CloudSolrServer(zkHost);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cloudSolrServer;
	}

	public static CloudSolrServer initLocalServer() throws MalformedURLException {
		CloudSolrServer cloudSolrServer = getCloudSolrServer(ZK_HOST);
		System.out.println("CloudSolrServer成功创建！");
		cloudSolrServer.setDefaultCollection(DEFAULT_COLLECTION);
		cloudSolrServer.setZkClientTimeout(ZK_CLIENT_TIMEOUT);
		cloudSolrServer.setZkConnectTimeout(ZK_CONNECT_TIMEOUT);
		cloudSolrServer.connect();
		System.out.println("cloudServer成功连接!");
		return cloudSolrServer;
	}
	
	public static CloudSolrServer initServer() throws MalformedURLException {
		GetAutowiredPropertyManager propertyManager = (GetAutowiredPropertyManager) SpringContextUtil.getBean("getAutowiredPropertyManager");
		CloudSolrServer cloudSolrServer = getCloudSolrServer(propertyManager.getZkHost());
		System.out.println("CloudSolrServer成功创建！");
		cloudSolrServer.setDefaultCollection(propertyManager.getCollection());
		cloudSolrServer.setZkClientTimeout(propertyManager.getZkClientTimeout());
		cloudSolrServer.setZkConnectTimeout(propertyManager.getZkConnectTimeout());
		cloudSolrServer.connect();
		System.out.println("cloudServer成功连接!");
		return cloudSolrServer;
	}

	/**
	 * ping检测solr是否down掉 [测试通过]
	 * 
	 * @param server
	 * @return
	 */
	public static String ping(SolrServer server) {
		try {
			return server.ping().getResponse().toString();
		} catch (SolrServerException e) {
			LOG.error("Solr system ping error " + e.getMessage(), e);
		} catch (IOException e) {
			LOG.error("Solr system ping error " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据id从索引中删除记录[测试通过]
	 * 
	 * @param server
	 * @param idName
	 *            主键名
	 * @param id
	 *            主键值
	 */
	public static void deleteById(SolrServer server, String idName, Object id, Object object) {
		try {
			if(null != object){
//				idName = parseString(idName);
				idName = parseQueryName(idName, object);
				LOG.debug("【解析后的查询字段：】"+idName);
			}
			server.deleteByQuery(idName + ":" + id.toString());
			server.commit(false, false);
			LOG.info("Delete from index by id" + id
					+ " finished . operate param is：" + idName + ":"
					+ id.toString());
		} catch (Exception e) {
			LOG.error(
					"Delete from index by id" + id + " error, "
							+ e.getMessage(), e);
		}
	}

	/**
	 * 根据id集合从索引中删除记录[测试通过]
	 * 
	 * @param server
	 * @param ids
	 */
	public static <T> void deleteByIds(SolrServer server, String idName, List<T> ids, Object object) {
		try {
			if (ids.size() > 0) {
				if(null != object){
//					idName = parseString(idName);
					idName = parseQueryName(idName, object);
					LOG.debug("【解析后的查询字段：】"+idName);
				}
				StringBuffer query = new StringBuffer(idName + ":" + ids.get(0));
				for (int i = 1; i < ids.size(); i++) {
					if (null != ids.get(i)) {
						query.append(" OR " + idName + ":"
								+ ids.get(i).toString());
					}
				}
				server.deleteByQuery(query.toString());
				server.commit(false, false);
				LOG.info("Delete from index by id list" + ids + " finished .");
			} else {
				LOG.info("Delete ids list is null.");
			}
		} catch (Exception e) {
			LOG.error(
					"Delete from index by id list" + ids + " error, "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询从索引中删除[测试通过]
	 * 
	 * @param server
	 * @param queryString
	 */
	@Deprecated
	public static void deleteByQuery(SolrServer server, String query, Object object) {
		try {
			if(null != object){
//				query = parseString(query);
				query = parseQueryName(query, object);
				LOG.debug("【解析后的查询字段：】"+query);
			}
			server.deleteByQuery(query);
			server.commit(false, false);
			LOG.info("Delete from index by query string " + query
					+ "finished .");
		} catch (Exception e) {
			LOG.error("Delete from index by query Strng " + query + "error, "
					+ e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据对象删除,实质是根据Id删除
	 * 
	 * @param server
	 *            solr客户端
	 * @param object
	 *            删除的对象
	 * @param idName
	 *            对象的主键名
	 */
	@Deprecated
	public static void deleteBean(SolrServer server, Object object, String idName) {
		Class<?> cls = object.getClass();
		try {
//			idName = parseString(idName);
			idName = parseQueryName(idName, object);
			LOG.debug("【解析后的查询字段：】"+idName);
			Method method = cls.getMethod(dynamicMethodName(idName, "get"));
			Object o = method.invoke(object);
			if (o != null) {
				deleteById(server, idName, o, object);
			}
			LOG.info("Delete from index by object" + object);
		} catch (Exception e) {
			LOG.error("Delete from index by object error, " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	//model属性解析成驼峰格式
	public static String parseString(String source){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char chr = source.charAt(i); 
			if(Character.isUpperCase(chr)){
				sb.append("_" + Character.toLowerCase(chr)); 
			}else if(Character.isLowerCase(chr)){  
				sb.append(chr);
			}  
		}
		return sb.toString();
	}
	
	public static String parseQueryName(String queryName, Object object){
		if(null == object){
			return "";
		}
		String name = "";
		if(object instanceof RegCompanyGroupDirectGoods){
			name = GOODS_PREFIX + queryName;
		}else if(object instanceof GoodsSku){
			name = SKU_PREFIX + queryName;
		}else if(object instanceof GoodsSkuPrice){
			name = PRICE_PREFIX + queryName;
		}else if(object instanceof GoodsBrand){
			name = BRAND_PREFIX + queryName;
		}else if(object instanceof RegCompanyGroupDirectGoodsClass){
			name = CLASS_PREFIX + queryName;
		}
		return name;
	}
	
	/**
	 * 删除所有索引 [测试通过]
	 * 
	 * @param server
	 */
	public static void deleteAllIndex(SolrServer server) {
		try {
			server.deleteByQuery("*:*");
			server.commit(false, false);
			LOG.info("All index delete finished.");
		} catch (Exception e) {
			LOG.error("Delete all index error " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新单个记录 [测试通过]
	 * 
	 * @author pudongping
	 * 
	 * @param server
	 *            Solr客户端
	 * @param object
	 *            要更新成的对象
	 * @param idName
	 *            主键id名
	 */
	@Deprecated
	public static void updateBean(SolrServer server, Object object,
			String idName) {
		if (null != object && StringUtils.isNotBlank(idName)) {
			Class<?> clzz = object.getClass();
			try {
				Method method = clzz
						.getMethod(dynamicMethodName(idName, "get"));
				Object o = method.invoke(object);
				if (null != o) {
					SolrQuery query = new SolrQuery();
					query.setQuery(idName + ":" + o.toString());
					query.setStart(0);
					query.setRows(1);
					QueryResponse response = server.query(query);
					SolrDocument document = response.getResults().get(0);
					// LOG.info("更新一个记录" +
					// EntityConvert.solrDocument2Entity(document, clzz));
					// System.out.println("更新一个记录" +
					// EntityConvert.solrDocument2Entity(document, clzz));
					UpdateRequest updateRequest = new UpdateRequest();
					updateRequest.setAction(
							AbstractUpdateRequest.ACTION.COMMIT, false, false);
					updateRequest.add(solrDocument2SolrInputDocument(document,
							object));
					updateRequest.process(server);
				}
			} catch (NoSuchMethodException e) {
				LOG.error("Update bean error" + object);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				LOG.error("Update bean error" + object);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				LOG.error("Update bean error" + object);
				e.printStackTrace();
			} catch (Exception e) {
				LOG.error("Update bean error" + object);
				e.printStackTrace();
			}
		}
	}

	/**
	 * [测试通过]
	 * 
	 * 更新数据时用到，给出要更新的对象，Id为必须给出的属性，然后加上要更新的属性 如果对应的属性的值为空或者为0，这不需要更新
	 * 
	 * @param sd
	 *            查询到得SolrDocument
	 * @param object
	 * @return SolrInputDocument
	 */
	@SuppressWarnings("rawtypes")
	public static SolrInputDocument solrDocument2SolrInputDocument(
			SolrDocument sd, Object object) {
		if (object != null && sd != null) {
			SolrInputDocument sid = new SolrInputDocument();
			Collection<String> fieldNameCollection = sd.getFieldNames(); // 得到所有的属性名
			Class<?> cls = object.getClass();
			Object o = null;
			for (String fieldName : fieldNameCollection) {
				fieldName = string2Filed(fieldName, true);
				try {
					// 需要说明的是返回的结果集中的FieldNames()比类属性多
					Field[] filedArrays = cls.getDeclaredFields(); // 获取类中所有属性
					for (Field f : filedArrays) {
						// 如果实体属性名和查询返回集中的字段名一致,填充对应的set方法
						if (f.getName().equals(fieldName)) {
							// 如果对应的属性的值为空或者为0，这不需要更新
							o = cls.getMethod(dynamicMethodName(fieldName, "get")).invoke(object);
							Class<?> fieldType = cls.getDeclaredField(fieldName).getType();

							if (fieldType.equals(Integer.TYPE)) {
								Integer fieldValue = Integer.class.cast(o);
								if (fieldValue != null
										&& fieldValue.compareTo(0) != 0) {
									sid.addField(fieldName, fieldValue);
								}
							} else if (fieldType.equals(Float.TYPE)) {
								Float fieldValue = Float.class.cast(o);
								if (fieldValue != null
										&& fieldValue.compareTo(0f) != 0) {
									sid.addField(fieldName, fieldValue);
								}
							} else if (fieldType.equals(Double.TYPE)) {
								Double fieldValue = Double.class.cast(o);
								if (fieldValue != null
										&& fieldValue.compareTo(0d) != 0) {
									sid.addField(fieldName, fieldValue);
								}
							} else if (fieldType.equals(Short.TYPE)) {
								Short fieldValue = Short.class.cast(o);
								if (fieldValue != null
										&& fieldValue.compareTo((short) 0) != 0) {
									sid.addField(fieldName, fieldValue);
								}
							} else if (fieldType.equals(Long.TYPE)) {
								Long fieldValue = Long.class.cast(o);
								if (fieldValue != null
										&& fieldValue.compareTo((long) 0) != 0) {
									sid.addField(fieldName, fieldValue);
								}
							} else if (fieldType.equals(List.class)) {
								List fieldValue = List.class.cast(o);
								if (fieldValue != null) {
									sid.addField(fieldName, fieldValue);
								}
							} else {
								if (o != null) {
									sid.addField(fieldName, o.toString());
								}
							}
						}
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					LOG.error("请检查PO类中的field对应的各个setter和getter是否存在！");
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					LOG.error("请检查schema中的field是否不存在于PO类中！");
					e.printStackTrace();
				}
			}
			return sid;
		}
		LOG.warn("即将要转换的SolrDocument或者要更新的Object为null");
		return null;
	}

	public static List<Object> solrDocument2Entity(SolrDocumentList sds, Object object) {
		Class<?> clazz = object.getClass();
		List<Object> items = new ArrayList<Object>();
		for (SolrDocument solrDocument : sds) {
			Collection<String> fieldNameCollection = solrDocument.getFieldNames();
			Method method = null;
			for (String fieldName : fieldNameCollection) {
				Object value = solrDocument.getFieldValue(fieldName);
				fieldName = string2Filed(fieldName, true);
				try {
					// 需要说明的是返回的结果集中的FieldNames()比类属性多
					Field[] filedArrays = clazz.getDeclaredFields();// 获取类中所有属性
					for (Field f : filedArrays) {
						// 如果实体属性名和查询返回集中的字段名一致,填充对应的set方法
						if (f.getName().equals(fieldName)) {
							// 如果对应的属性的值为空或者为0，这不需要更新
							PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
							method = pd.getWriteMethod();
							method.invoke(object, value);
						}
					}
					items.add(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IntrospectionException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return items;
		}
		return null;
	}

	private static String dynamicMethodName(String fieldName, String method) {
		fieldName = fieldName.trim();
		LOG.debug("【处理之前字段名：】" + fieldName + "，方法：" + method);
		String mname = method + string2Filed(fieldName, false);
		LOG.debug("【处理之后字段名：】" + fieldName);
		return mname;
	}

	public static String string2Filed(String source, boolean isAttr) {
		StringBuilder sb = new StringBuilder();
		if (source.indexOf('_') != -1) {
			String[] arr = source.split("_");
			for (int i = 0; i < arr.length; i++) {
				if (isAttr) {
					if (i == 1) {
						sb.append(arr[i]);
					} else if (i > 1) {
						String prop = Character.toUpperCase(arr[i].charAt(0)) + arr[i].substring(1);
						sb.append(prop);
					}
				} else {
					if (i != 0) {
						String prop = Character.toUpperCase(arr[i].charAt(0)) + arr[i].substring(1);
						sb.append(prop);
					}
				}
			}
		} else {
			if (isAttr) {
				sb.append(source);
			} else {
				String prop = Character.toUpperCase(source.charAt(0)) + source.substring(1);
				sb.append(prop);
			}
		}
		return sb.toString();
	}

	/**
	 * 根据关键字查询 [测试通过 - 使用 solr内部转换机制]
	 * 
	 * @param <T>
	 * @param server
	 *            solr客户端
	 * @param solrql
	 *            sql查询串
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            每页显示的大小
	 * @param clzz
	 *            对象类型
	 * @return
	 */
	public static Page query(SolrServer server, String solrql, int pageNum,
			int pageSize, Object object) {
		SolrQuery query = new SolrQuery();
		query.setQuery(solrql);
		query.setStart((pageNum - 1) * pageSize);
		query.setRows(pageSize);
		QueryResponse response = null;
		try {
			response = server.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
		SolrDocumentList docs = response.getResults();
		// 查询到的记录总数
		int totalRow = Long.valueOf(docs.getNumFound()).intValue();
		// 查询结果集,由于schema配置不支持，所以改写方法
		// List<T> items = response.getBeans(clzz);
		// for (SolrDocument doc : docs) {
		// Collection<String> fields = doc.getFieldNames();
		// for (String fieldName : fields) {
		// Map<String,String> map = new HashMap<String, String>();
		// map.put(fieldName, doc.getFieldValue(fieldName).toString());
		// }
		// }
		List<? extends Object> items = solrDocument2Entity(docs, object);

		// 填充page对象
		return new Page(pageNum, pageSize, totalRow, items);
	}

	/**
	 * 搜索索引
	 * 
	 * @param solrServer
	 * @param String
	 *            查询参数
	 * @param rows
	 *            返回显示条数
	 */
	public static List<Map<String, String>> search(SolrServer solrServer, String String, Integer pageNum, Integer pageSize) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		SolrQuery query = new SolrQuery();
		query.setQuery(String);
		query.setStart(pageNum == null ? 0 : (pageNum - 1) * (pageSize == null ? 10 : pageSize));
		query.setRows(pageSize == null ? 10 : pageSize);
		try {
			QueryResponse response = solrServer.query(query);
			SolrDocumentList docs = response.getResults();

			System.out.println("文档个数：" + docs.getNumFound());
			System.out.println("查询时间：" + response.getQTime());

			for (SolrDocument doc : docs) {
				Collection<String> fields = doc.getFieldNames();
				for (String fieldName : fields) {
					Map<String, String> map = new HashMap<String, String>();
					map.put(fieldName, doc.getFieldValue(fieldName).toString());
					result.add(map);
				}
			}
			return result;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加、更新索引
	 * 
	 * @param solrServer
	 */
	public static void modifyIndex(SolrServer solrServer, boolean isUpdate, List<Map<String, String>> datas) {
		try {
			if (ObjectUtils.isEmpty(datas)) {
				return;
			}
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (Map<String, String> map : datas) {
				SolrInputDocument doc = new SolrInputDocument();
				Set<Entry<String, String>> entrys = map.entrySet();
				for (Entry<String, String> entry : entrys) {
					String key = entry.getKey();
					String value = entry.getValue();
					if(isUpdate){
						doc.setField(key, value);
					}else{
						doc.addField(key, value);
						if(!doc.containsKey("id")){
							doc.addField("id", value);
						}
					}
				}
				docs.add(doc);
			}
			solrServer.add(docs);
			solrServer.commit();
		} catch (SolrServerException e) {
			LOG.debug("【索引添加、更新出错1】..........");
			System.out.println("Add docs Exception !!!");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("【索引添加、更新出错2】..........");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!!");
			e.printStackTrace();
		}
	}
	
	public static void modifyIndex(SolrServer solrServer, boolean isUpdate, Object object) {
		try {
			if (ObjectUtils.isEmpty(object)) {
				return;
			}
			Class<?> clazz = object.getClass();
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			Field[] filedArrays = clazz.getDeclaredFields();// 获取类中所有属性
			String idString = "";
			boolean isFirst = true;
			SolrInputDocument doc = new SolrInputDocument();
			for (Field field : filedArrays) {
				String modifier = Modifier.toString(field.getModifiers());
				if(modifier.contains("static final")){
					continue;
				}
				String fieldName = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method method = pd.getReadMethod();
				
				Object obj = method.invoke(object);
				if(ObjectUtils.isEmpty(obj)){
					continue;
				}
				if("id".equals(fieldName)){
					idString = obj.toString();
				}
				fieldName = parseQueryName(fieldName, object);
				if(isUpdate){//更新索引
					doc.setField(fieldName, obj);
					if(isFirst){
						if(!doc.containsKey("id")){
							doc.setField("id", idString);
							isFirst = false;
						}
					}
				}else{
					doc.setField(fieldName, obj);
					if(isFirst){
						if(!doc.containsKey("id")){
							doc.setField("id", idString);
							isFirst = false;
						}
					}
				}
			}
			docs.add(doc);
			
			solrServer.add(docs);
			solrServer.commit();
			
		} catch (SolrServerException e) {
			LOG.debug("【索引添加、更新出错1】..........");
			System.out.println("Add docs Exception !!!");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("【索引添加、更新出错2】..........");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unknowned Exception!!!!!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//测试连接
//		System.err.println(ping(initServer()));
		
		//条件查询
//		String queryString = "goods_name:*";
		CloudSolrServer server = SolrCRUD.initLocalServer();
//		 List<Map<String,String>> results = SolrCRUD.search(server, queryString,null,null);
//		 for (Map<String, String> map : results) {
//			 System.err.println(map);
//		 }
		
		//分页查询
//		Page page = SolrCRUD.query(server, queryString, 1, 15, new RegCompanyGroupDirectGoods());
//		System.err.println(page.getTotalCount() + ":" + page.getPageSize() + ":" + page.getNextPageNumber() + ":" + page.getThisPageNumber());
//		List list = page.getResult();
//		for (Object object : list) {
//			System.err.println("结果：" + object);
//		}
		
//		System.err.println(parseString("regCompanyGroupDirectGoods"));
		
//		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id", "00168858190479362");
//		map.put("brand_id", "0016885819047936");
//		map.put("brand_name", "瑞士军刀");
//		datas.add(map);
//		GoodsBrand gb = new GoodsBrand();
//		gb.setId("0016983965761539");
//		gb.setName("瑞士军刀1");
//		gb.setIsDel(true);
//		modifyIndex(server, false, gb);
		deleteAllIndex(server);
		
	}
}
