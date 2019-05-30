package com.xutt.sky.portal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TreeUtils<T> {

	public static <T> List<T> transToTree(List<T> datas, String idName, String parIdName, String childName)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (datas == null) {
			return null;
		}
		List<T> tree = new ArrayList();
		Map<String, T> selfKey = new HashMap();
		Map<String, List<T>> parKey = new HashMap();
		Method selfGet = null, parGet = null, childGet = null, childSet = null;
		for (T data : datas) {
			if (selfGet == null) {
				selfGet = AccessorUtils.findGetter(data, idName);
			}
			if (parGet == null) {
				parGet = AccessorUtils.findGetter(data, parIdName);
			}
			if (childGet == null) {
				childGet = AccessorUtils.findGetter(data, childName);
			}
			if (childSet == null) {
				childSet = AccessorUtils.findSetter(data, childName);
			}
			String id = (String) selfGet.invoke(data);
			String parId = (String) parGet.invoke(data);
			selfKey.put(id, data);
			List<T> parKeyList = parKey.get(parId);
			if (parKeyList == null) {
				parKeyList = new ArrayList();
				parKey.put(parId, parKeyList);
			}
			parKeyList.add(data);
		}
		Set<String> set = parKey.keySet();
		List<String> keyList = new ArrayList(set);
		for (String par : keyList) {
			T obj = selfKey.get(par);
			if (obj != null) {
				Object children = childGet.invoke(obj);
				if (children == null) {
					children = new ArrayList();
					childSet.invoke(obj, children);
				}
				((List<T>) children).addAll(parKey.get(par));
				parKey.remove(par);
			}
		}

		Set<Entry<String, List<T>>> root = parKey.entrySet();
		List<Entry<String, List<T>>> rootList = new ArrayList(root);
		for (Entry<String, List<T>> rootsKey : rootList) {
			for (T rootKey : rootsKey.getValue()) {
				T rootObj = selfKey.get(selfGet.invoke(rootKey));
				tree.add(rootObj);
			}
		}
		return tree;
	}

	private List arrayData = new ArrayList();
	private List treeDepthArray = new ArrayList();
	public static String columKey = "columKey";
	public static String rows = "rows";
	public static String columIdKey = "id_";
	public static String colKeyPre = "col";

	public <T> Map transToArray(List<T> trees, String childKey, String id, String text) throws Exception {
		Method idGet = null, textGet = null;
		this.reExe(trees, childKey, 1, null);
		Integer depth = Integer.parseInt(Collections.max(treeDepthArray).toString());
		Map<String, Object> map = new HashMap();
		List columKey = new ArrayList();
		List rows = new ArrayList();
		for (int i = 0; i < depth; i++) {
			columKey.add(colKeyPre + i);
		}
		for (int i = 0; i < arrayData.size(); i++) {
			List row = (List) arrayData.get(i);
			Map rowMap = new HashMap();
			for (int j = 0; j < row.size(); j++) {
				T col = (T) row.get(j);
				if (idGet == null) {
					idGet = AccessorUtils.findGetter(col, id);
				}
				if (textGet == null) {
					textGet = AccessorUtils.findGetter(col, text);
				}
				String treeId = (String) idGet.invoke(col);
				String treeText = (String) textGet.invoke(col);

				rowMap.put(colKeyPre + j, treeText);
				rowMap.put(columIdKey + colKeyPre + j, treeId);
			}
			rows.add(rowMap);
		}

		map.put(this.columKey, columKey);
		map.put(this.rows, rows);
		return map;
	}

	private <T> void reExe(List<T> trees, String childKey, int depthP, List<T> par) throws Exception {

		Method childGet = null;
		for (int i = 0; i < trees.size(); i++) {
			int depth = Integer.valueOf(depthP);
			List<T> parCol = par;
			if (parCol == null) {
				depth = 1;
				parCol = new ArrayList();
			} else {
				parCol = new ArrayList();
				parCol.addAll(par);
			}

			T t = trees.get(i);
			if (childGet == null) {
				childGet = AccessorUtils.findGetter(t, childKey);
			}
			List<T> childItems = (List<T>) childGet.invoke(t);
			parCol.add(t);
			if (childItems != null) {
				depth++;
				this.reExe(childItems, childKey, depth, parCol);
			} else {
				arrayData.add(parCol);
				treeDepthArray.add(depth);
				parCol = null;
			}

		}

	}

	public static void main(String[] args) throws Exception {
	}

}
