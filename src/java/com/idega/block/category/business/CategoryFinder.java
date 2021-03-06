package com.idega.block.category.business;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.block.category.data.Category;
import com.idega.block.category.data.CategoryEntity;
import com.idega.block.category.data.ICCategory;
import com.idega.block.category.data.ICCategoryHome;
import com.idega.core.component.business.ICObjectBusiness;
import com.idega.core.component.data.ICObjectInstance;
import com.idega.data.EntityControl;
import com.idega.data.EntityFinder;
import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFinderException;
import com.idega.data.IDOLegacyEntity;
import com.idega.data.IDOLookup;
/**
 *  Title: Description: Copyright: Copyright (c) 2000-2001 idega.is All Rights
 *  Reserved Company: idega
 *
 * @author     <a href="mailto:aron@idega.is">Aron Birkir</a>
 * @created    11. mars 2002
 * @version    1.1
 */
public class CategoryFinder {
	private static CategoryFinder categoryFinder;
	/**
	 *  Gets the category of the CategoryFinder object
	 *
	 * @param  iCategoryId  Description of the Parameter
	 * @return              The category value
	 */
	public ICCategory getCategory(int iCategoryId) {
		if (iCategoryId > 0) {
			try {
				return ((ICCategoryHome) IDOLookup.getHome(ICCategory.class)).findByPrimaryKey(new Integer(iCategoryId));
			}
			catch (Exception sql) {
				sql.printStackTrace(System.err);
				return null;
			}
			//      return (ICCategory) com.idega.core.data.ICCategoryBMPBean.getEntityInstance(ICCategory.class, iCategoryId);
		}
		return null;
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @param  type  Description of the Parameter
	 * @return       Description of the Return Value
	 */
	public List listOfCategories(String type) {
		try {
			return EntityFinder.findAllByColumn(
				GenericEntity.getStaticInstance(ICCategory.class),
				com.idega.block.category.data.ICCategoryBMPBean.getColumnType(),
				type);
		}
		catch (SQLException ex) {
			return null;
		}
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @return    Description of the Return Value
	 */
	public List listOfValidCategories() {
		try {
			return EntityFinder.findAllByColumn(
				GenericEntity.getStaticInstance(ICCategory.class),
				com.idega.block.category.data.ICCategoryBMPBean.getColumnValid(),
				"Y");
		}
		catch (SQLException ex) {
			return null;
		}
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @param  type  Description of the Parameter
	 * @return       Description of the Return Value
	 */
	public List listOfValidCategories(String type) {
		try {
			return EntityFinder.findAllByColumn(
				GenericEntity.getStaticInstance(ICCategory.class),
				com.idega.block.category.data.ICCategoryBMPBean.getColumnValid(),
				"Y",
				com.idega.block.category.data.ICCategoryBMPBean.getColumnType(),
				type);
		}
		catch (SQLException ex) {
			return null;
		}
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @return    Description of the Return Value
	 */
	public List listOfInValidCategories() {
		try {
			return EntityFinder.findAllByColumn(
				GenericEntity.getStaticInstance(ICCategory.class),
				com.idega.block.category.data.ICCategoryBMPBean.getColumnValid(),
				"N");
		}
		catch (SQLException ex) {
			return null;
		}
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @param  type  Description of the Parameter
	 * @return       Description of the Return Value
	 */
	public List listOfInValidCategories(String type) {
		try {
			return EntityFinder.findAllByColumn(
				GenericEntity.getStaticInstance(ICCategory.class),
				com.idega.block.category.data.ICCategoryBMPBean.getColumnValid(),
				"N",
				com.idega.block.category.data.ICCategoryBMPBean.getColumnType(),
				type);
		}
		catch (SQLException ex) {
			return null;
		}
	}
	/**
	 *  Gets the objectInstanceIdFromCategoryId of the CategoryFinder object
	 *
	 * @param  iCategoryId  Description of the Parameter
	 * @return              The object instance id from category id value
	 */
	public int getObjectInstanceIdFromCategoryId(int iCategoryId) {
		try {
			ICCategory nw = getCategory(iCategoryId);
			List L =
				EntityFinder.findRelated(
					nw,
					((com.idega.core.component.data.ICObjectInstanceHome) com
						.idega
						.data
						.IDOLookup
						.getHomeLegacy(ICObjectInstance.class))
						.createLegacy());
			if (L != null) {
				return ((ICObjectInstance) L.get(0)).getID();
			}
			else {
				return -1;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			return -2;
		}
	}
	/**
	 *  Gets the objectInstanceCategoryId of the CategoryFinder object
	 *
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @param  CreateNew          Description of the Parameter
	 * @param  type               Description of the Parameter
	 * @return                    The object instance category id value
	 */
	public int getObjectInstanceCategoryId(int iObjectInstanceId, boolean CreateNew, String type) {
		int id = -1;
		try {
			ICObjectInstance obj =
				(
					(com.idega.core.component.data.ICObjectInstanceHome) com.idega.data.IDOLookup.getHomeLegacy(
						ICObjectInstance.class)).findByPrimaryKeyLegacy(
					iObjectInstanceId);
			if (obj == null) {
				System.err.println("ic_object is null " + iObjectInstanceId);
			}
			id = getObjectInstanceCategoryId(obj);
			if (id <= 0 && CreateNew) {
				id = CategoryBusiness.getInstance().createCategory(iObjectInstanceId, type);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return id;
	}
	/**
	 *  Gets the objectInstanceCategoryIds of the CategoryFinder object
	 *
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @param  CreateNew          Description of the Parameter
	 * @param  type               Description of the Parameter
	 * @return                    The object instance category ids value
	 */
	public int[] getObjectInstanceCategoryIds(int iObjectInstanceId, boolean CreateNew, String type) {
		int[] ids = new int[0];
		try {
			ids = getObjectInstanceCategoryIds(iObjectInstanceId);
			if (ids.length == 0 && CreateNew) {
				System.out.println("[CategoryBlock]: Had to create new category for instance id = "+iObjectInstanceId+" of type "+type);
				ids = new int[1];
				ids[0] = CategoryBusiness.getInstance().createCategory(iObjectInstanceId, type);
				System.out.println("[CategoryBlock]: the new category ID is "+ids[0]);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return ids;
	}
	/**
	 *  Gets the objectInstanceCategoryId of the CategoryFinder object
	 *
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @return                    The object instance category id value
	 */
	public int getObjectInstanceCategoryId(int iObjectInstanceId) {
		try {
			ICObjectInstance obj =
				(
					(com.idega.core.component.data.ICObjectInstanceHome) com.idega.data.IDOLookup.getHomeLegacy(
						ICObjectInstance.class)).findByPrimaryKeyLegacy(
					iObjectInstanceId);
			return getObjectInstanceCategoryId(obj);
		}
		catch (Exception ex) {
		}
		return -1;
	}
	/**
	 *  Gets the objectInstanceCategoryId of the CategoryFinder object
	 *
	 * @param  eObjectInstance  Description of the Parameter
	 * @return                  The object instance category id value
	 */
	public int getObjectInstanceCategoryId(ICObjectInstance eObjectInstance) {
		try {
			List L =
				EntityFinder.findRelated(
					eObjectInstance,
					GenericEntity.getStaticInstance(ICCategory.class));
			if (L != null) {
				return ((IDOLegacyEntity) L.get(0)).getID();
			}
			else {
				return -1;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			return -2;
		}
	}
	/**
	 *  Gets the objectInstanceCategoryIds of the CategoryFinder object
	 *
	 * @param  ICObjectInstanceId  Description of the Parameter
	 * @return                     The object instance category ids value
	 */
	public int[] getObjectInstanceCategoryIds(int ICObjectInstanceId) {
		//select ic_category_id from IC_CATEGORY_ic_object_instance where ic_object_instance_ID=51
		StringBuffer sql = new StringBuffer("select ");
		ICCategory cat = (ICCategory) GenericEntity.getStaticInstance(ICCategory.class);
		ICObjectInstance obj =
			(ICObjectInstance) GenericEntity.getStaticInstance(ICObjectInstance.class);
		sql.append(cat.getIDColumnName()).append(" from ");
		sql.append(EntityControl.getManyToManyRelationShipTableName(ICCategory.class, ICObjectInstance.class));
		sql.append(" where ").append(obj.getIDColumnName()).append(" = ").append(ICObjectInstanceId);
		try {
			String[] sids = com.idega.data.SimpleQuerier.executeStringQuery(sql.toString());
			if (sids != null && sids.length > 0) {
				int[] ids = new int[sids.length];
				for (int i = 0; i < sids.length; i++) {
					ids[i] = Integer.parseInt(sids[i]);
				}
				return ids;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return new int[0];
		/*
		 *  try {
		 *  EntityFinder.debug = true;
		 *  List L = EntityFinder.findRelated(eObjectInstance ,(IDOLegacyEntity)com.idega.core.data.ICCategoryBMPBean.getStaticInstance(ICCategory.class));
		 *  EntityFinder.debug = false;
		 *  if(L!= null){
		 *  java.util.Iterator iter = L.iterator();
		 *  int[] ids = new int[L.size()];
		 *  for (int i = 0; i < ids.length; i++) {
		 *  ids[i] = ((IDOLegacyEntity) L.get(0)).getID();
		 *  }
		 *  return ids;
		 *  }
		 *  else
		 *  return new int[0];
		 *  }
		 *  catch (SQLException ex) {
		 *  ex.printStackTrace();
		 *  return new int[0];
		 *  }
		 */
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @param  instanceid  Description of the Parameter
	 * @return             Description of the Return Value
	 */
	public List listOfCategoryForObjectInstanceId(int instanceid, boolean order) {
		try {
			ICObjectInstance obj = ICObjectBusiness.getInstance().getICObjectInstance(instanceid);
			return listOfCategoryForObjectInstanceId(obj, order);
		}
		catch (Exception ex) {
			return null;
		}
	}
	public List listOfCategoryForObjectInstanceId(int instanceid) {
		try {
			ICObjectInstance obj = ICObjectBusiness.getInstance().getICObjectInstance(instanceid);
			return listOfCategoryForObjectInstanceId(obj);
		}
		catch (Exception ex) {
			return null;
		}
	}
	/**
	 *  @todo Description of the Method
	 *
	 * @param  obj  Description of the Parameter
	 * @return      Description of the Return Value
	 */
	public List listOfCategoryForObjectInstanceId(ICObjectInstance obj, boolean order)
		throws FinderException, RemoteException {
		ICCategoryHome catHome = (ICCategoryHome) IDOLookup.getHome(ICCategory.class);
		return catHome.getListOfCategoryForObjectInstance(obj, order);
	}
	public List listOfCategoryForObjectInstanceId(ICObjectInstance obj) {
		try {
			//ICCategoryICObjectInstanceHome home = (ICCategoryICObjectInstanceHome) IDOLookup.getHome(ICCategoryICObjectInstance.class);
			//return home.getListOfCategoryForObjectInstance(obj);
			List L =
				EntityFinder.findRelated(
					obj,
					GenericEntity.getStaticInstance(ICCategory.class));
			return L;
		}
		catch (Exception ex) {
			return null;
		}
	}
    
    /**
     * basically the same as listOfCategoryForObjectInstanceId, but returns only root categories
     * @param instanceid
     * @param order
     * @return
     */
    public List listOfRootCategoryForObjectInstanceId(int instanceid, boolean order) {
        try {
            ICObjectInstance obj = ICObjectBusiness.getInstance().getICObjectInstance(instanceid);
            return listOfRootCategoryForObjectInstanceId(obj, order);
        }
        catch (Exception ex) {
            return null;
        }
    } 
    public List listOfRootCategoryForObjectInstanceId(ICObjectInstance obj, boolean order) throws FinderException, RemoteException {
        ICCategoryHome catHome = (ICCategoryHome) IDOLookup
                .getHome(ICCategory.class);
        return catHome.getListOfRootCategoryForObjectInstance(obj, order);
    }    
    
	/**
	 *  Gets the relatedSQL of the CategoryFinder object
	 *
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @return                    The related SQL value
	 */
	//<<<<<<< CategoryFinder.java
	public String getRelatedSQL(int iObjectInstanceId) {
		/*try {
		  ICCategoryICObjectInstanceHome home = (ICCategoryICObjectInstanceHome) IDOLookup.getHome(ICCategoryICObjectInstance.class);
		  return home.getRelatedSQL(iObjectInstanceId);
		}catch (RemoteException rm)*/ {
			// Gamla d�ti�, fyrir Gr�m
			StringBuffer sql = new StringBuffer("select ");
			sql.append(
				((ICCategory) GenericEntity.getStaticInstance(ICCategory.class))
					.getIDColumnName());
			sql.append(" from ").append(
				com.idega.data.EntityControl.getManyToManyRelationShipTableName(
					ICCategory.class,
					ICObjectInstance.class));
			sql.append(" where ").append(
				((ICObjectInstance) GenericEntity
					.getStaticInstance(ICObjectInstance.class))
					.getIDColumnName());
			sql.append(" = ").append(iObjectInstanceId);
			return sql.toString();
		}
	}
	/*  public String getRelatedSQL(int iObjectInstanceId) {
	StringBuffer sql = new StringBuffer("select ");
	sql.append(((ICCategory) com.idega.core.data.ICCategoryBMPBean.getStaticInstance(ICCategory.class)).getIDColumnName());
	sql.append(" from ").append(com.idega.data.EntityControl.getManyToManyRelationShipTableName(ICCategory.class, ICObjectInstance.class));
	sql.append(" where ").append(((ICObjectInstance) com.idega.core.data.ICObjectInstanceBMPBean.getStaticInstance(ICObjectInstance.class)).getIDColumnName());
	sql.append(" = ").append(iObjectInstanceId);
	return sql.toString();
	}*/
	/**
	 *  Gets the relatedEntitySQL of the CategoryFinder object
	 *
	 * @param  tablename          Description of the Parameter
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @return                    The related entity SQL value
	 */
	private String getRelatedEntitySQL(String tablename, int iObjectInstanceId) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append(tablename).append(".* from ").append(tablename).append(",");
		String middletable = EntityControl.getManyToManyRelationShipTableName(ICCategory.class, ICObjectInstance.class);
		sql.append(middletable);
		sql.append(" where ").append(
			((ICObjectInstance) GenericEntity.getStaticInstance(ICObjectInstance.class))
				.getIDColumnName());
		sql.append(" = ").append(iObjectInstanceId);
		String idname =
			((ICCategory) GenericEntity.getStaticInstance(ICCategory.class)).getIDColumnName();
		sql.append(" and ").append(middletable).append(".").append(idname);
		sql.append(" = ").append(tablename).append(".").append(idname);
		return sql.toString();
	}
	/**
	 *  Gets the relatedEntitySql of the CategoryFinder object
	 *
	 * @param  EntityClass          Description of the Parameter
	 * @param  CategoryEntityClass  Description of the Parameter
	 * @param  EntityColumn         Description of the Parameter
	 * @param  iObjectInstanceId    Description of the Parameter
	 * @return                      The related entity sql value
	 */
	private String getRelatedEntitySql(
		Class EntityClass,
		Class CategoryEntityClass,
		String EntityColumn,
		int iObjectInstanceId) {
		IDOLegacyEntity entity = com.idega.data.GenericEntity.getStaticInstance(EntityClass);
		CategoryEntity catEntity =
			(CategoryEntity) GenericEntity.getStaticInstance(CategoryEntityClass);
		ICObjectInstance instanceEntity =
			(ICObjectInstance) GenericEntity.getStaticInstance(ICObjectInstance.class);
		ICCategory icCat = ((ICCategory) GenericEntity.getStaticInstance(ICCategory.class));
		String middletable = EntityControl.getManyToManyRelationShipTableName(ICCategory.class, ICObjectInstance.class);
		StringBuffer sql = new StringBuffer("select ");
		sql.append(entity.getEntityName()).append(".* from ").append(entity.getEntityName()).append(",");
		sql.append(catEntity.getEntityName()).append(",");
		sql.append(middletable);
		sql.append(" where ").append(instanceEntity.getIDColumnName());
		sql.append(" = ").append(iObjectInstanceId);
		sql.append(" and ").append(middletable).append(".").append(icCat.getIDColumnName());
		sql.append(" = ").append(catEntity.getEntityName()).append(".").append(
			com.idega.block.category.data.CategoryEntityBMPBean.getColumnCategoryId());
		sql.append(" and ").append(catEntity.getEntityName()).append(".").append(catEntity.getIDColumnName());
		sql.append(" = ").append(entity.getEntityName()).append(".").append(EntityColumn);
		//System.err.println(sql.toString());
		return sql.toString();
	}
	/**
	 *  Returns a Collection of ICCategory entities with specified type
	 *
	 * @param  ids   Description of the Parameter
	 * @param  type  Description of the Parameter
	 * @return       The categories value
	 */
	public Collection getCategories(int[] ids, String type) {
		StringBuffer sql = new StringBuffer("select * from ");
		ICCategory cat = (ICCategory) GenericEntity.getStaticInstance(ICCategory.class);
		sql.append(com.idega.block.category.data.ICCategoryBMPBean.getEntityTableName());
		sql.append(" where ").append(com.idega.block.category.data.ICCategoryBMPBean.getColumnType()).append(" = ").append(type);
		sql.append(" and ").append(cat.getIDColumnName()).append(" in (");
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append(ids[i]);
		}
		sql.append(" )");
		try {
			return EntityFinder.getInstance().findAll(ICCategory.class, sql.toString());
		}
		catch (IDOFinderException ex) {
		}
		return null;
	}
	/**
	 *  Returns a Collection of ICCategory-ids that have reference to a
	 *  ICObjectInstance
	 *
	 * @param  iObjectInstanceId  Description of the Parameter
	 * @return                    Description of the Return Value
	 */
	public Collection collectCategoryIntegerIds(int iObjectInstanceId) {
		String[] ids = null;
		try {
			String sql = getRelatedSQL(iObjectInstanceId);
			ids = com.idega.data.SimpleQuerier.executeStringQuery(sql);
			if (ids != null) {
				HashSet H = new HashSet();
				Integer I;
				for (int i = 0; i < ids.length; i++) {
					I = new Integer(ids[i]);
					if (!H.contains(I)) {
						H.add(I);
					}
				}
				return H;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/*
	 *  @todo Description of the Method
	 *
	 * @param  categoryEntityClass  Description of the Parameter
	 * @param  iCategoryId          Description of the Parameter
	 * @return                      Description of the Return Value
	 */
	public Collection listOfCategoryEntity(Class categoryEntityClass, int iCategoryId) {
		//if (categoryEntityClass.getSuperclass().equals(CategoryEntity.class)) {
		try {
			return EntityFinder.getInstance().findAllByColumn(
				categoryEntityClass,
				com.idega.block.category.data.CategoryEntityBMPBean.getColumnCategoryId(),
				iCategoryId);
		}
		catch (IDOFinderException ex) {
		}
		//}
		return null;
	}
	/*
	 *  public Collection listOfCategoryEntity(Class categoryEntityClass,int[] iCategoryIds){
	 *  if(categoryEntityClass.getSuperclass().equals(CategoryEntity.class)){
	 *  return EntityFinder.getInstance().findAllByColumn(categoryEntityClass,com.idega.data.CategoryEntityBMPBean.getColumnCategoryId(),iCategoryId);
	 *  }
	 *  else return null;
	 *  }
	 */
	/**
	 *  @todo Description of the Method
	 *
	 * @param  categoryEntityClass  Description of the Parameter
	 * @param  ObjectInstanceId     Description of the Parameter
	 * @return                      Description of the Return Value
	 */
	public List listOfCategoryEntityByInstanceId(Class categoryEntityClass, int ObjectInstanceId) {
		//if (categoryEntityClass.getSuperclass().equals(CategoryEntity.class)) {
		try {
			String entityName = ((CategoryEntity) categoryEntityClass.newInstance()).getEntityName();
			return EntityFinder.getInstance().findAll(
				categoryEntityClass,
				getRelatedEntitySQL(entityName, ObjectInstanceId));
		}
		catch (IDOFinderException ex) {
		}
		catch (Exception ex) {
		}
		//}
		return null;
	}
	/**
	 *  Gets the categoryRelatedEntityFromInstanceId of the CategoryFinder object	
	 *
	 * @param  CategoryEntityClass  Description of the Parameter
	 * @param  EntityClass          Description of the Parameter	
	 * @param  EntityColumn         Description of the Parameter	
	 * @param  ObjectInstanceId     Description of the Parameter	
	 * @return                      The category related entity from instance id	
	 *      value	
	 */
	public Collection getCategoryRelatedEntityFromInstanceId(
		Class CategoryEntityClass,
		Class EntityClass,
		String EntityColumn,
		int ObjectInstanceId) {
		//if (CategoryEntityClass.getSuperclass().equals(CategoryEntity.class)) {
		try {
			return EntityFinder.getInstance().findAll(
				EntityClass,
				getRelatedEntitySql(EntityClass, CategoryEntityClass, EntityColumn, ObjectInstanceId));
		}
		catch (IDOFinderException ex) {
		}
		catch (Exception ex) {
		}
		//}
		return null;
	}
	/**
	 *  Gets the instance of the CategoryFinder class
	 *
	 * @return    The instance value
	 */
	public static CategoryFinder getInstance() {
		if (categoryFinder == null) {
			categoryFinder = new CategoryFinder();
		}
		return categoryFinder;
	}
	/**
	 *  Gets the mapOfCategoriesById of the CategoryFinder object
	 *
	 * @param  c  Description of the Parameter
	 * @return    The map of categories by id value
	 */
	public Map getMapOfCategoriesById(int instanceId) {
		return EntityFinder.getInstance().getMapOfEntity(
			listOfCategoryForObjectInstanceId(instanceId),
			GenericEntity.getStaticInstance(ICCategory.class).getIDColumnName());
	}
	public String getInstanceManyToManyRelationShipName() {
		return EntityControl.getManyToManyRelationShipTableName(ICCategory.class, ICObjectInstance.class);
	}
	public ICCategoryHome getCategoryHome() throws java.rmi.RemoteException {
		return (ICCategoryHome) IDOLookup.getHome(ICCategory.class);
	}
	public int getCategoryOrderNumber(Category category, ICObjectInstance instance)
		throws FinderException, RemoteException {
		return getCategoryHome().getOrderNumber(category, instance);
	}
	public int getCategoryOrderNumber(Category category, String instanceId)
		throws FinderException, RemoteException {
		return getCategoryHome().getOrderNumber(category, instanceId);
	}
	public boolean setOrderNumber(Category category, ICObjectInstance instance, int orderNumber)
		throws IDOException, RemoteException {
		return getCategoryHome().setOrderNumber(category, instance, orderNumber);
	}
}
