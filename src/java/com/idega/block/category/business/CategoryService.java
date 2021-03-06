package com.idega.block.category.business;



public interface CategoryService extends com.idega.business.IBOService
{
 public int createCategory(int p0,java.lang.String p1)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public boolean deleteBlock(int p0)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public boolean disconnectBlock(int p0)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public boolean disconnectCategory(com.idega.block.category.data.ICCategory p0,int p1) throws java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategoryHome getCategoryHome()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategoryTranslationHome getCategoryTranslationHome()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public void removeCategory(int p0)throws java.lang.Exception, java.rmi.RemoteException;
 public void removeCategory(int p0,int p1)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public boolean removeInstanceCategories(int p0) throws java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategory storeCategory(int p0,java.lang.String p1,java.lang.String p2,int p3,java.lang.String p4,boolean p5)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategory storeCategory(com.idega.block.category.data.ICCategory p0,int p1,boolean p2) throws java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategory storeCategory(com.idega.block.category.data.ICCategory p0,int p1,int p2,boolean p3) throws java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategory storeCategory(int p0,java.lang.String p1,java.lang.String p2,int p3,int p4,java.lang.String p5,boolean p6)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.category.data.ICCategory storeCategory(int p0,java.lang.String p1,java.lang.String p2,int p3,java.lang.String p4)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public void storeCategoryToParent(int p0,int p1)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public void storeCategoryTranslation(int p0,java.lang.String p1,java.lang.String p2,int p3)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public void storeRelatedCategories(int p0,int[] p1) throws java.rmi.RemoteException;
 public boolean updateCategory(int p0,java.lang.String p1,java.lang.String p2,int p3)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public boolean updateCategory(int p0,java.lang.String p1,java.lang.String p2,int p3,int p4,int p5)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Map getInheritedMetaData(com.idega.block.category.data.ICCategory category) throws java.rmi.RemoteException;
 public java.util.Map getInheritedMetaData(java.util.Map table, com.idega.block.category.data.ICCategory category) throws java.rmi.RemoteException;
 public java.util.Map getInheritedMetaDataTypes(com.idega.block.category.data.ICCategory category) throws java.rmi.RemoteException;
 public java.util.Map getInheritedMetaDataTypes(java.util.Map metadata, com.idega.block.category.data.ICCategory category) throws java.rmi.RemoteException;
}