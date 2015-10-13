package com.cyberone.sskm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.ui.ModelMap;


/**
 * 데이터를 처리하기 위한 유틸
 * @author 9010661
 *
 */
@SuppressWarnings("rawtypes")
public abstract class DataUtil {

	/**
	 * DTO의 데이터를 String으로 변환
	 * @return
	 */
	public static String dtoToString(Object ogj) {
		return ToStringBuilder.reflectionToString(ogj, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	/**
	 * Request의 파라미터을 DTO로 생성
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object dtoBuilder(HttpServletRequest request, Class<?> clazz) throws Exception  {
		try {
			return dtoBuilder( request.getParameterMap() ,  clazz );
		} catch (Exception e) {
			throw new Exception("request Parsing Error", e);
		}
	
	}
	/**
	 * Request 파라미터가 배열일경우 List<DTO> 로 생성하여 리턴
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<?> listBuilder(HttpServletRequest request, Class<?> clazz) throws Exception  {
		return listBuilder(request, clazz, null);
	}
	/**
	 * Request 파라미터가 배열일경우 List<DTO> 로 생성하여 리턴
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<?> listBuilder(HttpServletRequest request, Class clazz , String key) throws Exception  {
		try {
				Enumeration<?> enumeration = request.getParameterNames();
				Map bufMap = null;
				List<Map> bufList = new ArrayList<Map>(); 
				List outList = new ArrayList();
				String[] string = new String[0];
				if(key != null){
					string = request.getParameterValues(key);
					if(StringUtil.isEmptyArray(string)) return bufList;
				}
				
				while (enumeration.hasMoreElements()) {
					String name = (String) enumeration.nextElement();
					String[] values = request.getParameterValues(name);
					
					for (int i = bufList.size(); i < values.length; i++) {
						bufList.add(new HashMap());
					}
					
					for (int i = 0; i < values.length; i++) {
						bufMap = bufList.get(i);
						bufMap.put(name, values[i]);
					}
				}
				
				
				if(key != null){
					for (int i = 0; i < string.length; i++) {
						bufMap = bufList.get(i);
						outList.add(dtoBuilder(bufMap ,clazz));
					}
				}else{
					for (Iterator it = bufList.iterator(); it.hasNext();) {
						bufMap = (Map) it.next();
						outList.add(dtoBuilder(bufMap ,clazz));
					}
				}
				
			
			return outList;
		} catch (Exception e) {
			throw new Exception("request Parsing Error", e);
		}
		
	}

	/**
	 * Map을 DTO로 변환
	 * 
	 * @param map
	 * @param objClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private static Object dtoBuilder(Map<?,?> map, Class<?> objClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Object obj = objClass.newInstance();
		
		BeanUtils bu = new BeanUtils();
		bu.populate( obj , map);
		
		return obj;
	}

	
	/**
	 * Object를 DodelMap으로 변환
	 * @param dto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static ModelMap setObjToModelMap(Object dto) throws IllegalArgumentException, IllegalAccessException{
		return setObjToModelMap(dto,false);
	}
	@SuppressWarnings("unchecked")
	public static ModelMap setObjToModelMap(Object dto,final boolean listtype) throws IllegalArgumentException, IllegalAccessException{
		ModelMap outmap = new  ModelMap();
		if(dto instanceof Map){
			outmap.addAllAttributes((Map)objToModelMap(dto,listtype));
		}else if(dto instanceof List){
			outmap.put("rows",objToModelMap(dto,listtype));
		}else{
			outmap.addAllAttributes(dtoToModelMap(dto,listtype));
		}
		return outmap;
	}
	
	/**
	 * DTO을 ModelMap으로 변환
	 * @param dto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private static Map dtoToModelMap(Object dto ,final boolean listtype) throws IllegalArgumentException, IllegalAccessException{
		Class class1 = dto.getClass();
		Map map = new HashMap();
		for (Class cl : class1.getInterfaces()) {
			
			map.putAll(classToModelMap(cl,dto, listtype));
			
		}
		Class cla = class1.getSuperclass () ;
		if(!cla.getName().equals("java.lang.Object")){
			
			map.putAll(classToModelMap(cla,dto, listtype));
		}
		map.putAll(classToModelMap(class1,dto, listtype));
		return map;
	}
	/**
	 * DTO을 Class을 이용하여 ModelMap으로 변환
	 * @param dto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private static Map classToModelMap(Class class1, Object dto ,final boolean listtype) throws IllegalArgumentException, IllegalAccessException{
		Map map = new HashMap();
		Field[] fields = null;
		fields = class1.getDeclaredFields();
		
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Object data = extractField(dto, field);
			if(data == null) continue;
			Object setObj = objToModelMap(data, listtype);
			map.put(field.getName(),setObj );
			
		}
		
		return map;
	}
	
	/**
	 * DTO Object 의 데이터를 Json의 ModelMap으로 변환
	 * @param data
	 * @param listtype
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private static Object objToModelMap(Object data ,final boolean listtype) throws IllegalArgumentException, IllegalAccessException{
		Object reObj = data;
		if(data instanceof List){
			List list = (List) data;
//			List setList = new ArrayList();
			Map<String, Object> map = new HashMap<String, Object>();
			int longth = list.size();
			Object[] ds = new Object[longth];
			for (int i = 0; i < longth; i++) {
				Object obj = list.get(i);
				
				Object setObj = objToModelMap(obj,listtype);
				if(listtype){
					ds[i] = setObj;
					continue;
				}
				if(isDataType(setObj)){
//						ds[i] = setObj;
				}else if(setObj instanceof Map){
					Map maps =  (Map) setObj;
					
					for (Iterator it = maps.keySet().iterator(); it.hasNext();) {
						Object key = (Object) it.next();
						
						Object[] objects = (Object[]) map.get(key);
						if(objects == null){
							objects = new Object[longth];
							map.put((String) key, objects);
						}
						
						objects[i] = maps.get(key);
					}
				}
			}
			if(listtype)reObj=ds;
			else reObj = map;
		}else if(data instanceof Map){
			Map map = ( Map) data;
			Map setmap = new HashMap();
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				Object obj = (Object) it.next();
				Object inObj = map.get(obj);
				
				Object setObj = objToModelMap(inObj,listtype);
				setmap.put(obj, setObj);
			}
			
			reObj = setmap;
		}else if(data != null && !isDataType(data)){
			reObj = dtoToModelMap(data,listtype);
		}
		
		return reObj;
	}
	
	/** 데이터형인지 확인
	 * @param data
	 * @return
	 */
	private static boolean isDataType(Object data) {
		return (data instanceof String
				|| data instanceof Character
				|| data instanceof Byte
				|| data instanceof Long
				|| data instanceof Integer
				|| data instanceof Double
				|| data instanceof Float
				|| data instanceof Short
				|| data instanceof Boolean
				|| data instanceof Object[]
		);
	}
	
	/**
	 * 배열을 List로 변환
	 * @param strings
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<?> array2List(Object[] strings) {
		if(strings != null){
			List re = new ArrayList();
			for (int i = 0; i < strings.length; i++) {
				re.add( strings[i] );
			}
			return re;
		}else{
			return null;
		}
	}
	/**
	 * Object 데이터를 String으로 변환
	 * @param dataObj
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private static String objectToString(Object dataObj) throws SQLException {
		if(dataObj == null) 		
			return "";
		
		return String.valueOf(dataObj);
	}
	
	
	
	 /**
	  * Sha1 암호화
	 * @param strData
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt_sha1(String strData) throws Exception { // 암호화 시킬 데이터
		if(StringUtil.isEmpty(strData)) throw new Exception("Data is Empty \n data["+strData+"]");
		
		StringBuffer strENCData = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("SHA1"); // "SHA1 형식으로 암호화"
		md.update(strData.getBytes());
		byte[] digest = md.digest(); // 배열로 저장을 한다.
		for (byte b : digest) {
			strENCData.append(Integer.toHexString(b & 0xFF));
		}
		
		return strENCData.toString(); // 암호화된 데이터를 리턴...
	}
	/**
	 * List의 DTO에 특정 데이터를 삽입
	 * @param list
	 * @param name
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setObjList(List list, String name,
			Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		if(StringUtil.isNotEmptyList(list) && StringUtil.isNotEmpty(name)){
			Field field = list.get(0).getClass().getDeclaredField(name);
			boolean b = field.isAccessible();
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object obj = (Object) it.next();
				if(!b) field.setAccessible(true);
				field.set(obj, value);
				if(!b) field.setAccessible(false);
			}
		}
	}
	
	/**
	 * List를 Map으로 변환
	 * @param list
	 * @param name
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map list2Map(List list,
			String... name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		if(StringUtil.isEmptyList(list))return null;
		Field[] fields = new Field[name.length];
		Object[] keys = null;
		for (int i = 0; i < name.length; i++) {
			
			fields[i] = list.get(0).getClass().getDeclaredField(name[i]);
		}
		
		TreeMap treeMap = new TreeMap();
		
		for (Iterator it = list.iterator(); it.hasNext();) {
			keys = new Object[name.length];
			Object obj = (Object) it.next();
			
			for (int i = 0; i < fields.length; i++) {
				keys[i] = extractField(obj, fields[i]);
			}
			treeMap.put(obj, keys);
		}
		return treeMap;//.toHashMap();
	}
	
	
	/**
	 * List 의 DTO의 특정 데이터를 추출
	 * @param list
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static Object[] extractListObject(List list,
			String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		if(StringUtil.isEmptyList(list)) return null;
		
		List<Object> ol = new ArrayList<Object>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object obj = (Object) it.next();
			ol.add(extractObject(obj, name));
		}
		return ol.toArray();
	}
	
	
	/**
	 * Object DTO의 특정 필드의 데이터를 추출 
	 * @param obj
	 * @param name
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object extractObject(Object obj,
			String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(name);
		Object value = extractField(obj , field);
		return value;
	}

	/**
	 * 특정 필드를 가지고 데이터를 추출
	 * @param obj
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object extractField(Object obj,
			Field field) throws IllegalArgumentException, IllegalAccessException {
		boolean b = field.isAccessible();
		if(!b)field.setAccessible(true);
		Object value = field.get(obj);
		if(!b)field.setAccessible(false);
		
		return value;
	}
	
	/**
	 *  파라미터가 Map 인 객체를 정렬
	 * @param list
	 * @param order
	 * @param pam
	 */
	@SuppressWarnings("unchecked")
	public static void sortListIsMap(List list,  int order , String... pam ) {
		EDIComparatorMap compiler = new EDIComparatorMap(pam , order);
		Collections.sort(list,compiler);
	}//SortModel


	/**
	 * 트리맵으로 변환
	 * @param list
	 * @param name
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static TreeMap list2TreeMap(List list, String... name)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		if (StringUtil.isEmptyList(list))
			return null;
		Field[] fields = new Field[name.length];
		Object[] keys = null;
		for (int i = 0; i < name.length; i++) {

			fields[i] = list.get(0).getClass().getDeclaredField(name[i]);
		}

		TreeMap treeMap = new TreeMap();

		for (Iterator it = list.iterator(); it.hasNext();) {
			keys = new Object[name.length];
			Object obj = (Object) it.next();

			for (int i = 0; i < fields.length; i++) {
				keys[i] = extractField(obj, fields[i]);
			}
			treeMap.put(obj, keys);
		}
		return treeMap;
	}
	
}
/**
 * Map데이터를 정렬하기 위한 비교 객체
 * @author 9010661
 *
 */
@SuppressWarnings("rawtypes")
class EDIComparatorMap implements Comparator<Map>{
	
	// 오름/내림 설정 변수
	private int oerder;
	
	/**
	 * 생성자
	 * @param arg
	 */
	public EDIComparatorMap(Object[] arg ) {
		this.arg = arg;
		oerder = 1; // 오름차순 (ASC)
	}
	public EDIComparatorMap(Object[] arg, int order) {
		this.arg = arg;
		this.oerder = order;
	}
	//비교 객체
	Collator collator = Collator.getInstance();
	//데이터를 비교하기 위한 Key
	Object[] arg;
	
	public int compare(Map d1, Map d2) {
		int rel = 0;
		Object o1 , o2;
		for (int i = 0; i < arg.length; i++) {
			o1 = d1.get(arg[i]);
			o2 = d2.get(arg[i]);
			if(!(o1 instanceof String)) o1 = String.valueOf(o1);
			if(!(o2 instanceof String)) o2 = String.valueOf(o2);
			rel = collator.compare(o1, o2);
			if(0 != rel){
				break;
			}
		}
		
		rel = rel * oerder;
		return rel;
	}
	
	public int getOerder() {
		return oerder;
	}
	public void setOerder(int oerder) {
		this.oerder = oerder;
	}
	
}
