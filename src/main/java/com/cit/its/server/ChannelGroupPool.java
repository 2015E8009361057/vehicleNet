/**  
* @Title: ChannelGroupPool.java 
* @Package com.cit.its.server 
* @Description: TODO 
* @author zxl   
* @date 2015年3月31日 下午4:38:25 
*/ 

package com.cit.its.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/** 
 * @Title: ChannelGroupPool
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月31日 下午4:38:25  
 */
public class ChannelGroupPool {
	public static ChannelGroup channels = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

}
