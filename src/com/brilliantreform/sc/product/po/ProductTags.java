package com.brilliantreform.sc.product.po;

public final class ProductTags {

	//称重商品
	public static final int WEIGHT = 0;
	//APP专享商品
	public static final int APPONLY = 1;
	//满减商品
	public static final int WHOLESALE = 2;
	//不能使用区享卡商品
	public static final int NOQXCARD = 3;
	//打折商品
	public static final int NOSALE = 4;
	//预售商品
	public static final int PRESALE = 5;
	//无货商品
	public static final int NOGOODS = 6;
	
	/**
	 * 设置商品tags
	 * @param tags 商品原来的tags
	 * @param tag 要设置的tag
	 * @param type true 设置 false 取消设置
	 * @return 设置后的tags
	 */
	public static int setTags(int tags, int tag, boolean type)
	{
		if(type)
		{
			if( (tags & ( 1<< tag)) == 0)
			{			
				tags = tags | ( 1 << tag);				
			}
		}else
		{
			if( (tags & ( 1<< tag)) != 0)
			{			
				tags = ~ (( ~ tags) | (1 << tag));				
			}
		}
		
		return tags;
	}
	
	/**
	 * 检查商品tags
	 * @param tags 商品的tags
	 * @param tag 要判断的tag
	 * @return  true 有该tag  false 无该tag
	 */
	public static boolean checkTag(int tags, int tag)
	{	
			if( (tags & ( 1<< tag)) == 0)					
				return false;			
			else
				return true;	
	}
	
	
}
