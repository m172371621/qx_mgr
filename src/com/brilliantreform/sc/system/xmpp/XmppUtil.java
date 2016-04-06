package com.brilliantreform.sc.system.xmpp;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XmppUtil {
	
	private static Logger logger = Logger.getLogger(XmppUtil.class);
	
    private static int SERVER_PORT = 5222;  
    
//   private static String SERVER_HOST = "127.0.0.1";  
    
    private static String SERVER_HOST = "61.191.191.84";  
    private static XmppUtil xmppUtil = new XmppUtil();  

    private static XMPPConnection connection = null;  
    //private String SERVER_NAME = "ubuntuserver4java";  
    
    /** 
     * 单例模式 
     *  
     * @return 
     */  
    synchronized public static XmppUtil getInstance() {  
        return xmppUtil;  
    }  
  
    /** 
     * 创建连接 
     */  
    public XMPPConnection getConnection() {  
        if (connection == null) {  
            openConnection();  
        }  
        return connection;  
    }  
  
    /** 
     * 打开连接 
     */  
    public boolean openConnection() {  
        try {  
            if (null == connection || !connection.isAuthenticated()) {  
                XMPPConnection.DEBUG_ENABLED = false;// DEBUG模式  
                // 配置连接  
                ConnectionConfiguration config = new ConnectionConfiguration(  
                        SERVER_HOST, SERVER_PORT);  
                config.setReconnectionAllowed(true);  
                config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  
                config.setSendPresence(true); // 状态设为离线，目的为了取离线消息  
                config.setSASLAuthenticationEnabled(false); // 是否启用安全验证  

                connection = new XMPPConnection(config);  
                connection.connect();// 连接到服务器  
                // 配置各种Provider，如果不配置，则会无法解析数据  
                logger.info("openConnection : 开启连接成功");  
                return true;  
            }  
        } catch (XMPPException xe) {  
            logger.equals(xe.getMessage());  
            connection = null;  
        }  
        logger.info("openConnection : 开启连接失败");  
        return false;  
    }  
  
    /** 
     * 关闭连接 
     */  
    public void closeConnection() {  
        if(connection!=null){            
            //connection.removeConnectionListener(connectionListener);  
            if(connection.isConnected())  
                connection.disconnect();  
            connection = null;  
        }  
        logger.info("XmppConnection : 关闭连接");  
    } 
	


	public boolean xmppRegist(String account, String password) {
		logger.info("xmppRegist : "+account+";"+password);  
		if (getConnection() == null)  
	            return false;  

	    try {
			getConnection().getAccountManager().createAccount(account, password);
			//getConnection().getAccountManager().createAccount(username, password, attributes)
			//RosterEntry entry = getConnection().getRoster().getEntry(user.getJID());

			//entry.setName("");
			closeConnection();
			logger.info("xmppRegist : 成功");  
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("xmppRegist : 失败"); 
		return false;		
	}
	
	public boolean xmppResetPwd(String account, String password,String newPassword) {
		logger.info("xmppResetPwd : "+account+";"+newPassword);  
		if (getConnection() == null)  
	            return false;  

	    try {
	    	//getConnection().getAccountManager().
	    	getConnection().login(account, password);  
			getConnection().getAccountManager().changePassword(newPassword);
			//getConnection().getAccountManager().createAccount(username, password, attributes)
			//RosterEntry entry = getConnection().getRoster().getEntry(user.getJID());

			//entry.setName("");
			closeConnection();
			logger.info("xmppRegist : 成功");  
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("xmppRegist : 失败"); 
		return false;		
	}
}
